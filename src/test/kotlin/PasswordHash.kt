import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

fun main() {
    val passwordEncoder = BCryptPasswordEncoder()
    val hashedPassword = passwordEncoder.encode("monitor1")
    println(hashedPassword)  // Print the hashed password
}
