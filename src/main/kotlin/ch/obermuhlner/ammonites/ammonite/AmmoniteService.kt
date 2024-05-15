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

    fun findByTaxonomySpecies(taxonomySpecies: String): Ammonite? {
        return ammoniteRepository.findByTaxonomySpecies(taxonomySpecies).orElse(null)
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
        count_primary_ribs: Double? = null,
        count_secondary_ribs: Double? = null,
        rib_division_ratio: Double? = null,
        limit: Int = 10
    ): List<AmmoniteWithMeasurement> {
        val records = ammoniteRepository.findMatchingAmmonitesWithMeasurements()

        val filteredRecords = records
            .mapNotNull { record ->
                Pair(matchingDistance(record, diameterSide, diameterCross, proportion_n, proportion_h, proportion_b, proportion_q, count_primary_ribs), record)
            }
            .sortedBy { it.first }
            .map { pair ->
                val distance = pair.first
                val record = pair.second

                val ammonite = Ammonite(
                    record.get(Tables.AMMONITE.ID),
                    record.get(Tables.AMMONITE.TAXONOMY_SUBCLASS),
                    record.get(Tables.AMMONITE.TAXONOMY_FAMILY),
                    record.get(Tables.AMMONITE.TAXONOMY_SUBFAMILY),
                    record.get(Tables.AMMONITE.TAXONOMY_GENUS),
                    record.get(Tables.AMMONITE.TAXONOMY_SUBGENUS),
                    record.get(Tables.AMMONITE.TAXONOMY_SPECIES),
                    record.get(Tables.AMMONITE.STRATA),
                    record.get(Tables.AMMONITE.DESCRIPTION),
                    record.get(Tables.AMMONITE.COMMENT),
                    record.get(Tables.AMMONITE.IMAGE_ID)
                )

                val measurement = Measurement(
                    record.get(Tables.MEASUREMENT.ID),
                    record.get(Tables.MEASUREMENT.DIAMETER_SIDE),
                    record.get(Tables.MEASUREMENT.DIAMETER_CROSS),
                    record.get(Tables.MEASUREMENT.PROPORTION_N),
                    record.get(Tables.MEASUREMENT.PROPORTION_H),
                    record.get(Tables.MEASUREMENT.PROPORTION_B),
                    record.get(Tables.MEASUREMENT.PROPORTION_Q),
                    record.get(Tables.MEASUREMENT.COUNT_PRIMARY_RIBS),
                    record.get(Tables.MEASUREMENT.COMMENT),
                    record.get(Tables.MEASUREMENT.AMMONITE_ID),
                    record.get(Tables.MEASUREMENT.IMAGE_ID),
                    record.get(Tables.MEASUREMENT.COUNT_SECONDARY_RIBS),
                    record.get(Tables.MEASUREMENT.RIB_DIVISION_RATIO),
                    )

                AmmoniteWithMeasurement(distance, ammonite, measurement)
            }
            .distinctBy { it.ammonite.taxonomySpecies }
            .take(limit)

        return filteredRecords
    }

    fun matchingDistance(
        record: Record,
        diameterSide: Double? = null,
        diameterCross: Double? = null,
        proportion_n: Double? = null,
        proportion_h: Double? = null,
        proportion_b: Double? = null,
        proportion_q: Double? = null,
        count_primary_ribs: Double? = null,
        count_secondary_ribs: Double? = null,
        rib_division_ratio: Double? = null,
        weightDiameterSide: Double = 0.1,
        weightDiameterCross: Double = 0.2,
        weightProportion_n: Double = 1.0,
        weightProportion_h: Double = 1.0,
        weightProportion_b: Double = 1.0,
        weightProportion_q: Double = 1.0,
        weightCount_primary_ribs: Double = 1.0,
        weightCount_secondary_ribs: Double = 1.0,
        weightRib_division_ratio: Double = 1.0,
    ): Double {
        var sum = 0.0

        sum += relativeSquareError(diameterSide, record[Tables.MEASUREMENT.DIAMETER_SIDE]) * weightDiameterSide
        sum += relativeSquareError(diameterCross, record[Tables.MEASUREMENT.DIAMETER_CROSS]) * weightDiameterCross
        sum += relativeSquareError(proportion_n, record[Tables.MEASUREMENT.PROPORTION_N]) * weightProportion_n
        sum += relativeSquareError(proportion_h, record[Tables.MEASUREMENT.PROPORTION_H]) * weightProportion_h
        sum += relativeSquareError(proportion_b, record[Tables.MEASUREMENT.PROPORTION_B]) * weightProportion_b
        sum += relativeSquareError(proportion_q, record[Tables.MEASUREMENT.PROPORTION_Q]) * weightProportion_q
        sum += relativeSquareError(count_primary_ribs, record[Tables.MEASUREMENT.COUNT_PRIMARY_RIBS]) * weightCount_primary_ribs
        sum += relativeSquareError(count_secondary_ribs, record[Tables.MEASUREMENT.COUNT_SECONDARY_RIBS]) * weightCount_secondary_ribs
        sum += relativeSquareError(rib_division_ratio, record[Tables.MEASUREMENT.RIB_DIVISION_RATIO]) * weightRib_division_ratio

        var count = 0
        count += isAvailable(diameterSide, record[Tables.MEASUREMENT.DIAMETER_SIDE])
        count += isAvailable(diameterCross, record[Tables.MEASUREMENT.DIAMETER_CROSS])
        count += isAvailable(proportion_n, record[Tables.MEASUREMENT.PROPORTION_N])
        count += isAvailable(proportion_h, record[Tables.MEASUREMENT.PROPORTION_H])
        count += isAvailable(proportion_b, record[Tables.MEASUREMENT.PROPORTION_B])
        count += isAvailable(proportion_q, record[Tables.MEASUREMENT.PROPORTION_Q])
        count += isAvailable(count_primary_ribs, record[Tables.MEASUREMENT.COUNT_PRIMARY_RIBS])
        count += isAvailable(count_secondary_ribs, record[Tables.MEASUREMENT.COUNT_SECONDARY_RIBS])
        count += isAvailable(rib_division_ratio, record[Tables.MEASUREMENT.RIB_DIVISION_RATIO])

        if (count == 0) {
            return 1.0
        }

        return sum / count
    }

    private fun isAvailable(sampleValue: Double?, referenceValue: Double?): Int {
        return if (sampleValue != null && referenceValue != null) 1 else 0
    }

    private fun relativeSquareError(value: Double?, referenceValue: Double?): Double {
        if (value == null || referenceValue == null) {
            return 0.0
        }

        val err = (value - referenceValue) / referenceValue
        return err * err
    }
}
