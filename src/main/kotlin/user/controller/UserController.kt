package com.wafflestudio.seminar.spring2023.user.controller

import com.wafflestudio.seminar.spring2023.user.service.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
class UserController(
    private val userService: UserService,
) {

    @PostMapping("/api/v1/signup")
    fun signup(
        @RequestBody request: SignUpRequest,
    ): ResponseEntity<Unit> {
        return try {
            userService.signUp(request.username, request.password, request.image)
            ResponseEntity.status(200).build()
        } catch (e: SignUpBadUsernameException) {
            ResponseEntity.status(400).build()
        } catch (e: SignUpBadPasswordException) {
            ResponseEntity.status(400).build()
        } catch (e: SignUpUsernameConflictException) {
            ResponseEntity.status(409).build()
        }
    }

    @PostMapping("/api/v1/signin")
    fun signIn(
        @RequestBody request: SignInRequest,
    ): ResponseEntity<SignInResponse> {
        return try {
            val user = userService.signIn(request.username, request.password)
            ResponseEntity.ok(SignInResponse(accessToken = user.getAccessToken()))
        } catch (e: SignInUserNotFoundException) {
            ResponseEntity.status(404).build()
        } catch (e: SignInInvalidPasswordException) {
            ResponseEntity.status(404).build()
        }
    }

    @GetMapping("/api/v1/users/me")
    fun me(
        @RequestHeader(name = "Authorization", required = false) authorizationHeader: String?,
    ): ResponseEntity<UserMeResponse> {
        return try {
            val authHeader = authorizationHeader?.removePrefix("Bearer ") ?: throw AuthenticateException()
            val user = userService.authenticate(authHeader)
            ResponseEntity.ok(UserMeResponse(username = user.username, image = user.image))
        } catch (e: AuthenticateException) {
            ResponseEntity.status(401).build()
        }
    }
}

data class UserMeResponse(
    val username: String,
    val image: String,
)

data class SignUpRequest(
    val username: String,
    val password: String,
    val image: String,
)

data class SignInRequest(
    val username: String,
    val password: String,
)

data class SignInResponse(
    val accessToken: String,
)
