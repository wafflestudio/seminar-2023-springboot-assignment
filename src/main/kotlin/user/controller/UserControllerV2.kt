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
        userService.signUp(request.username, request.password, request.image)
    }

    @PostMapping("/api/v2/signin")
    fun signIn(
        @RequestBody request: SignInRequest,
    ): SignInResponse {
        val user = userService.signIn(request.username, request.password)
        return SignInResponse(accessToken = user.getAccessToken())
    }

    @GetMapping("/api/v2/users/me")
    fun me(user: User): UserMeResponse {
        return UserMeResponse(
            username = user.username,
            image = user.image
        )
    }

    @ExceptionHandler(SignUpBadUsernameException::class)
    fun handleSignUpBadUsername(): ResponseEntity<Unit> = ResponseEntity.status(HttpStatus.BAD_REQUEST).build()

    @ExceptionHandler(SignUpBadPasswordException::class)
    fun handleSignUpBadPassword(): ResponseEntity<Unit> = ResponseEntity.status(HttpStatus.BAD_REQUEST).build()

    @ExceptionHandler(SignUpUsernameConflictException::class)
    fun handleSignUpConflict(): ResponseEntity<Unit> = ResponseEntity.status(HttpStatus.CONFLICT).build()

    @ExceptionHandler(SignInUserNotFoundException::class)
    fun handleSignInUserNotFound(): ResponseEntity<Unit> = ResponseEntity.status(HttpStatus.NOT_FOUND).build()

    @ExceptionHandler(SignInInvalidPasswordException::class)
    fun handleSignInInvalidPassword(): ResponseEntity<Unit> = ResponseEntity.status(HttpStatus.NOT_FOUND).build()

    @ExceptionHandler(AuthenticateException::class)
    fun handleAuthenticationFailed(): ResponseEntity<Unit> = ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
}
