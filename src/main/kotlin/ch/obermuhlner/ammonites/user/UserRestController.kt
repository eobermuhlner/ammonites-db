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
    fun create(@RequestBody user: Users): ResponseEntity<Users> {
        if (user.id != null) {
            throw IllegalArgumentException("Not allowed to create with explicit id")
        }
        val encodedPassword = passwordEncoder.encode(user.password)
        userService.updateUser(user)
        return ResponseEntity(user, HttpStatus.CREATED)
    }

    @PostMapping("/new")
    fun createSelfOnboarding(@RequestBody user: Users): ResponseEntity<Users> {
        if (user.id != null) {
            throw IllegalArgumentException("Not allowed to create with explicit id")
        }
        userService.registerUser(user.username, user.password)
        return ResponseEntity(user, HttpStatus.CREATED)
    }

    @GetMapping
    fun findAll(): ResponseEntity<List<Users>> {
        val users = userService.findAllUsers()
        return ResponseEntity(users, HttpStatus.OK)
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long): ResponseEntity<Users?> {
        val user = userService.findUserById(id)
        return if (user != null) {
            ResponseEntity(user, HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @PutMapping("/{id}")
    fun updateById(@PathVariable id: Long, @RequestBody updatedUser: Users): ResponseEntity<Users?> {
        val user = userService.findUserById(id)
        return if (user != null) {
            updatedUser.id = id
            if (updatedUser.password != null) {
                updatedUser.password = passwordEncoder.encode(updatedUser.password)
            }
            userService.updateUser(updatedUser)
            ResponseEntity(updatedUser, HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
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
}
