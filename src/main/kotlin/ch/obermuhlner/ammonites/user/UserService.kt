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
    @Transactional(readOnly = true)
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

    @Transactional(readOnly = true)
    fun findAllUsers(): List<Users> {
        return userRepository.findAllUsers()
    }

    @Transactional(readOnly = true)
    fun findUserById(id: Long): Users? {
        return userRepository.findUserById(id)
    }

    @Transactional
    fun updateUser(user: Users, changePassword: Boolean = false) {
        if (changePassword && user.password != null && user.password.isNotEmpty()) {
            user.password = passwordEncoder.encode(user.password)
            userRepository.updateUserWithPassword(user)
        } else {
            userRepository.updateUserWithoutPassword(user)
        }
    }

    @Transactional(readOnly = true)
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
