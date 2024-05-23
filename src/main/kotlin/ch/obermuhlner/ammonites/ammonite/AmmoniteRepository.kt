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

    fun findDistinctSubclass(): List<String> {
        return dsl.selectDistinct(AMMONITE.TAXONOMY_SUBCLASS)
            .from(AMMONITE)
            .fetch(AMMONITE.TAXONOMY_SUBCLASS)
    }

    fun findDistinctFamily(subclass: String?): List<String> {
        val query = dsl.selectDistinct(AMMONITE.TAXONOMY_FAMILY)
            .from(AMMONITE)
        if (subclass != null) {
            query.where(AMMONITE.TAXONOMY_SUBCLASS.eq(subclass))
        }
        return query.fetch(AMMONITE.TAXONOMY_FAMILY)
    }

    fun findDistinctSubfamily(family: String?): List<String> {
        val query = dsl.selectDistinct(AMMONITE.TAXONOMY_SUBFAMILY)
            .from(AMMONITE)
        if (family != null) {
            query.where(AMMONITE.TAXONOMY_FAMILY.eq(family))
        }
        return query.fetch(AMMONITE.TAXONOMY_SUBFAMILY)
    }

    fun findDistinctGenus(subfamily: String?): List<String> {
        val query = dsl.selectDistinct(AMMONITE.TAXONOMY_GENUS)
            .from(AMMONITE)
        if (subfamily != null) {
            query.where(AMMONITE.TAXONOMY_SUBFAMILY.eq(subfamily))
        }
        return query.fetch(AMMONITE.TAXONOMY_GENUS)
    }

    fun findDistinctSubgenus(genus: String?): List<String> {
        val query = dsl.selectDistinct(AMMONITE.TAXONOMY_SUBGENUS)
            .from(AMMONITE)
        if (genus != null) {
            query.where(AMMONITE.TAXONOMY_GENUS.eq(genus))
        }
        return query.fetch(AMMONITE.TAXONOMY_SUBGENUS)
    }

    fun fetchBrowseAmmonites(filters: Map<String, String>): List<Ammonite> {
        // Construct your query based on the provided filters
        val query = dsl.selectFrom(AMMONITE)

        filters.forEach { (key, value) ->
            if (!value.isNullOrBlank()) {
                when (key) {
                    "taxonomySubclass" -> query.where(AMMONITE.TAXONOMY_SUBCLASS.containsIgnoreCase(value))
                    "taxonomyFamily" -> query.where(AMMONITE.TAXONOMY_FAMILY.containsIgnoreCase(value))
                    "taxonomySubfamily" -> query.where(AMMONITE.TAXONOMY_SUBFAMILY.containsIgnoreCase(value))
                    "taxonomyGenus" -> query.where(AMMONITE.TAXONOMY_GENUS.containsIgnoreCase(value))
                    "taxonomySubgenus" -> query.where(AMMONITE.TAXONOMY_SUBGENUS.containsIgnoreCase(value))
                    "taxonomySpecies" -> query.where(AMMONITE.TAXONOMY_SPECIES.containsIgnoreCase(value))
                    "strata" -> query.where(AMMONITE.STRATA.containsIgnoreCase(value))
                    "description" -> query.where(AMMONITE.DESCRIPTION.containsIgnoreCase(value))
                    "comment" -> query.where(AMMONITE.COMMENT.containsIgnoreCase(value))
                }
            }
        }

        return query.fetchInto(Ammonite::class.java)
    }

    fun findById(id: Int): Optional<Ammonite> {
        return dsl.selectFrom(AMMONITE).where(AMMONITE.ID.eq(id)).fetchOptionalInto(Ammonite::class.java)
    }

    fun existsById(id: Int): Boolean {
        return dsl.fetchExists(dsl.selectFrom(AMMONITE).where(AMMONITE.ID.eq(id)))
    }

    fun findByTaxonomySpecies(taxonomySpecies: String): Optional<Ammonite> {
        return dsl.selectFrom(AMMONITE).where(AMMONITE.TAXONOMY_SPECIES.eq(taxonomySpecies)).fetchOptionalInto(Ammonite::class.java)
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