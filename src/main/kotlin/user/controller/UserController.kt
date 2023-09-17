package com.wafflestudio.seminar.spring2023.user.controller

import com.wafflestudio.seminar.spring2023.user.repository.UserEntity
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
        try {
            userService.signUp(
                    username = request.username,
                    password = request.password,
                    image = request.image
            )
            return ResponseEntity.status(HttpStatus.OK).build()
        } catch (e: Exception) {
            return when (e) {
                is SignUpUsernameConflictException -> {
                    ResponseEntity.status(HttpStatus.CONFLICT).build()
                }

                else -> {
                    ResponseEntity.status((HttpStatus.BAD_REQUEST)).build()
                }
            }
        }
    }

    @PostMapping("/api/v1/signin")
    fun signIn(
        @RequestBody request: SignInRequest,
    ): ResponseEntity<SignInResponse> {
        return try {
            val user = userService.signIn(
                    username = request.username,
                    password = request.password
            )
            ResponseEntity.ok(
                    SignInResponse(
                            accessToken = user.getAccessToken()
                    )
            )
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).build()
        }
    }

    @GetMapping("/api/v1/users/me")
    fun me(
        @RequestHeader(name = "Authorization", required = false) authorizationHeader: String?,
    ): ResponseEntity<UserMeResponse> {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
        }

        return try {
            val user = userService.authenticate(accessToken = authorizationHeader.removePrefix("Bearer "))
            ResponseEntity.ok(
                    UserMeResponse(
                        username = user.username,
                        image = user.image
                    )
            )
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
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
