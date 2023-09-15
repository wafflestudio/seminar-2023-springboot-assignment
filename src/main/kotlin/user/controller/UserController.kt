package com.wafflestudio.seminar.spring2023.user.controller

import com.wafflestudio.seminar.spring2023.user.service.*
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(
    private val userService: UserService,
) {

    @PostMapping("/api/v1/signup")
    fun signup(
        @RequestBody request: SignUpRequest,
    ): ResponseEntity<Unit> {
        try{
            // request에서 username, password,image를 입력 받아서 userService를 생성
            userService.signUp(request.username,request.password,request.image)
            // 정상적이라면 200응답
            return ResponseEntity.ok().build()
        }
        // 이름에 문제가 있을 경우 400 응답
        catch (e: SignUpBadUsernameException){
            return ResponseEntity.badRequest().build()
        }
        // 비밀번호에 문제가 있을 경우 400 응답
        catch (e: SignUpBadPasswordException){
            return ResponseEntity.badRequest().build()
        }
        // 이미 존재하는 유저 이름으로 가입할 경우 409 응답
        catch (e: SignUpUsernameConflictException){
            return ResponseEntity.status(HttpStatus.CONFLICT).build()
        }
    }

    @PostMapping("/api/v1/signin")
    fun signIn(
        @RequestBody request: SignInRequest,
    ): ResponseEntity<SignInResponse> {
        try{
            // 입력한 이름과 비밀번호로 접속 시도
            val user = userService.signIn(request.username,request.password)
            // 정상적이라면 200 응답
            return ResponseEntity.ok(SignInResponse(accessToken = user.getAccessToken()))
        }
        catch (e: SignInUserNotFoundException){
            // 해당 유저가 존재하지 않을 때 404 응답
            return ResponseEntity.notFound().build()
        }
        catch (e: SignInInvalidPasswordException){
            // 비밀번호가 틀렸을 때 404 응답
            return ResponseEntity.notFound().build()
        }
    }

    @GetMapping("/api/v1/users/me")
    fun me(
        @RequestHeader(name = "Authorization", required = false) authorizationHeader: String?,
    ): ResponseEntity<UserMeResponse> {
        //헤더에서 접두사 Bearer 제거 후 null인지 확인하고 null 이면 AuthenticateException를 발생
        val token = authorizationHeader?.removePrefix("Bearer ") ?: throw AuthenticateException()
        try{
            val user = userService.authenticate(token)
            // 정상적으로 인증이 되었다면 200 응답
            return ResponseEntity.ok(UserMeResponse(username = user.username,image = user.image))
        }
        catch (e : AuthenticateException){
            // 인증에 오류가 있다면 401 응답
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
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
