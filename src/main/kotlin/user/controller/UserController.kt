package com.wafflestudio.seminar.spring2023.user.controller

import com.wafflestudio.seminar.spring2023.user.service.AuthenticateException
import com.wafflestudio.seminar.spring2023.user.service.Authenticated
import com.wafflestudio.seminar.spring2023.user.service.SignInInvalidPasswordException
import com.wafflestudio.seminar.spring2023.user.service.SignInUserNotFoundException
import com.wafflestudio.seminar.spring2023.user.service.SignUpBadPasswordException
import com.wafflestudio.seminar.spring2023.user.service.SignUpBadUsernameException
import com.wafflestudio.seminar.spring2023.user.service.SignUpUsernameConflictException
import com.wafflestudio.seminar.spring2023.user.service.User
import com.wafflestudio.seminar.spring2023.user.service.UserException
import com.wafflestudio.seminar.spring2023.user.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(
    private val userService: UserService,
) {

    @PostMapping("/api/v1/signup")
    fun signup(
        @RequestBody request: SignUpRequest,
    ) {
        userService.signUp(
            username = request.username,
            password = request.password,
            image = request.image
        )
    }

    @PostMapping("/api/v1/signin")
    fun signIn(
        @RequestBody request: SignInRequest,
    ): SignInResponse {
        val user = userService.signIn(
            username = request.username,
            password = request.password
        )

        return SignInResponse(user.getAccessToken())
    }

    @GetMapping("/api/v1/users/me")
    fun me(
        @Authenticated user: User,
    ): UserMeResponse {
        return UserMeResponse(
            username = user.username,
            image = user.image
        )
    }

    @ExceptionHandler
    fun handleException(e: UserException): ResponseEntity<Unit> {
        val status = when (e) {
            is SignUpBadUsernameException, is SignUpBadPasswordException -> 400
            is SignUpUsernameConflictException -> 409
            is SignInUserNotFoundException, is SignInInvalidPasswordException -> 404
            is AuthenticateException -> 401
            else -> 404
        }

        return ResponseEntity.status(status).build()
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
