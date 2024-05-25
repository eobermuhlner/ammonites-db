package ch.obermuhlner.ammonites.user

import ch.obermuhlner.ammonites.jooq.Tables.*
import ch.obermuhlner.ammonites.jooq.tables.pojos.Users
import org.jooq.DSLContext
import org.springframework.stereotype.Repository

@Repository
class UserRepository(private val dsl: DSLContext) {

    fun isUsernameAvailable(username: String): Boolean {
        return dsl.selectCount()
            .from(USERS)
            .where(USERS.USERNAME.eq(username))
            .fetchOne(0, Int::class.java) == 0
    }

    fun saveUser(username: String, password: String, enabled: Boolean): Long? {
        return dsl.insertInto(USERS)
            .set(USERS.USERNAME, username)
            .set(USERS.PASSWORD, password)
            .set(USERS.ENABLED, enabled)
            .returning(USERS.ID)
            .fetchOne()
            ?.id
    }

    fun findAllUsers(): List<Users> {
        return dsl.selectFrom(USERS).fetchInto(Users::class.java)
    }

    fun findAllUsersWithRoles(): List<UsersWithRoles> {
        val users = dsl.selectFrom(USERS).fetchInto(Users::class.java)
        val rolesByUserId = dsl.select(USER_ROLES.USER_ID, ROLES.NAME)
            .from(USER_ROLES)
            .join(ROLES).on(USER_ROLES.ROLE_ID.eq(ROLES.ID))
            .fetchGroups(USER_ROLES.USER_ID, ROLES.NAME)

        return users.map { user ->
            UsersWithRoles(
                user = user,
                roles = rolesByUserId[user.id] ?: emptyList()
            )
        }
    }

    fun findUserById(id: Long): Users? {
        return dsl.selectFrom(USERS)
            .where(USERS.ID.eq(id))
            .fetchOneInto(Users::class.java)
    }

    fun updateUserWithoutPassword(user: Users) {
        dsl.update(USERS)
            .set(USERS.USERNAME, user.username)
            .set(USERS.ENABLED, user.enabled)
            .where(USERS.ID.eq(user.id))
            .execute()
    }

    fun updateUserWithPassword(user: Users) {
        dsl.update(USERS)
            .set(dsl.newRecord(USERS, user))
            .where(USERS.ID.eq(user.id))
            .execute()
    }

    fun deleteUserById(id: Long): Boolean {
        val deleted = dsl.deleteFrom(USERS)
            .where(USERS.ID.eq(id))
            .execute()
        return deleted > 0
    }

    fun addRoleToUser(userId: Long, roleId: Long) {
        dsl.insertInto(USER_ROLES)
            .set(USER_ROLES.USER_ID, userId)
            .set(USER_ROLES.ROLE_ID, roleId)
            .execute()
    }

    fun addRolesToUser(userId: Long, roles: List<Long>) {
        roles.forEach { roleId ->
            addRoleToUser(userId, roleId)
        }
    }

    fun removeRoleFromUser(userId: Long, roleId: Long) {
        dsl.deleteFrom(USER_ROLES)
            .where(USER_ROLES.USER_ID.eq(userId))
            .and(USER_ROLES.ROLE_ID.eq(roleId))
            .execute()
    }

    fun findUserWithRolesById(id: Long): UsersWithRoles? {
        val userRecord = dsl.selectFrom(USERS)
            .where(USERS.ID.eq(id))
            .fetchOneInto(Users::class.java)

        val roles = dsl.select(ROLES.NAME)
            .from(ROLES)
            .join(USER_ROLES).on(ROLES.ID.eq(USER_ROLES.ROLE_ID))
            .where(USER_ROLES.USER_ID.eq(id))
            .fetchInto(String::class.java)

        return userRecord?.let { UsersWithRoles(it, roles) }
    }

    data class UsersWithRoles(
        val user: Users,
        val roles: List<String>
    )
}
