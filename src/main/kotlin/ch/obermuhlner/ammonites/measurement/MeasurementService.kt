package ch.obermuhlner.ammonites.measurement

import ch.obermuhlner.ammonites.jooq.Tables
import ch.obermuhlner.ammonites.jooq.tables.pojos.Ammonite
import ch.obermuhlner.ammonites.jooq.tables.pojos.Measurement
import org.jooq.Record
import org.springframework.stereotype.Service
import java.util.*

@Service
class MeasurementService(private val measurementRepository: MeasurementRepository) {

    fun create(measurement: Measurement): Measurement {
        if (measurement.id != null) {
            throw IllegalArgumentException("Not allowed to create with explicit id")
        }
        return measurementRepository.save(measurement)
    }

    fun findAll(ammoniteId: Int): List<Measurement> {
        return measurementRepository.findAll(ammoniteId)
    }

    fun findById(id: Int): Measurement? {
        return measurementRepository.findById(id).orElse(null)
    }

    fun updateById(id: Int, modified: Measurement): Measurement? {
        if (measurementRepository.existsById(id)) {
            modified.id = id
            return measurementRepository.save(modified)
        }
        return null
    }

    fun deleteById(id: Int): Boolean {
        return measurementRepository.deleteById(id)
    }
}
