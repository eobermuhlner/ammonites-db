package ch.obermuhlner.ammonites.ammonite

import ch.obermuhlner.ammonites.jooq.Tables
import ch.obermuhlner.ammonites.jooq.tables.pojos.Ammonite
import ch.obermuhlner.ammonites.jooq.tables.pojos.Measurement
import org.jooq.Record
import org.springframework.stereotype.Service

@Service
class AmmoniteService(private val ammoniteRepository: AmmoniteRepository) {

    fun create(ammonite: Ammonite): Ammonite {
        if (ammonite.id != null) {
            throw IllegalArgumentException("Not allowed to create with explicit id")
        }
        return ammoniteRepository.save(ammonite)
    }

    fun findAll(): List<Ammonite> {
        return ammoniteRepository.findAll()
    }

    fun findById(id: Int): Ammonite? {
        return ammoniteRepository.findById(id).orElse(null)
    }

    fun updateById(id: Int, updatedAmmonite: Ammonite): Ammonite? {
        if (ammoniteRepository.existsById(id)) {
            updatedAmmonite.id = id
            return ammoniteRepository.save(updatedAmmonite)
        }
        return null
    }

    fun deleteById(id: Int): Boolean {
        return ammoniteRepository.deleteById(id)
    }

    data class AmmoniteWithMeasurement(val distance: Double, val ammonite: Ammonite, val measurement: Measurement)

    fun findMatchingAmmonitesWithMeasurements(
        diameterSide: Double? = null,
        diameterCross: Double? = null,
        proportion_n: Double? = null,
        proportion_h: Double? = null,
        proportion_b: Double? = null,
        proportion_q: Double? = null,
        count_z: Double? = null,
        limit: Int = 5
    ): List<AmmoniteWithMeasurement> {
        val records = ammoniteRepository.findMatchingAmmonitesWithMeasurements()
        return records
            .map { record ->
                Pair(sumOfRelativeSquareErrors(record!!, diameterSide, diameterCross, proportion_n, proportion_h, proportion_b, proportion_q, count_z), record)
            }
            .sortedBy { pair -> pair.first }
            .take(limit)
            .map { pair ->
                val distance = pair.first
                val record = pair.second
                val ammonite = record.into(Ammonite::class.java)
                val measurement = record.into(Measurement::class.java)
                AmmoniteWithMeasurement(distance, ammonite, measurement)
            }
    }

    fun sumOfRelativeSquareErrors(
        record: Record,
        diameterSide: Double? = null,
        diameterCross: Double? = null,
        proportion_n: Double? = null,
        proportion_h: Double? = null,
        proportion_b: Double? = null,
        proportion_q: Double? = null,
        count_z: Double? = null,
        weightDiameterSide: Double = 0.1,
        weightDiameterCross: Double = 0.1,
        weightProportion_n: Double = 1.0,
        weightProportion_h: Double = 1.0,
        weightProportion_b: Double = 1.0,
        weightProportion_q: Double = 1.0,
        weightCount_z: Double = 1.0,
    ): Double {
        var sum = 0.0

        sum += relativeSquareError(diameterSide, record[Tables.MEASUREMENT.DIAMETER_SIDE]) * weightDiameterSide
        sum += relativeSquareError(diameterCross, record[Tables.MEASUREMENT.DIAMETER_CROSS]) * weightDiameterCross
        sum += relativeSquareError(proportion_n, record[Tables.MEASUREMENT.PROPORTION_N]) * weightProportion_n
        sum += relativeSquareError(proportion_h, record[Tables.MEASUREMENT.PROPORTION_H]) * weightProportion_h
        sum += relativeSquareError(proportion_b, record[Tables.MEASUREMENT.PROPORTION_B]) * weightProportion_b
        sum += relativeSquareError(proportion_q, record[Tables.MEASUREMENT.PROPORTION_Q]) * weightProportion_q
        sum += relativeSquareError(count_z, record[Tables.MEASUREMENT.COUNT_Z]) * weightCount_z

        return sum
    }

    private fun relativeSquareError(value: Double?, referenceValue: Double?): Double {
        if (value == null || referenceValue == null) {
            return 0.0
        }

        val err = (value - referenceValue) / referenceValue
        return err * err
    }
}
