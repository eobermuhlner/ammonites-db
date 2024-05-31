package ch.obermuhlner.ammonites.auth

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.csrf.CsrfToken
import org.springframework.web.bind.annotation.*


data class LoginRequest(val username: String, val password: String)
data class LoginResponse(val message: String, val token: String?)

@RestController
@RequestMapping("/api")
class AuthRestController(private val authenticationManager: AuthenticationManager) {

    @GetMapping("/csrf-token")
    fun getCsrfToken(csrfToken: CsrfToken?): CsrfToken? {
        return csrfToken
    }

    @PostMapping("/login")
    fun login(@RequestBody loginRequest: LoginRequest): ResponseEntity<LoginResponse> {
        val authenticationToken = UsernamePasswordAuthenticationToken(loginRequest.username, loginRequest.password)
        return try {
            val authentication: Authentication = authenticationManager.authenticate(authenticationToken)
            SecurityContextHolder.getContext().authentication = authentication

            val userDetails = authentication.principal as org.springframework.security.core.userdetails.User
            val roles = userDetails.authorities.map { it.authority }

            val token = JwtUtil.generateToken(loginRequest.username, roles)
            ResponseEntity.ok(LoginResponse("Login successful", token))
        } catch (ex: Exception) {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(LoginResponse("Login failed: ${ex.message}", null))
        }
    }
}
