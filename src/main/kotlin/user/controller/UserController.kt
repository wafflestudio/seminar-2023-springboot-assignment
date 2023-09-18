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
            ResponseEntity(HttpStatus.OK)
        } catch (e: SignUpBadUsernameException) {
            ResponseEntity(HttpStatus.BAD_REQUEST)
        } catch (e: SignUpBadPasswordException) {
            ResponseEntity(HttpStatus.BAD_REQUEST)
        } catch (e: SignUpUsernameConflictException) {
            ResponseEntity(HttpStatus.CONFLICT)
        }
    }

    @PostMapping("/api/v1/signin")
    fun signIn(
        @RequestBody request: SignInRequest,
    ): ResponseEntity<SignInResponse> {
        return try {
            val user = userService.signIn(request.username, request.password)
            ResponseEntity(SignInResponse(user.getAccessToken()), HttpStatus.OK)
        } catch (e: SignInUserNotFoundException) {
            ResponseEntity(null, HttpStatus.NOT_FOUND)
        } catch (e: SignInInvalidPasswordException) {
            ResponseEntity(null, HttpStatus.NOT_FOUND)
        }
    }

    @GetMapping("/api/v1/users/me")
    fun me(
        @RequestHeader(name = "Authorization", required = false) authorizationHeader: String?,
    ): ResponseEntity<UserMeResponse> {
        return try {
            if (authorizationHeader == null) {
                ResponseEntity(HttpStatus.UNAUTHORIZED)
            } else {
                val token = authorizationHeader.split(" ").getOrNull(1)
                        ?: return ResponseEntity(HttpStatus.UNAUTHORIZED)
                val user = userService.authenticate(token)
                ResponseEntity(UserMeResponse(user.username, user.image), HttpStatus.OK)
            }
        } catch (e: AuthenticateException) {
            ResponseEntity(null, HttpStatus.UNAUTHORIZED)
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
