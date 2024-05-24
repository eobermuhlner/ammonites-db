package ch.obermuhlner.ammonites.user

import ch.obermuhlner.ammonites.jooq.tables.pojos.Users
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users")
class UserRestController @Autowired constructor(
    private val userService: UserService,
    private val passwordEncoder: PasswordEncoder
) {
    @PostMapping
    fun create(@RequestBody user: Users): ResponseEntity<UserDTO> {
        if (user.id != null) {
            throw IllegalArgumentException("Not allowed to create with explicit id")
        }
        val encodedPassword = passwordEncoder.encode(user.password)
        user.password = encodedPassword
        userService.updateUser(user)
        return ResponseEntity(user.toDTO(), HttpStatus.CREATED)
    }

    @PostMapping("/new")
    fun createSelfOnboarding(@RequestBody user: Users): ResponseEntity<UserDTO> {
        if (user.id != null) {
            throw IllegalArgumentException("Not allowed to create with explicit id")
        }
        userService.registerUser(user.username, user.password)
        return ResponseEntity(user.toDTO(), HttpStatus.CREATED)
    }

    @GetMapping
    fun findAll(): ResponseEntity<List<UserDTO>> {
        val users = userService.findAllUsers().map { it.toDTO() }
        return ResponseEntity(users, HttpStatus.OK)
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long): ResponseEntity<UserDTO?> {
        val user = userService.findUserById(id)
        return if (user != null) {
            ResponseEntity(user.toDTO(), HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @PutMapping("/{id}")
    fun updateById(@PathVariable id: Long, @RequestBody updatedUser: Users): ResponseEntity<UserDTO?> {
        val user = userService.findUserById(id)
        return if (user != null) {
            updatedUser.id = id
            if (updatedUser.password != null) {
                updatedUser.password = passwordEncoder.encode(updatedUser.password)
            }
            userService.updateUser(updatedUser, false)
            ResponseEntity(updatedUser.toDTO(), HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @PutMapping("/{id}/password")
    fun changePassword(@PathVariable id: Long, @RequestBody passwordRequest: PasswordRequest) {
        val user = userService.findUserById(id)
        if (user != null) {
            user.password = passwordEncoder.encode(passwordRequest.password)
            userService.updateUser(user, changePassword = true)
        }
    }

    @DeleteMapping("/{id}")
    fun deleteById(@PathVariable id: Long): ResponseEntity<Unit> {
        val deleted = userService.deleteUserById(id)
        return if (deleted) {
            ResponseEntity(HttpStatus.NO_CONTENT)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    private fun Users.toDTO(): UserDTO {
        return UserDTO(id = this.id, username = this.username, enabled = this.enabled!!)
    }

    data class PasswordRequest @JvmOverloads constructor(val password: String = "")
}
