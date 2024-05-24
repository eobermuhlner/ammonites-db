package ch.obermuhlner.ammonites.image

import ch.obermuhlner.ammonites.jooq.tables.pojos.Image
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ImageService(@Autowired private val repository: ImageRepository) {

    @Transactional
    fun save(image: Image): Image = repository.save(image)

    @Transactional(readOnly = true)
    fun findById(id: Int): Image? = repository.findById(id)

    @Transactional(readOnly = true)
    fun findAll(): List<Image> = repository.findAll()

    @Transactional
    fun update(image: Image): Image = repository.update(image)

    @Transactional
    fun delete(id: Int) = repository.deleteById(id)
}
