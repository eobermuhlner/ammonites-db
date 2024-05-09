package ch.obermuhlner.ammonites.image

import ch.obermuhlner.ammonites.jooq.tables.pojos.Image
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.util.Base64

@RestController
@RequestMapping("/api/images")
class ImageRestController(@Autowired private val imageService: ImageService) {

    @PostMapping("/upload")
    fun uploadImage(@RequestParam("image") file: MultipartFile): ResponseEntity<Image> {
        if (file.isEmpty || file.contentType == null || !file.contentType!!.startsWith("image")) {
            return ResponseEntity(HttpStatus.BAD_REQUEST)
        }

        val imageBytes = file.bytes
        val base64Image = Base64.getEncoder().encodeToString(imageBytes)
        val name = file.originalFilename?.substringAfterLast("/")?:""
        val imageDto = Image(null, name, base64Image, file.contentType!!)

        val savedImage = imageService.save(imageDto)
        return ResponseEntity.ok(savedImage)
    }

    @GetMapping("/{id}")
    fun getImage(@PathVariable id: Int): ResponseEntity<Any> {
        val imageDto = imageService.findById(id) ?: return ResponseEntity.notFound().build()
        val imageBytes = Base64.getDecoder().decode(imageDto.data)
        val mediaType = MediaType.parseMediaType(imageDto.mediaType)
        return ResponseEntity.ok().contentType(mediaType).body(imageBytes)
    }

    @GetMapping
    fun getAllImages(): ResponseEntity<List<Image>> = ResponseEntity.ok(imageService.findAll())

    @PutMapping("/{id}")
    fun updateImage(@PathVariable id: Int, @RequestParam("file") file: MultipartFile): ResponseEntity<Image> {
        if (file.isEmpty || file.contentType == null || !file.contentType!!.startsWith("image")) {
            return ResponseEntity(HttpStatus.BAD_REQUEST)
        }

        val imageBytes = file.bytes
        val base64Image = Base64.getEncoder().encodeToString(imageBytes)
        val imageDto = Image(id, "", base64Image, file.contentType!!)

        val updatedImage = imageService.update(imageDto)
        return ResponseEntity.ok(updatedImage)
    }

    @DeleteMapping("/{id}")
    fun deleteImage(@PathVariable id: Int): ResponseEntity<Void> {
        imageService.delete(id)
        return ResponseEntity.noContent().build()
    }
}
