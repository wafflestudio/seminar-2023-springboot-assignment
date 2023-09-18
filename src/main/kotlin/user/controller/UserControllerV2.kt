package com.wafflestudio.seminar.spring2023.user.controller

import com.wafflestudio.seminar.spring2023.user.service.*
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode  //응답 상태를 내려줄 HttpStatus가 정의가 안되있길래 넣었다
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController


/* TODO: 추가 과제 ExceptionHandler와 HandlerMethodArgumentResolver 적용
원래 UserController 구현시에는 각각의 동작마다 예외를 따로 처리해야해서 굉장히 복잡했음
-> ExceptionHandler라는 annotation을 통해 예외처리 객체를 따로 생성함으로써 예외를 한방에 처리할 수 있다
원래 UserController 구현시에는 인증토큰 확인 및 유저 응답처리시 직접 요청 Header에서 authorization 부분의 토큰을 떼와서 처리해야 하는 불편함이 있었음
me 함수의 파라미터를 HandlerMethodArgumentResolver를 통해서 따로 간단히 처리하고자 함
HandlerMethodArgumentResolver를 쓰면 User의 파라미터들을 간단하게 처리할 수 있다
-> UserArgumentResolver에서 사용해서 불러오므로 거기서 자세히 설명한다*/
@RestController
class UserControllerV2(
    private val userService: UserService,
) {

    @PostMapping("/api/v2/signup")
    fun signup(
        @RequestBody request: SignUpRequest,
    ) {
        userService.signUp(request.username,request.password,request.image) //예외처리가 여기서는 필요 없으므로 signup만 시켜주면 된다
    }

    @PostMapping("/api/v2/signin")
    fun signIn(
        @RequestBody request: SignInRequest,
    ): SignInResponse {
        val user = userService.signIn(request.username,request.password)
        return SignInResponse(user.getAccessToken()) //예외처리가 필요 없으므로 요청사항에 맞는 유저를 찾아서 인증 토큰을 응답해주면 된다.
    }

    @GetMapping("/api/v2/users/me")
    fun me(user: User): UserMeResponse {
        return UserMeResponse(user.username,user.image) //UserArgumentResolver에서 파라미터와 로직처리를 다 해주므로 그냥 들어온 유저네임과 인증토큰을 가지고 인증여부 응답만 내려주면 된다
    }

    @ExceptionHandler //모든 종류의 예외처리를 따로 빼서 한방에 진행할 수 있는 annotation이다
    fun handleException(e: UserException): ResponseEntity<Unit> {
        return when(e){
            is SignUpBadUsernameException, is SignUpBadPasswordException -> ResponseEntity(HttpStatus.BAD_REQUEST) //400 에러 구현
            is SignUpUsernameConflictException -> ResponseEntity(HttpStatus.CONFLICT) //409 에러 구현
            is SignInUserNotFoundException, is SignInInvalidPasswordException -> ResponseEntity(HttpStatus.NOT_FOUND) //404 에러 구현
            is AuthenticateException -> ResponseEntity(HttpStatus.UNAUTHORIZED) //401 에러 구현
        }
    }
}
