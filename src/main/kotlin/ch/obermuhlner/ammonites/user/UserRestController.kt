package ch.obermuhlner.ammonites.user

import ch.obermuhlner.ammonites.jooq.Tables.USERS
import ch.obermuhlner.ammonites.jooq.tables.pojos.Users
import org.jooq.DSLContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users")
class UserRestController @Autowired constructor(
    private val dsl: DSLContext,
    private val passwordEncoder: PasswordEncoder
) {
    @PostMapping
    fun create(@RequestBody user: Users): ResponseEntity<Users> {
        if (user.id != null) {
            throw IllegalArgumentException("Not allowed to create with explicit id")
        }
        user.password = passwordEncoder.encode(user.password)
        dsl.insertInto(USERS)
            .set(dsl.newRecord(USERS, user))
            .execute()
        return ResponseEntity(user, HttpStatus.CREATED)
    }

    @PostMapping("/new")
    fun createSelfOnboarding(@RequestBody user: Users): ResponseEntity<Users> {
        if (user.id != null) {
            throw IllegalArgumentException("Not allowed to create with explicit id")
        }
        user.password = passwordEncoder.encode(user.password)
        dsl.insertInto(USERS)
            .set(dsl.newRecord(USERS, user))
            .execute()
        return ResponseEntity(user, HttpStatus.CREATED)
    }

    @GetMapping
    fun findAll(): ResponseEntity<List<Users>> {
        val users = dsl.selectFrom(USERS).fetchInto(Users::class.java)
        return ResponseEntity(users, HttpStatus.OK)
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long): ResponseEntity<Users?> {
        val user = dsl.selectFrom(USERS)
            .where(USERS.ID.eq(id))
            .fetchOneInto(Users::class.java)
        return if (user != null) {
            ResponseEntity(user, HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @PutMapping("/{id}")
    fun updateById(@PathVariable id: Long, @RequestBody updatedUser: Users): ResponseEntity<Users?> {
        val userExists = dsl.fetchExists(dsl.selectOne().from(USERS).where(USERS.ID.eq(id)))
        return if (userExists) {
            updatedUser.id = id
            if (updatedUser.password != null) {
                updatedUser.password = passwordEncoder.encode(updatedUser.password)
            }
            dsl.update(USERS)
                .set(dsl.newRecord(USERS, updatedUser))
                .where(USERS.ID.eq(id))
                .execute()
            ResponseEntity(updatedUser, HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @DeleteMapping("/{id}")
    fun deleteById(@PathVariable id: Long): ResponseEntity<Unit> {
        val deleted = dsl.deleteFrom(USERS)
            .where(USERS.ID.eq(id))
            .execute()
        return if (deleted > 0) {
            ResponseEntity(HttpStatus.NO_CONTENT)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @PostMapping("/default")
    fun createDefaultUser(): ResponseEntity<Users> {
        val defaultUser = Users(
            null,
            "default_user",
            passwordEncoder.encode("password"),
            true
        )
        dsl.insertInto(USERS)
            .set(dsl.newRecord(USERS, defaultUser))
            .execute()
        return ResponseEntity(defaultUser, HttpStatus.CREATED)
    }
}
