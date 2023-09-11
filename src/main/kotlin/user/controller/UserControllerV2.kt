package com.wafflestudio.seminar.spring2023.user.controller

import com.wafflestudio.seminar.spring2023.user.AuthUser
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
        userService.signUp(request.username, request.password, request.image)
    }

    @PostMapping("/api/v2/signin")
    fun signIn(
        @RequestBody request: SignInRequest,
    ): SignInResponse {
        return SignInResponse(userService.signIn(request.username, request.password).getAccessToken())
    }

    @GetMapping("/api/v2/users/me")
    fun me(@AuthUser user: User): UserMeResponse {
        return UserMeResponse(username = user.username, image = user.image)
    }

    @ExceptionHandler(value = [UserException::class])
    fun handleException(e: UserException): ResponseEntity<Unit> {
        return when (e) {
            is SignUpBadUsernameException -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null)
            is SignUpBadPasswordException -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null)
            is SignUpUsernameConflictException -> ResponseEntity.status(HttpStatus.CONFLICT).body(null)
            is SignInUserNotFoundException -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null)
            is SignInInvalidPasswordException -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null)
            is AuthenticateException -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null)
        }
    }
}
