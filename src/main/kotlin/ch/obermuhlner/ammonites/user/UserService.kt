package ch.obermuhlner.ammonites.user

import ch.obermuhlner.ammonites.jooq.tables.pojos.Users
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    @Value("\${admin.username}") private val adminUsername: String,
    @Value("\${admin.password}") private val adminPassword: String
) {
    fun isUsernameAvailable(username: String): Boolean {
        return userRepository.isUsernameAvailable(username)
    }

    @Transactional
    fun registerUser(username: String, password: String) {
        if (!isUsernameAvailable(username)) {
            throw IllegalArgumentException("Username already taken")
        }
        userRepository.saveUser(username, passwordEncoder.encode(password), true)
    }

    fun findAllUsers(): List<Users> {
        return userRepository.findAllUsers()
    }

    fun findUserById(id: Long): Users? {
        return userRepository.findUserById(id)
    }

    fun updateUser(user: Users) {
        userRepository.updateUser(user)
    }

    fun deleteUserById(id: Long): Boolean {
        return userRepository.deleteUserById(id)
    }

    @PostConstruct
    private fun createDefaultAdminUserIfNeeded() {
        if (userRepository.isUsernameAvailable(adminUsername)) {
            registerUser(adminUsername, adminPassword)
        }
    }
}
