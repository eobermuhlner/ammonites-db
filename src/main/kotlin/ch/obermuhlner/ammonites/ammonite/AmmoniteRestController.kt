package ch.obermuhlner.ammonites.ammonite

import ch.obermuhlner.ammonites.jooq.tables.pojos.Ammonite
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/ammonites")
class AmmoniteRestController @Autowired constructor(
    private val ammoniteService: AmmoniteService
) {

    @PostMapping
    fun create(@RequestBody ammonite: Ammonite): ResponseEntity<Ammonite> {
        val createdAmmonite = ammoniteService.create(ammonite)
        return ResponseEntity(createdAmmonite, HttpStatus.CREATED)
    }

    @GetMapping
    fun findAll(): ResponseEntity<List<Ammonite>> {
        val ammonites = ammoniteService.findAll()
        return ResponseEntity(ammonites, HttpStatus.OK)
    }

    @GetMapping("/taxonomy/{level}")
    fun getTaxonomyOptions(
        @PathVariable level: String,
        @RequestParam(required = false) parent: String?
    ): ResponseEntity<List<String>> {
        val options = ammoniteService.getTaxonomyOptions(level, parent)
        return ResponseEntity(options, HttpStatus.OK)
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Int): ResponseEntity<Ammonite?> {
        val ammonite = ammoniteService.findById(id)
        return if (ammonite != null) {
            ResponseEntity(ammonite, HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @PutMapping("/{id}")
    fun updateById(@PathVariable id: Int, @RequestBody updatedAmmonite: Ammonite): ResponseEntity<Ammonite?> {
        val ammonite = ammoniteService.updateById(id, updatedAmmonite)
        return if (ammonite != null) {
            ResponseEntity(ammonite, HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @DeleteMapping("/{id}")
    fun deleteById(@PathVariable id: Int): ResponseEntity<Unit> {
        val deleted = ammoniteService.deleteById(id)
        return if (deleted) {
            ResponseEntity(HttpStatus.NO_CONTENT)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @GetMapping("/browse")
    fun fetchBrowseAmmonites(@RequestParam filters: Map<String, String>): ResponseEntity<List<Ammonite>> {
        val ammonites = ammoniteService.fetchBrowseAmmonites(filters)
        return ResponseEntity(ammonites, HttpStatus.OK)
    }

    @GetMapping("/matching")
    fun findMatchingAmmonitesWithMeasurements(
        @RequestParam(required = false) diameterSide: Double? = null,
        @RequestParam(required = false) diameterCross: Double? = null,
        @RequestParam(required = false) proportionN: Double? = null,
        @RequestParam(required = false) proportionH: Double? = null,
        @RequestParam(required = false) proportionB: Double? = null,
        @RequestParam(required = false) proportionQ: Double? = null,
        @RequestParam(required = false) countPrimaryRibs: Double? = null,
        @RequestParam(required = false) countSecondaryRibs: Double? = null,
        @RequestParam(required = false) ribDivisionRatio: Double? = null,
        @RequestParam(defaultValue = "10")limit: Int
    ): List<AmmoniteService.AmmoniteWithMeasurement> {
        return ammoniteService.findMatchingAmmonitesWithMeasurements(
            diameterSide,
            diameterCross,
            proportionN,
            proportionH,
            proportionB,
            proportionQ,
            countPrimaryRibs,
            countSecondaryRibs,
            ribDivisionRatio,
            limit)
    }
}
