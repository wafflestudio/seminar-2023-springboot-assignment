package com.wafflestudio.seminar.spring2023.user.controller

import com.wafflestudio.seminar.spring2023.user.service.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController
import kotlin.Exception

@RestController
class UserController(
    private val userService: UserService,
) {

    @PostMapping("/api/v1/signup")
    fun signup(
        @RequestBody request: SignUpRequest,
    ): ResponseEntity<Unit> {
        //TODO()
        try {
            userService.signUp(request.username,request.password,request.image)
            return ResponseEntity.ok().build()
        }
        catch (e:SignUpBadUsernameException){
            return ResponseEntity.status(400).build()
        }
        catch (e:SignUpBadPasswordException){
            return ResponseEntity.status(400).build()
        }
        catch (e:SignUpUsernameConflictException){
            return ResponseEntity.status(409).build()
        }
    }

    @PostMapping("/api/v1/signin")
    fun signIn(
        @RequestBody request: SignInRequest,
    ): ResponseEntity<SignInResponse> {
        //TODO()
        return try {
            val user = userService.signIn(request.username,request.password)
            ResponseEntity.ok().body(SignInResponse(user.getAccessToken()))
        } catch (e:Exception){
            ResponseEntity.status(404).build()
        }
    }

    @GetMapping("/api/v1/users/me")
    fun me(
        @RequestHeader(name = "Authorization", required = false) authorizationHeader: String?,
    ): ResponseEntity<UserMeResponse> {
        //TODO()
        if(authorizationHeader==null) return ResponseEntity.status(401).build()
        return try {
            val token= authorizationHeader.removePrefix("Bearer ")
            val user = userService.authenticate(token)
            ResponseEntity.ok().body(UserMeResponse(user.username,user.image))
        }
        catch (e:AuthenticateException){
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
