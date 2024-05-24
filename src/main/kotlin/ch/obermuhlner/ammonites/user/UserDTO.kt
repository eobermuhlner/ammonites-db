package ch.obermuhlner.ammonites.user

data class UserDTO(
    val id: Long?,
    val username: String,
    val enabled: Boolean
)