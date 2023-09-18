package com.wafflestudio.seminar.spring2023.user.controller

import com.wafflestudio.seminar.spring2023.user.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController
import com.wafflestudio.seminar.spring2023.user.service.AuthenticateException
import com.wafflestudio.seminar.spring2023.user.service.SignInInvalidPasswordException
import com.wafflestudio.seminar.spring2023.user.service.SignInUserNotFoundException
import com.wafflestudio.seminar.spring2023.user.service.SignUpBadPasswordException
import com.wafflestudio.seminar.spring2023.user.service.SignUpBadUsernameException
import com.wafflestudio.seminar.spring2023.user.service.SignUpUsernameConflictException

@RestController
class UserController(
    private val userService: UserService,
) {

    @PostMapping("/api/v1/signup")
    fun signup(
        @RequestBody request: SignUpRequest,
    ): ResponseEntity<Unit> {
        try {
            val user = userService.signUp(request.username, request.password, request.image)
            return ResponseEntity.ok().build()
        } catch (e: SignUpUsernameConflictException) {
            return ResponseEntity.status(409).build()
        } catch (e: SignUpBadUsernameException) {
            return ResponseEntity.status(400).build()
        } catch (e: SignUpBadPasswordException) {
            return ResponseEntity.status(400).build()
        }
    }

    @PostMapping("/api/v1/signin")
    fun signIn(
        @RequestBody request: SignInRequest,
    ): ResponseEntity<SignInResponse> {
        try {
            val user = userService.signIn(request.username, request.password)
            val accessToken = user.username.reversed()
            return ResponseEntity.ok(SignInResponse(accessToken))
        } catch (e: SignInUserNotFoundException) {
            return ResponseEntity.status(404).build()
        } catch (e: SignInInvalidPasswordException) {
            return ResponseEntity.status(404).build()
        }
    }

    @GetMapping("/api/v1/users/me")
    fun me(
        @RequestHeader(name = "Authorization", required = false) authorizationHeader: String?,
    ): ResponseEntity<UserMeResponse> {
        if (authorizationHeader == null) {
            return ResponseEntity.status(401).build()
        }

        val token = authorizationHeader.replace("Bearer ", "")

        try {
            val user = userService.authenticate(token)
            return ResponseEntity.ok(UserMeResponse(user.username, user.image))
        } catch (e: AuthenticateException) {
            return ResponseEntity.status(401).build()
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
