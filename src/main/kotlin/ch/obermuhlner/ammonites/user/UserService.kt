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
    private val roleRepository: RoleRepository,
    private val passwordEncoder: PasswordEncoder,
    @Value("\${admin.username}") private val adminUsername: String,
    @Value("\${admin.password}") private val adminPassword: String
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
    fun registerUserWithRoleNames(username: String, password: String, email: String, firstName: String?, lastName: String?, roles: List<String>) {
        val roleIds = roles.map { roleName -> roleRepository.findByName(roleName)!!.id }
        registerUserWithRoles(username, password, email, firstName, lastName, roleIds)
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

    @Transactional(readOnly = true)
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
