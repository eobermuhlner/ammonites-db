package ch.obermuhlner.ammonites.user

import ch.obermuhlner.ammonites.jooq.Tables.*
import org.jooq.DSLContext
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(private val dsl: DSLContext) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val userRecord = dsl.selectFrom(USERS)
            .where(USERS.USERNAME.eq(username))
            .fetchOne()
            ?: throw UsernameNotFoundException("User not found")

        val roles = dsl.select(ROLES.NAME)
            .from(ROLES)
            .join(USER_ROLES).on(USER_ROLES.ROLE_ID.eq(ROLES.ID))
            .where(USER_ROLES.USER_ID.eq(userRecord.id))
            .fetch()
            .map { SimpleGrantedAuthority("ROLE_${it.value1()}") }

        return User(
            userRecord.username,
            userRecord.password,
            userRecord.enabled,
            true,
            true,
            true,
            roles
        )
    }
}
