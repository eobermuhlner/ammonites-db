package ch.obermuhlner.ammonites.ammonite

import ch.obermuhlner.ammonites.jooq.Tables
import ch.obermuhlner.ammonites.jooq.Tables.AMMONITE
import ch.obermuhlner.ammonites.jooq.Tables.MEASUREMENT
import ch.obermuhlner.ammonites.jooq.tables.records.AmmoniteRecord
import org.jooq.DSLContext
import org.jooq.Record
import org.jooq.Result
import org.springframework.stereotype.Service
import java.util.*

@Service
class AmmoniteService(private val dsl: DSLContext) {
    fun fetchAllAmmonites(): Result<AmmoniteRecord> {
        return dsl.selectFrom(AMMONITE).fetch()
    }

    fun fetchAllAmmonitesWithMeasurements(): Result<Record> {
        return dsl.select()
            .from(AMMONITE)
            .join(MEASUREMENT).on(AMMONITE.ID.eq(MEASUREMENT.AMMONITE_ID))
            .fetch()
    }

    fun fetchMatchingAmmonites(
        diameterSide: Double? = null,
        diameterCross: Double? = null,
        proportion_n: Double? = null,
        proportion_h: Double? = null,
        proportion_b: Double? = null,
        proportion_q: Double? = null,
        count_z: Double? = null,
        factor: Double = 1.5
    ): Result<Record> {
        var query = dsl.select()
            .from(AMMONITE)
            .join(MEASUREMENT).on(AMMONITE.ID.eq(MEASUREMENT.AMMONITE_ID))
            .where()

        if (diameterSide != null) {
            query = query.and(MEASUREMENT.DIAMETER_SIDE.between(diameterSide/factor, diameterSide*factor))
        }
        if (diameterCross != null) {
            query = query.and(MEASUREMENT.DIAMETER_CROSS.isNull.or(MEASUREMENT.DIAMETER_CROSS.between(diameterCross/factor, diameterCross*factor)))
        }
        if (proportion_n != null) {
            query = query.and(MEASUREMENT.PROPORTION_N.isNull.or(MEASUREMENT.PROPORTION_N.between(proportion_n/factor, proportion_n*factor)))
        }
        if (proportion_h != null) {
            query = query.and(MEASUREMENT.PROPORTION_H.isNull.or(MEASUREMENT.PROPORTION_H.between(proportion_h/factor, proportion_h*factor)))
        }
        if (proportion_b != null) {
            query = query.and(MEASUREMENT.PROPORTION_B.isNull.or(MEASUREMENT.PROPORTION_B.between(proportion_b/factor, proportion_b*factor)))
        }
        if (proportion_q != null) {
            query = query.and(MEASUREMENT.PROPORTION_Q.isNull.or(MEASUREMENT.PROPORTION_Q.between(proportion_q/factor, proportion_q*factor)))
        }
        if (count_z != null) {
            query = query.and(MEASUREMENT.COUNT_Z.isNull.or(MEASUREMENT.COUNT_Z.between((count_z/factor), (count_z*factor))))
        }

        return query.fetch()
    }

    fun searchMatchingAmmonites(
        ammoniteService: AmmoniteService,
        diameterSide: Double? = null,
        diameterCross: Double? = null,
        proportion_n: Double? = null,
        proportion_h: Double? = null,
        proportion_b: Double? = null,
        proportion_q: Double? = null,
        count_z: Double? = null,
        limit: Int = 5
    ): Map<Double, Record> {
        val records = ammoniteService.fetchAllAmmonitesWithMeasurements()
        return records
            .map { record -> Pair(sumOfRelativeSquareErrors(record!!, diameterSide, diameterCross, proportion_n, proportion_h, proportion_b, proportion_q, count_z), record) }
            .sortedBy { pair -> pair.first }
            .take(limit)
            .toMap(TreeMap())
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
