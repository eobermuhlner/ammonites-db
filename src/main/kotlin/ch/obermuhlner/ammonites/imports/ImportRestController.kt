package ch.obermuhlner.ammonites.imports

import ch.obermuhlner.ammonites.ammonite.AmmoniteService
import ch.obermuhlner.ammonites.image.ImageService
import ch.obermuhlner.ammonites.imports.parser.ImportParser
import ch.obermuhlner.ammonites.imports.parser.ImportParserFactory
import ch.obermuhlner.ammonites.jooq.tables.pojos.Ammonite
import ch.obermuhlner.ammonites.jooq.tables.pojos.Image
import ch.obermuhlner.ammonites.jooq.tables.pojos.Measurement
import ch.obermuhlner.ammonites.measurement.MeasurementService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.net.URLConnection
import java.util.*

@RestController
@RequestMapping("/api/import")
class ImportRestController @Autowired constructor(
    private val ammoniteService: AmmoniteService,
    private val imageService: ImageService,
    private val measurementService: MeasurementService
) {
    @PostMapping("/file")
    fun importFile(@RequestParam("file") file: MultipartFile, @RequestParam("images") images: List<MultipartFile>): ResponseEntity<String> {
        if (file.isEmpty) {
            return ResponseEntity.badRequest().body("File is empty")
        }

        val imagesMap = convertToMap(images)

        val log = StringBuilder()
        val parser = ImportParserFactory().getParser(file)
        val result = parser.parse(file.inputStream,
            log,
            { headers, log ->
                val importType = getImportType(headers)
                log.append("Importing: $importType\n")
                when (importType) {
                    ImportType.AMMONITES -> importAmmonites(file, imagesMap, parser, log)
                    ImportType.AMMONITE_MEASUREMENTS -> importMeasurementsForAmmonites(file, imagesMap, parser, log)
                    ImportType.UNKNOWN -> log.append("Unknown import type: $headers\n")
                }
            },
            { _, _ -> }
        )
        return ResponseEntity.ok(result)
    }

    private fun getImportType(headers: List<String>): ImportType {
        return when {
            headers.containsAll(listOf("Subclass", "Family", "Genus", "Species")) -> ImportType.AMMONITES
            headers.containsAll(listOf("Species", "DiameterSide", "ProportionN")) -> ImportType.AMMONITE_MEASUREMENTS
            else -> ImportType.UNKNOWN
        }
    }

    private fun importAmmonites(file: MultipartFile, imagesMap: Map<String, ByteArray>, parser: ImportParser, log: StringBuilder): String {
        parser.parse(
            file.inputStream,
            log,
            { headers, _ -> log.append("Importing Ammonites with headers: $headers\n") },
            { cells, log ->
                if (cells.size >= 11) {
                    processAmmoniteRow(
                        taxonomySubclass = cells[0],
                        taxonomyFamily = cells[1],
                        taxonomySubfamily = cells[2],
                        taxonomyGenus = cells[3],
                        taxonomySubgenus = cells[4],
                        taxonomySpecies = cells[5],
                        description = cells[6],
                        comment = cells[7],
                        strata = cells[8],
                        overwriteImage = cells[9].toBoolean(),
                        imageFilePath = cells[10],
                        imageBytes = imagesMap[cells[10]],
                        log = log)
                } else {
                    log.append("Invalid data format: $cells\n")
                }
            })
        return log.toString()
    }

    private fun processAmmoniteRow(
        taxonomySubclass: String,
        taxonomyFamily: String,
        taxonomySubfamily: String,
        taxonomyGenus: String,
        taxonomySubgenus: String,
        taxonomySpecies: String,
        description: String,
        comment: String,
        strata: String,
        overwriteImage: Boolean,
        imageFilePath: String,
        imageBytes: ByteArray?,
        log: StringBuilder
    ) {
        val oldAmmonite = ammoniteService.findByTaxonomySpecies(taxonomySpecies)
        val imageId = uploadImageIfNecessary(overwriteImage, oldAmmonite?.imageId, imageFilePath, imageBytes, log)
        val newAmmonite = Ammonite(null, taxonomySubclass, taxonomyFamily, taxonomySubfamily, taxonomyGenus, taxonomySubgenus, taxonomySpecies, strata, description, comment, imageId)

        if (oldAmmonite == null) {
            val id = ammoniteService.create(newAmmonite)
            log.append("New ammonite: $id '${newAmmonite.taxonomySpecies}'\n")
        } else {
            ammoniteService.updateById(oldAmmonite.id, newAmmonite)
            log.append("Updated ammonite: ${oldAmmonite.id} '${newAmmonite.taxonomySpecies}'\n")
        }
    }

    private fun importMeasurementsForAmmonites(file: MultipartFile, imagesMap: Map<String, ByteArray>, parser: ImportParser, log: StringBuilder): String {
        parser.parse(
            file.inputStream,
            log,
            { headers, _ -> log.append("Importing Ammonite Measurements with headers: $headers\n") },
            { cells, log ->
                if (cells.size >= 13) {
                    processAmmoniteMeasurementRow(
                        taxonomySpecies = cells[0],
                        diameterSide = cells[1].toDoubleOrNull(),
                        diameterCross = cells[2].toDoubleOrNull(),
                        proportionN = cells[3].toDoubleOrNull(),
                        proportionH = cells[4].toDoubleOrNull(),
                        proportionB = cells[5].toDoubleOrNull(),
                        proportionQ = cells[6].toDoubleOrNull(),
                        countPrimaryRibs = cells[7].toDoubleOrNull(),
                        countSecondaryRibs = cells[8].toDoubleOrNull(),
                        ribDivisionRatio = cells[9].toDoubleOrNull(),
                        comment = cells[10],
                        overwriteImage = cells[11].toBoolean(),
                        imageFilePath = cells[12],
                        imageBytes = imagesMap[cells[12]],
                        log = log)
                } else {
                    log.append("Invalid data format: $cells\n")
                }
            })
        return log.toString()
    }

    private fun processAmmoniteMeasurementRow(
        taxonomySpecies: String,
        diameterSide: Double?,
        diameterCross: Double?,
        proportionN: Double?,
        proportionH: Double?,
        proportionB: Double?,
        proportionQ: Double?,
        countPrimaryRibs: Double?,
        countSecondaryRibs: Double?,
        ribDivisionRatio: Double?,
        comment: String,
        overwriteImage: Boolean,
        imageFilePath: String,
        imageBytes: ByteArray?,
        log: StringBuilder
    ) {
        val ammonite = ammoniteService.findByTaxonomySpecies(taxonomySpecies)
        if (ammonite == null) {
            log.append("Ammonite not found: '$taxonomySpecies'\n")
            return
        }
        val imageId = uploadImageIfNecessary(overwriteImage, ammonite.imageId, imageFilePath, imageBytes, log)
        val measurement = measurementService.create(Measurement(null, diameterSide, diameterCross, proportionN, proportionH, proportionB, proportionQ, countPrimaryRibs, comment, ammonite.id, imageId, countSecondaryRibs, ribDivisionRatio))
        log.append("Measurement added: '$taxonomySpecies' $measurement\n")
    }

    private fun uploadImageIfNecessary(
        overwriteImage: Boolean,
        oldImageId: Int?,
        imageFilePath: String,
        imageBytes: ByteArray?,
        log: StringBuilder
    ): Int? {
        val imageId = if (overwriteImage || oldImageId == null) {
            if (imageFilePath.isNullOrEmpty()) {
                null
            } else {
                if (imageBytes == null) {
                    log.append("Image file not found: $imageFilePath\n")
                    null
                } else {
                    val base64Image = Base64.getEncoder().encodeToString(imageBytes)
                    val fileName = File(imageFilePath).name
                    val mediaType = URLConnection.guessContentTypeFromName(fileName)
                    val image = Image(null, fileName, base64Image, mediaType)
                    val savedImage = imageService.save(image)
                    savedImage.id
                }
            }
        } else {
            oldImageId
        }
        return imageId
    }

    fun convertToMap(images: List<MultipartFile>): Map<String, ByteArray> {
        val imageMap = mutableMapOf<String, ByteArray>()

        images.forEach { file ->
            val fileName = file.originalFilename ?: throw IllegalStateException("File must have a name")
            val fileBytes = file.bytes
            imageMap[fileName] = fileBytes
        }

        return imageMap
    }

    enum class ImportType {
        AMMONITES,
        AMMONITE_MEASUREMENTS,
        UNKNOWN
    }
}

