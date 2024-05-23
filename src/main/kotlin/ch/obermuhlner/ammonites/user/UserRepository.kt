package ch.obermuhlner.ammonites.user

import ch.obermuhlner.ammonites.jooq.Tables.USERS
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

    fun saveUser(username: String, password: String, enabled: Boolean) {
        dsl.insertInto(USERS)
            .set(USERS.USERNAME, username)
            .set(USERS.PASSWORD, password)
            .set(USERS.ENABLED, enabled)
            .execute()
    }

    fun findAllUsers(): List<Users> {
        return dsl.selectFrom(USERS).fetchInto(Users::class.java)
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
}
