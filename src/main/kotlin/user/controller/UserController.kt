package com.wafflestudio.seminar.spring2023.user.controller

import com.wafflestudio.seminar.spring2023.user.service.AuthenticateException
import com.wafflestudio.seminar.spring2023.user.service.SignInInvalidPasswordException
import com.wafflestudio.seminar.spring2023.user.service.SignInUserNotFoundException
import com.wafflestudio.seminar.spring2023.user.service.SignUpBadPasswordException
import com.wafflestudio.seminar.spring2023.user.service.SignUpBadUsernameException
import com.wafflestudio.seminar.spring2023.user.service.SignUpUsernameConflictException
import com.wafflestudio.seminar.spring2023.user.service.UserService
import com.wafflestudio.seminar.spring2023.user.service.UserException
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
        try{
            userService.signUp(request.username, request.password, request.image)
        }
        catch (e: SignUpBadUsernameException){
            return ResponseEntity.status(400).build()
        }
        catch (e: SignUpBadPasswordException) {
            return ResponseEntity.status(400).build()
        }
        catch (e: SignUpUsernameConflictException) {
            return ResponseEntity.status(409).build()
        }
        return ResponseEntity.ok().build()
    }

    @PostMapping("/api/v1/signin")
    fun signIn(
        @RequestBody request: SignInRequest,
    ): ResponseEntity<SignInResponse> {
        try{
            val signInToken = SignInResponse(accessToken = userService.signIn(request.username, request.password).getAccessToken())
            return ResponseEntity.ok(signInToken)
        }
        catch (e: SignInUserNotFoundException) {
            return ResponseEntity.status(404).build()
        }
        catch (e: SignInInvalidPasswordException) {
            return ResponseEntity.status(404).build()
        }
    }

    @GetMapping("/api/v1/users/me")
    fun me(
        @RequestHeader(name = "Authorization", required = false) authorizationHeader: String?,
    ): ResponseEntity<UserMeResponse> {
        try{
            val resp = authorizationHeader?.split(" ")?.let { userService.authenticate(accessToken = it[1]) }
            return ResponseEntity.ok(UserMeResponse(username = resp!!.username, image = resp.image))
        }
        catch (e: AuthenticateException){
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
