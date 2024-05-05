package ch.obermuhlner.ammonites.measurement

import ch.obermuhlner.ammonites.jooq.tables.pojos.Measurement
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api")
class MeasurementRestController @Autowired constructor(
    private val measurementService: MeasurementService
) {

    @PostMapping("/ammonites/{ammoniteId}/measurements")
    fun create(@PathVariable ammoniteId: Int, @RequestBody measurement: Measurement): ResponseEntity<Measurement> {
        val created = measurementService.create(measurement)
        return ResponseEntity(created, HttpStatus.CREATED)
    }

    @GetMapping("/ammonites/{ammoniteId}/measurements")
    fun findAll(@PathVariable ammoniteId: Int): ResponseEntity<List<Measurement>> {
        val ammonites = measurementService.findAll(ammoniteId)
        return ResponseEntity(ammonites, HttpStatus.OK)
    }

    @GetMapping("/measurements/{id}")
    fun findById(@PathVariable id: Int): ResponseEntity<Measurement?> {
        val ammonite = measurementService.findById(id)
        return if (ammonite != null) {
            ResponseEntity(ammonite, HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @PutMapping("/measurements/{id}")
    fun updateById(@PathVariable id: Int, @RequestBody modified: Measurement): ResponseEntity<Measurement?> {
        val ammonite = measurementService.updateById(id, modified)
        return if (ammonite != null) {
            ResponseEntity(ammonite, HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @DeleteMapping("/measurements/{id}")
    fun deleteById(@PathVariable id: Int): ResponseEntity<Unit> {
        val deleted = measurementService.deleteById(id)
        return if (deleted) {
            ResponseEntity(HttpStatus.NO_CONTENT)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }
}
