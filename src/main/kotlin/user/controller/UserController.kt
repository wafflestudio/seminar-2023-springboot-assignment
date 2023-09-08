package com.wafflestudio.seminar.spring2023.user.controller

import com.wafflestudio.seminar.spring2023.user.service.*
import com.wafflestudio.seminar.spring2023.user.service.UserService
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
            ResponseEntity.ok().build()
        } catch (e: UserException) {
            val statusCode = handleUserException(e)
            ResponseEntity.status(statusCode).build()
        }
    }

    @PostMapping("/api/v1/signin")
    fun signIn(
        @RequestBody request: SignInRequest,
    ): ResponseEntity<SignInResponse> {
        return try {
            val user = userService.signIn(request.username, request.password)
            ResponseEntity.ok(
                SignInResponse(
                    accessToken = user.getAccessToken(),
                )
            )
        } catch (e: UserException) {
            val statusCode = handleUserException(e)
            ResponseEntity.status(statusCode).build()
        }
    }

    @GetMapping("/api/v1/users/me")
    fun me(
        @RequestHeader(name = "Authorization", required = false) authorizationHeader: String?,
    ): ResponseEntity<UserMeResponse> {
        return try {
            val accessToken = authorizationHeader?.removePrefix("Bearer ") ?: throw AuthenticateException()
            val user = userService.authenticate(accessToken)
            ResponseEntity.ok(
                UserMeResponse(
                    username = user.username,
                    image = user.image,
                )
            )
        } catch (e: UserException) {
            val statusCode = handleUserException(e)
            ResponseEntity.status(statusCode).build()
        }
    }

    fun handleUserException(ex: UserException): Int = when (ex) {
        is SignUpBadUsernameException -> 400
        is SignUpBadPasswordException -> 400
        is SignUpUsernameConflictException -> 409
        is SignInUserNotFoundException -> 404
        is SignInInvalidPasswordException -> 404
        is AuthenticateException -> 401
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
