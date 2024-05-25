package ch.obermuhlner.ammonites.user

data class UserDTO(
    val id: Long?,
    val username: String,
    val enabled: Boolean,
    val email: String,
    val firstName: String,
    val lastName: String,
    val roles: List<String>
)