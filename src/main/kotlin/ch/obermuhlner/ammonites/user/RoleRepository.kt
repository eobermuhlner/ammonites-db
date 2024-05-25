package ch.obermuhlner.ammonites.user

import ch.obermuhlner.ammonites.jooq.tables.pojos.Roles
import ch.obermuhlner.ammonites.jooq.tables.Roles.ROLES
import org.jooq.DSLContext
import org.springframework.stereotype.Repository

@Repository
class RoleRepository(private val dsl: DSLContext) {

    fun findByName(roleName: String): Roles? {
        return dsl.selectFrom(ROLES)
            .where(ROLES.NAME.eq(roleName))
            .fetchOneInto(Roles::class.java)
    }

    fun findAllRoles(): List<Roles> {
        return dsl.selectFrom(ROLES).fetchInto(Roles::class.java)
    }
}
