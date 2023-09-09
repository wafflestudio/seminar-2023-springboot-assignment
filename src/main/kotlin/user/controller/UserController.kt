package com.wafflestudio.seminar.spring2023.user.controller

import com.wafflestudio.seminar.spring2023.user.service.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController
import kotlin.reflect.typeOf

@RestController
class UserController(
  private val userService: UserService,
) {

  @PostMapping("/api/v1/signup")
  fun signup(
    @RequestBody request: SignUpRequest,
  ): ResponseEntity<Unit> {
    val (username, password, image) = request
    return try {
      userService.signUp(username, password, image)
      ResponseEntity.status(200).body(Unit)
    } catch (e: UserException) {
      val statusCode = if (e is SignUpUsernameConflictException) 409 else 400
      ResponseEntity.status(statusCode).body(Unit)
    }
  }

  @PostMapping("/api/v1/signin")
  fun signIn(
    @RequestBody request: SignInRequest,
  ): ResponseEntity<SignInResponse> {
    val (username, password) = request
    return try {
      val accessToken = userService.signIn(username, password).getAccessToken()
      ResponseEntity.status(200).body(SignInResponse(accessToken))
    } catch (e: UserException) {
      ResponseEntity.status(404).body(SignInResponse(""))
    }
  }

  @GetMapping("/api/v1/users/me")
  fun me(
    @RequestHeader(
      name = "Authorization",
      required = false
    ) authorizationHeader: String?,
  ): ResponseEntity<UserMeResponse> {
    val isValidToken =
      (authorizationHeader != null)
          && (authorizationHeader != "Bearer ")
          && authorizationHeader.startsWith("Bearer ")

    val bearerToken =
      if (isValidToken) authorizationHeader!!.split(" ")[1] else ""

    return try {
      val (username, image) = userService.authenticate(bearerToken)
      ResponseEntity.status(200).body(UserMeResponse(username, image))
    } catch (e: AuthenticateException) {
      ResponseEntity.status(401).body(UserMeResponse("", ""))
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
