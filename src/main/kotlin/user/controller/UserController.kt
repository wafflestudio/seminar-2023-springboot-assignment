package com.wafflestudio.seminar.spring2023.user.controller

import com.wafflestudio.seminar.spring2023.user.service.*
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
            userService.signUp(
                username = request.username,
                password = request.password,
                image = request.image
            )
            ResponseEntity.status(200).build()
        } catch (e: UserException) {
            when(e) {
                is SignUpBadUsernameException,
                is SignUpBadPasswordException
                    -> ResponseEntity.status(400).build()
                is SignUpUsernameConflictException
                    -> ResponseEntity.status(409).build()
                else -> ResponseEntity.status(500).build()
            }
        }
    }

    @PostMapping("/api/v1/signin")
    fun signIn(
        @RequestBody request: SignInRequest,
    ): ResponseEntity<SignInResponse> {
        return try {
            ResponseEntity.ok(SignInResponse(
                userService.signIn(
                    username = request.username,
                    password = request.password
                ).getAccessToken()
            ))
        } catch(e: UserException) {
            ResponseEntity.status(404).build()
        }
    }

    @GetMapping("/api/v1/users/me")
    fun me(
        @RequestHeader(name = "Authorization", required = false) authorizationHeader: String?,
    ): ResponseEntity<UserMeResponse> {
        return try {
            val user = userService.authenticate(authorizationHeader!!.removePrefix("Bearer "))
            ResponseEntity.ok(UserMeResponse(
                username = user.username,
                image = user.image
            ))
        } catch(e: Exception) {
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
