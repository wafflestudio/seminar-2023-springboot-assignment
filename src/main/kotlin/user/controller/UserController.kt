package com.wafflestudio.seminar.spring2023.user.controller

import com.wafflestudio.seminar.spring2023.user.service.*
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController
import kotlin.math.sign

@RestController
class UserController(
    private val userService: UserService,
) {

    @PostMapping("/api/v1/signup")
    fun signup(
        @RequestBody request: SignUpRequest,
    ): ResponseEntity<Unit> {
        return try{
            userService.signUp(
                    username = request.username,
                    password = request.password,
                    image = request.image
            )
            ResponseEntity(HttpStatus.OK)
        } catch(e : SignUpBadUsernameException) {
            ResponseEntity(HttpStatus.BAD_REQUEST)
        } catch(e : SignUpBadPasswordException) {
            ResponseEntity(HttpStatus.BAD_REQUEST)
        } catch(e : SignUpUsernameConflictException) {
            ResponseEntity(HttpStatus.CONFLICT)
        }
    }

    @PostMapping("/api/v1/signin")
    fun signIn(
        @RequestBody request: SignInRequest,
    ): ResponseEntity<SignInResponse> {
        return try{
            val signInUser = userService.signIn(
                    username = request.username,
                    password = request.password
            )
            ResponseEntity(SignInResponse(signInUser.getAccessToken()),HttpStatus.OK)
        } catch(e : SignInUserNotFoundException){
            ResponseEntity(HttpStatus.NOT_FOUND)
        } catch(e : SignInInvalidPasswordException){
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @GetMapping("/api/v1/users/me")
    fun me(
        @RequestHeader(name = "Authorization", required = false) authorizationHeader: String?,
    ): ResponseEntity<UserMeResponse> {
        return try{
            val authenticatedUser = userService.authenticate(
                    accessToken = authorizationHeader?.substringAfter("Bearer ") ?: throw AuthenticateException()
            )
            ResponseEntity(UserMeResponse(username = authenticatedUser.username,image = authenticatedUser.image),HttpStatus.OK)
        } catch(e : AuthenticateException){
            ResponseEntity(HttpStatus.UNAUTHORIZED)
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
