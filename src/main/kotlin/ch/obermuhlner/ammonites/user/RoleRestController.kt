package ch.obermuhlner.ammonites.user

import ch.obermuhlner.ammonites.jooq.tables.pojos.Roles
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/roles")
class RoleRestController @Autowired constructor(
    private val roleService: RoleService
) {
    @GetMapping
    fun getAllRoles(): ResponseEntity<List<Roles>> {
        val roles = roleService.findAllRoles()
        return ResponseEntity(roles, HttpStatus.OK)
    }
}
