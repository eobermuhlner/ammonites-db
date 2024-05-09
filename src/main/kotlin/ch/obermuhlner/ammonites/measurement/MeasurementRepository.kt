package ch.obermuhlner.ammonites.measurement

import ch.obermuhlner.ammonites.jooq.Tables.MEASUREMENT
import ch.obermuhlner.ammonites.jooq.tables.pojos.Measurement
import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class MeasurementRepository(private val dsl: DSLContext) {

    fun findAll(ammoniteId: Int): List<Measurement> {
        return dsl.selectFrom(MEASUREMENT).where(MEASUREMENT.AMMONITE_ID.eq(ammoniteId)).fetchInto(Measurement::class.java)
    }

    fun findById(id: Int): Optional<Measurement> {
        return dsl.selectFrom(MEASUREMENT).where(MEASUREMENT.ID.eq(id)).fetchOptionalInto(Measurement::class.java)
    }

    fun existsById(id: Int): Boolean {
        return dsl.fetchExists(dsl.selectFrom(MEASUREMENT).where(MEASUREMENT.ID.eq(id)))
    }

    fun save(measurement: Measurement): Measurement {
        if (measurement.id == null) {
            val record = dsl.newRecord(MEASUREMENT, measurement)
            record.store()
            return record.into(Measurement::class.java)
        } else {
            // Update existing record
            dsl.update(MEASUREMENT)
                .set(MEASUREMENT.DIAMETER_SIDE, measurement.diameterSide)
                .set(MEASUREMENT.DIAMETER_CROSS, measurement.diameterCross)
                .set(MEASUREMENT.PROPORTION_N, measurement.proportionN)
                .set(MEASUREMENT.PROPORTION_H, measurement.proportionH)
                .set(MEASUREMENT.PROPORTION_B, measurement.proportionB)
                .set(MEASUREMENT.PROPORTION_Q, measurement.proportionQ)
                .set(MEASUREMENT.COUNT_Z, measurement.countZ)
                .set(MEASUREMENT.COMMENT, measurement.comment)
                .set(MEASUREMENT.AMMONITE_ID, measurement.ammoniteId)
                .set(MEASUREMENT.IMAGE_ID, measurement.imageId)
                .where(MEASUREMENT.ID.eq(measurement.id))
                .execute()
            return measurement
        }
    }

    fun deleteById(id: Int): Boolean {
        return dsl.deleteFrom(MEASUREMENT).where(MEASUREMENT.ID.eq(id)).execute() == 1
    }
}