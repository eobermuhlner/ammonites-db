package ch.obermuhlner.ammonites.image

import ch.obermuhlner.ammonites.jooq.Tables.IMAGE
import ch.obermuhlner.ammonites.jooq.tables.pojos.Image
import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import org.springframework.beans.factory.annotation.Autowired

@Repository
class ImageRepository(@Autowired private val dsl: DSLContext) {

    fun save(image: Image): Image {
        val record = dsl.insertInto(IMAGE, IMAGE.NAME, IMAGE.DATA, IMAGE.MEDIA_TYPE)
            .values(image.name, image.data, image.mediaType)
            .returning(IMAGE.ID)
            .fetchOne()!!
        image.id = record.getValue(IMAGE.ID)
        return image
    }

    fun findById(id: Int): Image? {
        val record = dsl.selectFrom(IMAGE)
            .where(IMAGE.ID.eq(id))
            .fetchOne() ?: return null
        return Image(
            record.getValue(IMAGE.ID),
            record.getValue(IMAGE.NAME),
            record.getValue(IMAGE.DATA),
            record.getValue(IMAGE.MEDIA_TYPE)
        )
    }

    fun findAll(): List<Image> {
        return dsl.selectFrom(IMAGE)
            .fetch()
            .map { record ->
                Image(
                    record.getValue(IMAGE.ID),
                    record.getValue(IMAGE.NAME),
                    record.getValue(IMAGE.DATA),
                    record.getValue(IMAGE.MEDIA_TYPE),
                )
            }
    }

    fun update(image: Image): Image {
        dsl.update(IMAGE)
            .set(IMAGE.NAME, image.name)
            .set(IMAGE.DATA, image.data)
            .set(IMAGE.MEDIA_TYPE, image.mediaType)
            .where(IMAGE.ID.eq(image.id))
            .execute()
        return image
    }

    fun deleteById(id: Int) {
        dsl.deleteFrom(IMAGE)
            .where(IMAGE.ID.eq(id))
            .execute()
    }
}
