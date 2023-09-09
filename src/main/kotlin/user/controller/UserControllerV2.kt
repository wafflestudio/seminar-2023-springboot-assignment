package com.wafflestudio.seminar.spring2023.user.controller

import com.wafflestudio.seminar.spring2023.user.service.*
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
    val (username, password, image) = request
    userService.signUp(username, password, image)
  }

  @PostMapping("/api/v2/signin")
  fun signIn(
    @RequestBody request: SignInRequest,
  ): SignInResponse {
    val (username, password) = request
    val accessToken = userService.signIn(username, password).getAccessToken()
    return SignInResponse(accessToken)

  }

  @GetMapping("/api/v2/users/me")
  fun me(user: User): UserMeResponse {
    val (username, image) = user
    return UserMeResponse(username, image)
  }

  @ExceptionHandler
  fun handleException(e: UserException): ResponseEntity<Unit> {
    val statusCode = when (e) {
      is SignUpBadUsernameException -> 400
      is SignUpBadPasswordException -> 400
      is SignUpUsernameConflictException -> 409
      is SignInUserNotFoundException -> 404
      is SignInInvalidPasswordException -> 404
      is AuthenticateException -> 401
    }
    return ResponseEntity.status(statusCode).body(Unit)
  }
}
