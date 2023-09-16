package com.wafflestudio.seminar.spring2023.user.controller

import com.wafflestudio.seminar.spring2023.user.service.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController


// TODO: 추가 과제 ExceptionHandler와 HandlerMethodArgumentResolver 적용
@RestController
class UserControllerV2(
    private val userService: UserService,
) {

    @PostMapping("/api/v2/signup")
    fun signup(
        @RequestBody request: SignUpRequest,
    ) {
        userService.signUp(request.username,request.password,request.image)
        return Unit
    }

    @PostMapping("/api/v2/signin")
    fun signIn(
        @RequestBody request: SignInRequest,
    ): SignInResponse {
        userService.signIn(request.username,request.password)
        return SignInResponse(request.username)
    }

    @GetMapping("/api/v2/users/me")
    fun me(user: User): UserMeResponse {
        return UserMeResponse(user.username, user.image)
    }

    @ExceptionHandler
    fun handleException(e: UserException): ResponseEntity<Unit> {
        return when (e) {
            is SignUpBadUsernameException -> ResponseEntity.status(400).build()
            is SignUpUsernameConflictException -> ResponseEntity.status(409).build()
            is SignUpBadUsernameException -> ResponseEntity.status(400).build()
            is SignInUserNotFoundException -> ResponseEntity.status(404).build()
            is SignInInvalidPasswordException -> ResponseEntity.status(404).build()
            is AuthenticateException -> ResponseEntity.status(401).build()
            else -> ResponseEntity.status(400).build()
        }
    }

}
