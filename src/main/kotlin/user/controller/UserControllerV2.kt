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
        userService.signUp(request.username, request.password, request.image)
    }

    @PostMapping("/api/v2/signin")
    fun signIn(
        @RequestBody request: SignInRequest,
    ): SignInResponse {
        val accessToken = userService.signIn(request.username, request.password).getAccessToken()
        return SignInResponse(accessToken)
    }

    @GetMapping("/api/v2/users/me")
    fun me(user: User): UserMeResponse {
        return UserMeResponse(user.username, user.image)
    }

    @ExceptionHandler
    fun handleException(e: UserException): ResponseEntity<Unit> {
        when (e){
            is SignUpBadUsernameException-> return ResponseEntity.status(400).build()
            is SignUpBadPasswordException-> return ResponseEntity.status(400).build()
            is SignUpUsernameConflictException-> return ResponseEntity.status(409).build()
            is SignInInvalidPasswordException-> return ResponseEntity.status(404).build()
            is SignInUserNotFoundException-> return ResponseEntity.status(404).build()
            is AuthenticateException-> return ResponseEntity.status(401).build()
            else-> return ResponseEntity.status(405).build()
        }
    }
}
