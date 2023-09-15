package com.wafflestudio.seminar.spring2023.user.controller

import com.wafflestudio.seminar.spring2023.user.service.*
import org.springframework.http.HttpStatus
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
        userService.signUp(username = request.username, password = request.password,
            image = request.image)
    }

    @PostMapping("/api/v2/signin")
    fun signIn(
        @RequestBody request: SignInRequest,
    ): SignInResponse {
        val user = userService.signIn(username = request.username, password = request.password)
        return SignInResponse(accessToken = user.getAccessToken())
    }

    @GetMapping("/api/v2/users/me")
    fun me(user: User): UserMeResponse {
        return UserMeResponse(username = user.username, image = user.image)
    }

    @ExceptionHandler
    fun handleException(e: UserException): ResponseEntity<Unit> {
        return when(e) {
            is SignUpBadUsernameException, is SignUpBadPasswordException -> ResponseEntity.badRequest().build()
            is SignUpUsernameConflictException -> ResponseEntity.status(HttpStatus.CONFLICT).build()
            is SignInUserNotFoundException, is SignInInvalidPasswordException -> ResponseEntity.notFound().build()
            is AuthenticateException -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
        }
    }
}
