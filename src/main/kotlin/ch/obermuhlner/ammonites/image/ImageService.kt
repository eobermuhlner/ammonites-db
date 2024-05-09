package ch.obermuhlner.ammonites.image

import ch.obermuhlner.ammonites.jooq.tables.pojos.Image
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ImageService(@Autowired private val repository: ImageRepository) {

    fun save(image: Image): Image = repository.save(image)

    fun findById(id: Int): Image? = repository.findById(id)

    fun findAll(): List<Image> = repository.findAll()

    fun update(image: Image): Image = repository.update(image)

    fun delete(id: Int) = repository.deleteById(id)
}
