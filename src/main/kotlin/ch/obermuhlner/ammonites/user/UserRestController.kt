package ch.obermuhlner.ammonites.user

import ch.obermuhlner.ammonites.jooq.tables.pojos.Users
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/users")
class UserRestController @Autowired constructor(
    private val userService: UserService
) {
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    fun create(@RequestBody user: Users): ResponseEntity<UserDTO> {
        if (user.id != null) {
            throw IllegalArgumentException("Not allowed to create with explicit id")
        }
        userService.updateUser(user)
        return ResponseEntity(user.toDTO(), HttpStatus.CREATED)
    }

    @PostMapping("/new")
    fun createSelfOnboarding(@RequestBody user: Users): ResponseEntity<UserDTO> {
        if (user.id != null) {
            throw IllegalArgumentException("Not allowed to create with explicit id")
        }
        userService.registerUserWithRoleNames(
            user.username,
            user.password,
            user.email,
            user.firstName,
            user.lastName,
            listOf("USER")
        )
        return ResponseEntity(user.toDTO(), HttpStatus.CREATED)
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    fun findAll(): ResponseEntity<List<UserDTO>> {
        val users = userService.findAllUsersWithRoles().map { it.toDTO() }
        return ResponseEntity(users, HttpStatus.OK)
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long): ResponseEntity<UserDTO?> {
        val userWithRoles = userService.findUserWithRolesById(id)
        return if (userWithRoles != null) {
            ResponseEntity(userWithRoles.toDTO(), HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    fun updateById(@PathVariable id: Long, @RequestBody updatedUser: Users): ResponseEntity<UserDTO?> {
        val user = userService.findUserById(id)
        return if (user != null) {
            updatedUser.id = id
            userService.updateUser(updatedUser, false)
            ResponseEntity(updatedUser.toDTO(), HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @PutMapping("/{id}/password")
    fun changePassword(@PathVariable id: Long, @RequestBody passwordRequest: PasswordRequest): ResponseEntity<Unit> {
        val user = userService.findUserById(id)
        return if (user != null) {
            user.password = passwordRequest.password
            userService.updateUser(user, changePassword = true)
            ResponseEntity(HttpStatus.NO_CONTENT)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    fun deleteById(@PathVariable id: Long): ResponseEntity<Unit> {
        val deleted = userService.deleteUserById(id)
        return if (deleted) {
            ResponseEntity(HttpStatus.NO_CONTENT)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/{userId}/roles")
    fun addRoleToUser(@PathVariable userId: Long, @RequestBody roleName: String): ResponseEntity<Unit> {
        userService.addRoleToUser(userId, roleName)
        return ResponseEntity(HttpStatus.OK)
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{userId}/roles")
    fun removeRoleFromUser(@PathVariable userId: Long, @RequestBody roleName: String): ResponseEntity<Unit> {
        userService.removeRoleFromUser(userId, roleName)
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }

    @GetMapping("/roles")
    fun getUserRoles(authentication: Authentication): ResponseEntity<Set<String>> {
        val roles = authentication.authorities.map { it.authority.removePrefix("ROLE_") }.toSet()
        return ResponseEntity.ok(roles)
    }

    @GetMapping("/me")
    fun findCurrentUser(authentication: Authentication): ResponseEntity<UserDTO?> {
        val currentUser = userService.findUserByUsername(authentication.name)
        return if (currentUser != null) {
            ResponseEntity(currentUser.toDTO(), HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @PutMapping("/me")
    fun updateCurrentUser(
        authentication: Authentication,
        @RequestBody updatedUser: Users
    ): ResponseEntity<UserDTO?> {
        val currentUser = userService.findUserByUsername(authentication.name)
        return if (currentUser != null) {
            updatedUser.id = currentUser.id
            userService.updateUser(updatedUser, false)
            ResponseEntity(updatedUser.toDTO(), HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @PutMapping("/me/password")
    fun changeCurrentUserPassword(
        authentication: Authentication,
        @RequestBody passwordRequest: PasswordRequest
    ): ResponseEntity<Unit> {
        val currentUser = userService.findUserByUsername(authentication.name)
        return if (currentUser != null) {
            currentUser.password = passwordRequest.password
            userService.updateUser(currentUser, changePassword = true)
            ResponseEntity(HttpStatus.NO_CONTENT)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    private fun Users.toDTO(): UserDTO {
        return UserDTO(
            id = this.id,
            username = this.username,
            email = this.email,
            firstName = this.firstName,
            lastName = this.lastName,
            enabled = this.enabled!!,
            roles = Collections.emptyList()
        )
    }

    data class PasswordRequest @JvmOverloads constructor(val password: String = "")

    private fun UserRepository.UsersWithRoles.toDTO(): UserDTO {
        return UserDTO(
            id = this.user.id,
            username = this.user.username,
            email = this.user.email,
            firstName = this.user.firstName,
            lastName = this.user.lastName,
            enabled = this.user.enabled!!,
            roles = this.roles
        )
    }
}
