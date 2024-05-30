package ch.obermuhlner.ammonites.user

import ch.obermuhlner.ammonites.email.EmailService
import ch.obermuhlner.ammonites.jooq.tables.pojos.ConfirmationTokens
import ch.obermuhlner.ammonites.jooq.tables.pojos.Users
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.*

@Service
class UserService(
    private val userRepository: UserRepository,
    private val roleRepository: RoleRepository,
    private val passwordEncoder: PasswordEncoder,
    private val emailService: EmailService,
    private val confirmationTokenRepository: ConfirmationTokenRepository,
    @Value("\${admin.username}") private val adminUsername: String,
    @Value("\${admin.password}") private val adminPassword: String,
    @Value("\${app.base-url}") private val baseUrl: String,
    @Value("\${app.email-confirmation.enable}") private val enableEmailConfirmation: Boolean
) {
    @Transactional(readOnly = true)
    fun isUsernameAvailable(username: String): Boolean {
        return userRepository.isUsernameAvailable(username)
    }

    @Transactional
    fun registerUserWithRoles(username: String, password: String, email: String, firstName: String?, lastName: String?, roles: List<Long>) {
        if (!isUsernameAvailable(username)) {
            throw IllegalArgumentException("Username already taken")
        }
        val userId = userRepository.saveUser(username, passwordEncoder.encode(password), email, firstName, lastName, true)
        if (userId != null) {
            userRepository.addRolesToUser(userId, roles)
        }
    }

    @Transactional
    fun registerUserWithRoleNames(
        username: String,
        password: String,
        email: String,
        firstName: String?,
        lastName: String?,
        roles: List<String>
    ) {
        if (!isUsernameAvailable(username)) {
            throw IllegalArgumentException("Username already taken")
        }
        val encodedPassword = passwordEncoder.encode(password)
        val enabled = !enableEmailConfirmation
        val userId = userRepository.saveUser(username, encodedPassword, email, firstName, lastName, enabled)
        val roleIds = roles.map { roleName -> roleRepository.findByName(roleName)!!.id }
        if (userId != null) {
            userRepository.addRolesToUser(userId, roleIds)
            if (enableEmailConfirmation) {
                sendConfirmationEmail(email, userId)
            }
        }
    }

    private fun sendConfirmationEmail(email: String, userId: Long) {
        val token = UUID.randomUUID().toString()
        val expiryDate = LocalDateTime.now().plusDays(1)
        confirmationTokenRepository.saveToken(ConfirmationTokens(
            null,
            token,
            userId,
            expiryDate
        ))
        val confirmationUrl = "$baseUrl/users/confirm?token=$token"
        val message = "Please confirm your email by clicking on the following link: $confirmationUrl"
        emailService.sendEmail(email, "Email Confirmation", message)
    }

    @Transactional
    fun confirmUser(token: String): Boolean {
        val confirmationToken = confirmationTokenRepository.findByToken(token)
        return if (confirmationToken != null && confirmationToken.userId != null) {
            val user = userRepository.findUserById(confirmationToken.userId)
            if (user != null) {
                user.enabled = true
                userRepository.updateUserWithoutPassword(user)
                confirmationTokenRepository.deleteToken(confirmationToken.id)
                true
            } else {
                false
            }
        } else {
            false
        }
    }

    @Transactional(readOnly = true)
    fun findAllUsers(): List<Users> {
        return userRepository.findAllUsers()
    }

    fun findAllUsersWithRoles(): List<UserRepository.UsersWithRoles> {
        return userRepository.findAllUsersWithRoles()
    }

    @Transactional(readOnly = true)
    fun findUserById(id: Long): Users? {
        return userRepository.findUserById(id)
    }

    @Transactional(readOnly = true)
    fun findUserWithRolesById(id: Long): UserRepository.UsersWithRoles? {
        return userRepository.findUserWithRolesById(id)
    }

    @Transactional(readOnly = true)
    fun findUserByUsername(username: String): Users? {
        return userRepository.findUserByUsername(username)
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

    @Transactional
    fun deleteUserById(id: Long): Boolean {
        return userRepository.deleteUserById(id)
    }

    @Transactional
    fun addRoleToUser(userId: Long, roleName: String) {
        val role = roleRepository.findByName(roleName) ?: throw IllegalArgumentException("Role not found: $roleName")
        userRepository.addRoleToUser(userId, role.id!!)
    }

    @Transactional
    fun removeRoleFromUser(userId: Long, roleName: String) {
        val role = roleRepository.findByName(roleName) ?: throw IllegalArgumentException("Role not found: $roleName")
        userRepository.removeRoleFromUser(userId, role.id!!)
    }

    @PostConstruct
    private fun createDefaultAdminUserIfNeeded() {
        if (userRepository.isUsernameAvailable(adminUsername)) {
            registerUserWithRoleNames(adminUsername, adminPassword, "admin.ammonites@obermuhlner.ch", "Admin", "User", listOf("ADMIN"))
        }
    }
}
