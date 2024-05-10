package ch.obermuhlner.ammonites.ammonite

import ch.obermuhlner.ammonites.jooq.Tables
import ch.obermuhlner.ammonites.jooq.Tables.AMMONITE
import ch.obermuhlner.ammonites.jooq.tables.pojos.Ammonite
import org.jooq.DSLContext
import org.jooq.Record
import org.jooq.Result
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class AmmoniteRepository(private val dsl: DSLContext) {

    fun findAll(): List<Ammonite> {
        return dsl.selectFrom(AMMONITE).fetchInto(Ammonite::class.java)
    }

    fun findById(id: Int): Optional<Ammonite> {
        return dsl.selectFrom(AMMONITE).where(AMMONITE.ID.eq(id)).fetchOptionalInto(Ammonite::class.java)
    }

    fun existsById(id: Int): Boolean {
        return dsl.fetchExists(dsl.selectFrom(AMMONITE).where(AMMONITE.ID.eq(id)))
    }

    fun save(ammonite: Ammonite): Ammonite {
        if (ammonite.id == null) {
            val record = dsl.newRecord(AMMONITE, ammonite)
            record.store()
            return record.into(Ammonite::class.java)
        } else {
            // Update existing record
            dsl.update(AMMONITE)
                .set(AMMONITE.TAXONOMY_SUBCLASS, ammonite.taxonomySubclass)
                .set(AMMONITE.TAXONOMY_FAMILY, ammonite.taxonomyFamily)
                .set(AMMONITE.TAXONOMY_SUBFAMILY, ammonite.taxonomySubfamily)
                .set(AMMONITE.TAXONOMY_GENUS, ammonite.taxonomyGenus)
                .set(AMMONITE.TAXONOMY_SUBGENUS, ammonite.taxonomySubgenus)
                .set(AMMONITE.TAXONOMY_SPECIES, ammonite.taxonomySpecies)
                .set(AMMONITE.DESCRIPTION, ammonite.description)
                .set(AMMONITE.STRATA, ammonite.strata)
                .set(AMMONITE.IMAGE_ID, ammonite.imageId)
                .where(AMMONITE.ID.eq(ammonite.id))
                .execute()
            return ammonite
        }
    }

    fun deleteById(id: Int): Boolean {
        return dsl.deleteFrom(AMMONITE).where(AMMONITE.ID.eq(id)).execute() == 1
    }

    fun findMatchingAmmonitesWithMeasurements(): Result<Record> {
        return dsl.select()
            .from(AMMONITE)
            .join(Tables.MEASUREMENT).on(AMMONITE.ID.eq(Tables.MEASUREMENT.AMMONITE_ID))
            .fetch()
    }
}