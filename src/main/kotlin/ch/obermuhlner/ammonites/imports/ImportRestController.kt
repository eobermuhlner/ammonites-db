package ch.obermuhlner.ammonites.imports

import ch.obermuhlner.ammonites.ammonite.AmmoniteService
import ch.obermuhlner.ammonites.image.ImageService
import ch.obermuhlner.ammonites.jooq.tables.pojos.Image
import org.apache.poi.ss.usermodel.WorkbookFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets
import java.util.*


@RestController
@RequestMapping("/api/import")
class ImportRestController @Autowired constructor(
    private val ammoniteService: AmmoniteService,
    private val imageService: ImageService
) {

    @PostMapping("/ammonite/images")
    fun importImagesForAmmonites(@RequestParam("file") file: MultipartFile): ResponseEntity<String> {
        if (file.isEmpty) {
            return ResponseEntity.badRequest().body("File is empty")
        }

        val result = StringBuilder()

        val contentType = file.contentType
        when {
            contentType.equals("application/vnd.ms-excel", ignoreCase = true) ||
                    contentType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", ignoreCase = true) -> {
                // Process Excel file
                val workbook = WorkbookFactory.create(file.inputStream)
                val sheet = workbook.getSheetAt(0)
                sheet.forEach { row ->
                    if (row.rowNum > 0) {
                        result.append(processAmmoniteImageRow(row.getCell(0).stringCellValue, row.getCell(1).booleanCellValue, row.getCell(2).stringCellValue))
                        result.append("\n")
                    }
                }
                workbook.close()
            }
            contentType.equals("text/csv", ignoreCase = true) -> {
                // Process CSV file
                val bufferedReader = BufferedReader(InputStreamReader(file.inputStream, StandardCharsets.UTF_8))
                var rowCount = 0
                bufferedReader.useLines { lines ->
                    lines
                        .filter { line -> !line.startsWith("#") }
                        .forEach { line ->
                            if (rowCount > 0) {
                                val parts = line.split(',')
                                if (parts.size >= 3) {
                                    result.append(processAmmoniteImageRow(parts[0].trim(), parts[1].trim().toBoolean(), parts[2].trim()))
                                    result.append("\n")
                                }
                            }
                            rowCount++
                        }
                }
            }
            else -> {
                return ResponseEntity.badRequest().body("Unsupported file type")
            }
        }
        return ResponseEntity.ok("Import successful:\n$result")
    }

    private fun processAmmoniteImageRow(taxonomySpecies: String, overwrite: Boolean, importFilePath: String): String {
        val ammonite = ammoniteService.findByTaxonomySpecies(taxonomySpecies)
        if (ammonite == null) {
            return "Ammonite not found: '$taxonomySpecies'"
        }
        if (ammonite.imageId != null && !overwrite) {
            return "Ammonite already has image: '$taxonomySpecies' id=${ammonite.id} imageId=${ammonite.imageId}"
        }
        val imageFile = File(importFilePath)
        if (!imageFile.exists()) {
            return "Image file not found: $importFilePath"
        }
        val imageBytes = imageFile.readBytes()
        val base64Image = Base64.getEncoder().encodeToString(imageBytes)
        val name = imageFile.name
        val imageDto = Image(null, name, base64Image, "image/jpeg") // Assuming JPEG for simplicity
        val savedImage = imageService.save(imageDto)

        val oldImageId = ammonite.imageId
        ammonite.imageId = savedImage.id
        ammoniteService.updateById(ammonite.id, ammonite)
        return "Ammonite image set: $taxonomySpecies id=${ammonite.id} imageId=${ammonite.imageId} oldImageId=${oldImageId}"
    }
}
