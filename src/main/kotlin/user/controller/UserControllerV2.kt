package com.wafflestudio.seminar.spring2023.user.controller

import com.wafflestudio.seminar.spring2023.user.service.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import kotlin.reflect.typeOf


// TODO: 추가 과제 ExceptionHandler와 HandlerMethodArgumentResolver 적용
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
        return SignInResponse(userService.signIn(request.username, request.password).getAccessToken())
    }

    @GetMapping("/api/v2/users/me")
    fun me(user: User): UserMeResponse {
        return UserMeResponse(user.username, user.image)
    }

    @ExceptionHandler
    fun handleException(e: UserException): ResponseEntity<Unit> {
        val statusCode = when (e) {
            is SignUpBadUsernameException, is SignUpBadPasswordException -> 400
            is AuthenticateException -> 401
            is SignInInvalidPasswordException, is SignInUserNotFoundException -> 404
            is SignUpUsernameConflictException -> 409
        }
        return ResponseEntity.status(statusCode).build()
    }
}
