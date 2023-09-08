package com.wafflestudio.seminar.spring2023.user.controller

import com.wafflestudio.seminar.spring2023.user.service.*
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController


@RestController
class UserControllerV2(
    private val userService: UserService,
) {

    @PostMapping("/api/v2/signup")
    fun signup(
        @RequestBody request: SignUpRequest,
    ) {
        userService.signUp(request.username, request.password, request.image)
    }

    @PostMapping("/api/v2/signin")
    fun signIn(
        @RequestBody request: SignInRequest,
    ): SignInResponse {
        val user = userService.signIn(request.username, request.password)
        return SignInResponse(user.getAccessToken())
    }

    @GetMapping("/api/v2/users/me")
    fun me(user: User): UserMeResponse {
        return UserMeResponse(user.username, user.image)
    }

    @ExceptionHandler
    fun handleException(e: UserException): ResponseEntity<Unit> {
        return when (e) {
            is SignUpBadUsernameException, is SignUpBadPasswordException -> ResponseEntity(HttpStatus.BAD_REQUEST)
            is SignUpUsernameConflictException -> ResponseEntity(HttpStatus.CONFLICT)
            is SignInUserNotFoundException, is SignInInvalidPasswordException -> ResponseEntity(HttpStatus.NOT_FOUND)
            is AuthenticateException -> ResponseEntity(HttpStatus.UNAUTHORIZED)
        }
    }
}
