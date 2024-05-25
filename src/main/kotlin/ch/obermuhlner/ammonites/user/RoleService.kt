package ch.obermuhlner.ammonites.user

import ch.obermuhlner.ammonites.jooq.tables.pojos.Roles
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RoleService(private val roleRepository: RoleRepository) {

    @Transactional(readOnly = true)
    fun findAllRoles(): List<Roles> {
        return roleRepository.findAllRoles()
    }

    @Transactional(readOnly = true)
    fun findRoleByName(name: String): Roles? {
        return roleRepository.findByName(name)
    }
}
