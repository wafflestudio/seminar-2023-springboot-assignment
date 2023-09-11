package com.wafflestudio.seminar.spring2023.user.controller

import com.wafflestudio.seminar.spring2023.user.service.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class UserController(
    private val userService: UserService,
) {
    @PostMapping("/api/v1/signup")
    fun signUp(
        @RequestBody request: SignUpRequest,
    ): ResponseEntity<Unit> {
        userService.signUp(
            request.username,
            request.password,
            request.image,
            )
        return ResponseEntity.ok().build()
    }

    @PostMapping("/api/v1/signin")
    fun signIn(
        @RequestBody request: SignInRequest,
    ): ResponseEntity<SignInResponse> {
        val user = userService.signIn(
            request.username,
            request.password
        )
        return ResponseEntity.ok(
            SignInResponse(
                accessToken = user.getAccessToken()
            )
        )
    }

    @GetMapping("/api/v1/users/me")
    fun me(
        @RequestHeader(name = "Authorization", required = false) authorizationHeader: String?,
    ): ResponseEntity<UserMeResponse> {
        val tokenValue = authorizationHeader?.removePrefix("Bearer ") ?: return ResponseEntity.status(401).build()
        val user = userService.authenticate(tokenValue)
        return ResponseEntity.ok(
            UserMeResponse(
                username = user.username,
                image = user.image
            )
        )
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
