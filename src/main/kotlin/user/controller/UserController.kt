package com.wafflestudio.seminar.spring2023.user.controller

import com.wafflestudio.seminar.spring2023.user.service.*
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
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
        //TODO()
        return try {
            userService.signUp(request.username, request.password, request.image)
            ResponseEntity.ok(Unit)
        } catch (e: SignUpBadPasswordException) {
            ResponseEntity.status(400).build()
        } catch (e: SignUpBadUsernameException) {
            ResponseEntity.status(400).build()
        } catch (e: SignUpUsernameConflictException) {
            ResponseEntity.status(409).build()
        }
    }

    @PostMapping("/api/v1/signin")
    fun signIn(
        @RequestBody request: SignInRequest,
    ): ResponseEntity<SignInResponse> {
        //TODO()
        return try {
            ResponseEntity.ok(SignInResponse(userService.signIn(request.username, request.password).getAccessToken()))
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
        //TODO()
        try {
            if (authorizationHeader == null) {
                return ResponseEntity.status(401).build()
            }
            val user = userService.authenticate(authorizationHeader.substring(7))
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
