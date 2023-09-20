package com.wafflestudio.seminar.spring2023.user.controller


import com.wafflestudio.seminar.spring2023.user.service.*
import org.springframework.http.HttpStatus
import com.wafflestudio.seminar.spring2023.user.service.AuthenticateException
import com.wafflestudio.seminar.spring2023.user.service.Authenticated
import com.wafflestudio.seminar.spring2023.user.service.SignInInvalidPasswordException
import com.wafflestudio.seminar.spring2023.user.service.SignInUserNotFoundException
import com.wafflestudio.seminar.spring2023.user.service.SignUpBadPasswordException
import com.wafflestudio.seminar.spring2023.user.service.SignUpBadUsernameException
import com.wafflestudio.seminar.spring2023.user.service.SignUpUsernameConflictException
import com.wafflestudio.seminar.spring2023.user.service.User
import com.wafflestudio.seminar.spring2023.user.service.UserException
import com.wafflestudio.seminar.spring2023.user.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class UserController(
    private val userService: UserService,
) {
    @PostMapping("/api/v1/signup")
    fun signup(
        @RequestBody request: SignUpRequest
    ): ResponseEntity<Unit> {

        return try{
            userService.signUp(request.username, request.password, request.image)
            ResponseEntity(HttpStatus.OK)
        }catch (e : SignUpBadUsernameException){
            ResponseEntity(HttpStatus.BAD_REQUEST)
        }catch (e : SignUpBadPasswordException){
            ResponseEntity(HttpStatus.BAD_REQUEST)
        }catch (e : SignUpUsernameConflictException){
            ResponseEntity(HttpStatus.CONFLICT)
        }
    }

    @PostMapping("/api/v1/signin")
    fun signIn(
        @RequestBody request: SignInRequest,
    ): ResponseEntity<SignInResponse> {

        return try{
            val user = userService.signIn(request.username, request.password)
            val accessToken = user.getAccessToken()
            ResponseEntity(SignInResponse(accessToken),HttpStatus.OK)
        }catch (e : SignInUserNotFoundException){
            ResponseEntity(null,HttpStatus.NOT_FOUND)
        }catch (e : SignInInvalidPasswordException){
            ResponseEntity(null, HttpStatus.NOT_FOUND)
        }
    }

    @GetMapping("/api/v1/users/me")
    fun me(
        @RequestHeader(name = "Authorization", required = false) authorizationHeader: String?,
    ): ResponseEntity<UserMeResponse> {

        return try {
            if(authorizationHeader == null){
                ResponseEntity(null,HttpStatus.UNAUTHORIZED)
            }else{
                val user = userService.authenticate(authorizationHeader.split(" ")[1])
                ResponseEntity(UserMeResponse(user.username, user.image),HttpStatus.OK)
            }
        }catch (e : AuthenticateException){
            ResponseEntity(null, HttpStatus.UNAUTHORIZED)
        }

    }

    @ExceptionHandler
    fun handleException(e: UserException): ResponseEntity<Unit> {
        val status = when (e) {
            is SignUpBadUsernameException, is SignUpBadPasswordException -> 400
            is SignUpUsernameConflictException -> 409
            is SignInUserNotFoundException, is SignInInvalidPasswordException -> 404
            is AuthenticateException -> 401
        }

        return ResponseEntity.status(status).build()
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
