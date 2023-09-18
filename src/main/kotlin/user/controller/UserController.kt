package com.wafflestudio.seminar.spring2023.user.controller

import com.wafflestudio.seminar.spring2023.user.service.*
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController

//요청과 응답을 처리하는 컨트롤러를 설계하자
@RestController
class UserController(
    private val userService: UserService,
        //UserService라는 인터베이스를 상속받기
) {

    //요청 경로에 맞게 기능 함수를 매핑해주자
    //응답 형식에 맞춘 응답을 내려주기 위해 ResponseEntity 기능을 사용
    //회원가입시에 유저 이름 혹은 비밀번호가 정해진 규칙에 맞지 않는 경우 400 응답을 내려준다 구현
    //회원가입시에 이미 해당 유저 이름이 존재하면 409 응답을 내려준다 구현
    @PostMapping("/api/v1/signup")
    fun signup(
        @RequestBody request: SignUpRequest,
    ): ResponseEntity<Unit> {
        //try-catch 구문으로 예외처리하자
        return try{ //올바른 요청인 경우
            userService.signUp(request.username, request.password, request.image) //인터페이스에 해당 유저 추가
            ResponseEntity.status(HttpStatus.OK).build() //올바르다는 응답 반환
        } catch (e:SignUpBadUsernameException){ //유저 이름이 잘못 되었을 때 400 응답 반환
            ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
        } catch (e:SignUpBadPasswordException){//비밀번호가 잘못 되었을 때 400 응답 반환
            ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
        } catch (e:SignUpUsernameConflictException){ //이미 데이터베이스에 있는 유저네임일 경우 409 응답 반환
            ResponseEntity.status(HttpStatus.CONFLICT).build()
        }
    }

    //로그인 요청에서의 응답 반환
    //로그인 정보가 정확하지 않으면 404 응답을 내려준다 구현
    @PostMapping("/api/v1/signin")
    fun signIn(
        @RequestBody request: SignInRequest,
    ): ResponseEntity<SignInResponse> {
        //마찬가지로 try-catch 구문으로 예외처리
        //test 파일에서는 username이 존재하지 않거나, password가 틀린 경우를 예외 처리하도록 지시하고 있음
        return try{
            val user = userService.signIn(request.username,request.password) //정상적인 경우 인터페이스에 signIn함
            ResponseEntity.status(HttpStatus.OK).body(SignInResponse(user.getAccessToken())) //정상적인 경우 올바르다는 응답과 함께 응답 값으로 accesstoken을 내려준다
        } catch(e:SignInUserNotFoundException){//username이 존재하지 않는 경우 처리
            ResponseEntity.status(HttpStatus.NOT_FOUND).build()
        } catch(e:SignInInvalidPasswordException){//password가 틀린경우 예외 처리
            ResponseEntity.status(HttpStatus.NOT_FOUND).build()
        }
    }

    //들어온 인증토큰이 올바른 인증토큰인지 조회한다
    //잘못된 인증 토큰으로 인증시 401 응답을 내려준다 구현
    @GetMapping("/api/v1/users/me")
    fun me(
        @RequestHeader(name = "Authorization", required = false) authorizationHeader: String?,
    ): ResponseEntity<UserMeResponse> {
        //일단 인증토큰이 있는지부터 점검(Elvis 연산자 사용)
        val token = authorizationHeader?.removePrefix("Bearer ") ?: return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build() //왜인지는 잘 모르겠으나 authorizationHeader의 앞에 붙어서 들어오는 Bearer를 떼야하는거 같다

        //try-catch 구문으로 그 인증토큰이 올바른지 예외 처리
        return try{ //올바른 경우에는 userService 인터페이스에서 해당 토큰을 가진 유저를 불러와서, 응답으로 그 유저의 정보를 담은 UserMeResponse를 내려주어야 함
            val user = userService.authenticate(token)
            ResponseEntity.status(HttpStatus.OK).body(UserMeResponse(user.username,user.image))
        }catch(e:AuthenticateException){//올바른 인증토큰이 아닐 경우 예외 처리
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
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
