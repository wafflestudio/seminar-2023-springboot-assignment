package com.wafflestudio.seminar.spring2023.user.controller

import com.wafflestudio.seminar.spring2023.user.service.*
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController

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
            ResponseEntity.status(HttpStatus.OK).body(null)
        } catch (e: SignUpBadUsernameException) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null)
        } catch (e: SignUpBadPasswordException) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null)
        } catch (e: SignUpUsernameConflictException) {
            ResponseEntity.status(HttpStatus.CONFLICT).body(null)
        }
    }

    @PostMapping("/api/v1/signin")
    fun signIn(
        @RequestBody request: SignInRequest,
    ): ResponseEntity<SignInResponse> {
        return try {
            val user = userService.signIn(request.username, request.password)
            ResponseEntity.status(HttpStatus.OK).body(SignInResponse(user.getAccessToken()))
        } catch (e: SignInUserNotFoundException) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(null)
        } catch (e: SignInInvalidPasswordException) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(null)
        }
    }

    @GetMapping("/api/v1/users/me")
    fun me(
        @RequestHeader(name = "Authorization", required = false) authorizationHeader: String?,
    ): ResponseEntity<UserMeResponse> {
        val token = authorizationHeader?.removePrefix("Bearer ") ?: return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null)
        return try {
            val user = userService.authenticate(token)
            ResponseEntity.status(HttpStatus.OK).body(UserMeResponse(user.username, user.image))
        } catch (e: AuthenticateException) {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null)
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
