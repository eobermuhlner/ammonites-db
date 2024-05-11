package ch.obermuhlner.ammonites.imports

import ch.obermuhlner.ammonites.ammonite.AmmoniteService
import ch.obermuhlner.ammonites.image.ImageService
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
    @PostMapping("/ammonites")
    fun importAmmonites(@RequestParam("file") file: MultipartFile): ResponseEntity<String> {
        if (file.isEmpty) {
            return ResponseEntity.badRequest().body("File is empty")
        }

        val parser = ImportParserFactory().getParser(file.contentType)
        val result = parser.parse(file.inputStream,
            { headers, log -> log.append("Headers: $headers\n") },
            { cells, log ->
                if (cells.size >= 11) {
                    processAmmoniteRow(
                        taxonomySubclass = cells[0],
                        taxonomyFamily = cells[1],
                        taxonomySubfamily = cells[2],
                        taxonomyGenus = cells[3],
                        taxonomySubgenus = cells[4],
                        taxonomySpecies = cells[5],
                        strata = cells[6],
                        description = cells[7],
                        comment = cells[8],
                        overwriteImage = cells[9].toBoolean(),
                        imageFilePath = cells[10],
                        log)
                } else {
                    log.append("Invalid data format: $cells")
                }
            })
        return ResponseEntity.ok("Import successful:\n$result")
    }

    private fun processAmmoniteRow(
        taxonomySubclass: String,
        taxonomyFamily: String,
        taxonomySubfamily: String,
        taxonomyGenus: String,
        taxonomySubgenus: String,
        taxonomySpecies: String,
        strata: String,
        description: String,
        comment: String,
        overwriteImage: Boolean,
        imageFilePath: String,
        log: StringBuilder
    ) {
        val oldAmmonite = ammoniteService.findByTaxonomySpecies(taxonomySpecies)
        val imageId = uploadImageIfNecessary(overwriteImage, oldAmmonite?.imageId, imageFilePath, log)
        val newAmmonite = Ammonite(null, taxonomySubclass, taxonomyFamily, taxonomySubfamily, taxonomyGenus, taxonomySubgenus, taxonomySpecies, strata, description, comment, imageId)

        if (oldAmmonite == null) {
            val id = ammoniteService.create(newAmmonite)
            log.append("New ammonite: $id '${newAmmonite.taxonomySpecies}'\n")
        } else {
            ammoniteService.updateById(oldAmmonite.id, newAmmonite)
            log.append("Updated ammonite: ${oldAmmonite.id} '${newAmmonite.taxonomySpecies}'\n")
        }
    }

    @PostMapping("/ammonite/measurements")
    fun importMeasurementsForAmmonites(@RequestParam("file") file: MultipartFile): ResponseEntity<String> {
        if (file.isEmpty) {
            return ResponseEntity.badRequest().body("File is empty")
        }

        val parser = ImportParserFactory().getParser(file.contentType)
        val result = parser.parse(file.inputStream,
            { headers, log -> log.append("Headers: $headers\n") },
            { cells, log ->
                if (cells.size >= 11) {
                    processAmmoniteMeasurementRow(
                        taxonomySpecies = cells[0],
                        diameterSide = cells[1].toDoubleOrNull(),
                        diameterCross = cells[2].toDoubleOrNull(),
                        proportionN = cells[3].toDoubleOrNull(),
                        proportionH = cells[4].toDoubleOrNull(),
                        proportionB = cells[5].toDoubleOrNull(),
                        proportionQ = cells[6].toDoubleOrNull(),
                        countZ = cells[7].toDoubleOrNull(),
                        comment = cells[8],
                        cells[9].toBoolean(),
                        cells[10],
                        log)
                } else {
                    log.append("Invalid data format: $cells")
                }
            })
        return ResponseEntity.ok("Import successful:\n$result")
    }

    private fun processAmmoniteMeasurementRow(
        taxonomySpecies: String,
        diameterSide: Double?,
        diameterCross: Double?,
        proportionN: Double?,
        proportionH: Double?,
        proportionB: Double?,
        proportionQ: Double?,
        countZ: Double?,
        comment: String,
        overwriteImage: Boolean,
        imageFilePath: String,
        log: StringBuilder
    ) {
        val ammonite = ammoniteService.findByTaxonomySpecies(taxonomySpecies)
        if (ammonite == null) {
            log.append("Ammonite not found: '$taxonomySpecies'\n")
            return
        }
        val imageId = uploadImageIfNecessary(overwriteImage, ammonite.imageId, imageFilePath, log)
        val measurement = measurementService.create(Measurement(null, diameterSide, diameterCross, proportionN, proportionH, proportionB, proportionQ, countZ, comment, ammonite.id, imageId))
        log.append("Measurement added: '$taxonomySpecies' $measurement\n")
    }

    @PostMapping("/ammonite/images")
    fun importImagesForAmmonites(@RequestParam("file") file: MultipartFile): ResponseEntity<String> {
        if (file.isEmpty) {
            return ResponseEntity.badRequest().body("File is empty")
        }

        val parser = ImportParserFactory().getParser(file.contentType)
        val result = parser.parse(file.inputStream,
            { headers, log -> log.append("Headers: $headers\n") },
            { cells, log ->
                if (cells.size >= 3) {
                    processAmmoniteImageRow(cells[0], cells[1].toBoolean(), cells[2], log)
                } else {
                    log.append("Invalid data format: $cells\n")
                }
            })
        return ResponseEntity.ok("Import successful:\n$result")
    }

    private fun processAmmoniteImageRow(taxonomySpecies: String, overwriteImage: Boolean, imageFilePath: String, log: StringBuilder) {
        val ammonite = ammoniteService.findByTaxonomySpecies(taxonomySpecies)
        if (ammonite == null) {
            log.append("Ammonite not found: '$taxonomySpecies'\n")
            return
        }
        if (ammonite.imageId != null && !overwriteImage) {
            log.append("Ammonite already has image: '$taxonomySpecies' id=${ammonite.id} imageId=${ammonite.imageId}\n")
            return
        }
        val imageFile = File(imageFilePath)
        if (!imageFile.exists()) {
            log.append("Image file not found: $imageFilePath\n")
            return
        }
        val imageBytes = imageFile.readBytes()
        val base64Image = Base64.getEncoder().encodeToString(imageBytes)
        val name = imageFile.name
        val mediaType = URLConnection.guessContentTypeFromName(name)
        val image = Image(null, name, base64Image, mediaType)
        val savedImage = imageService.save(image)

        val oldImageId = ammonite.imageId
        ammonite.imageId = savedImage.id
        ammoniteService.updateById(ammonite.id, ammonite)
        log.append("Ammonite image set: $taxonomySpecies id=${ammonite.id} imageId=${ammonite.imageId} oldImageId=${oldImageId}\n")
    }

    private fun uploadImageIfNecessary(
        overwriteImage: Boolean,
        oldImageId: Int?,
        imageFilePath: String,
        log: StringBuilder
    ): Int? {
        val imageId = if (overwriteImage || oldImageId == null) {
            if (imageFilePath.isNullOrEmpty()) {
                null
            } else {
                val imageFile = File(imageFilePath)
                if (imageFile.exists()) {
                    val imageBytes = imageFile.readBytes()
                    val base64Image = Base64.getEncoder().encodeToString(imageBytes)
                    val name = imageFile.name
                    val mediaType = URLConnection.guessContentTypeFromName(name)
                    val image = Image(null, name, base64Image, mediaType)
                    val savedImage = imageService.save(image)
                    savedImage.id
                } else {
                    log.append("Image file not found: $imageFilePath\n")
                    null
                }
            }
        } else {
            oldImageId
        }
        return imageId
    }

}
