package com.wafflestudio.seminar.spring2023.user.controller

import com.fasterxml.jackson.databind.JsonSerializer.None
import com.wafflestudio.seminar.spring2023.user.service.*
import org.apache.coyote.Response
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
            val user = userService.signUp(request.username, request.password, request.image)
            return ResponseEntity.status(200).body(Unit)
        }catch (e :Exception){
            return when (e) {
                is SignUpBadPasswordException, is SignUpBadUsernameException ->{
                    ResponseEntity.status(400).body(Unit)
                }
                is SignUpUsernameConflictException ->{
                    ResponseEntity.status(409).body(Unit)
                }
                else ->{
                    ResponseEntity.status(400).body(Unit)
                }
            }
        }
    }

    @PostMapping("/api/v1/signin")
    fun signIn(
        @RequestBody request: SignInRequest,
    ): ResponseEntity<SignInResponse> {
        return try {
            val user = userService.signIn(request.username, request.password)
            ResponseEntity.status(200).body(SignInResponse(user.getAccessToken()))
        }catch(e : UserException){
            ResponseEntity.status(404).body(null)
        }
    }

    @GetMapping("/api/v1/users/me")
    fun me(
        @RequestHeader(name = "Authorization", required = false) authorizationHeader: String?,
    ): ResponseEntity<UserMeResponse> {
        return try {
            val authorizationHeaderValue = authorizationHeader.toString().substringAfter("Bearer ")
            val user = userService.authenticate(authorizationHeaderValue)
            ResponseEntity.status(200).body(UserMeResponse(user.username, user.image))
        }catch (e : UserException){
            ResponseEntity.status(401).body(null)
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
