package com.wafflestudio.seminar.spring2023.user.controller

<<<<<<< HEAD
import com.wafflestudio.seminar.spring2023.user.service.*
=======
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
>>>>>>> 6f4df81c3a062e525f0ffbd2e4c8d81cb3ec020e
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import kotlin.Exception

@RestController
class UserController(
    private val userService: UserService,
) {

    @PostMapping("/api/v1/signup")
    fun signup(
        @RequestBody request: SignUpRequest,
<<<<<<< HEAD
    ): ResponseEntity<Unit> {
        //TODO()
        try {
            userService.signUp(request.username,request.password,request.image)
            return ResponseEntity.ok().build()
        }
        catch (e:SignUpBadUsernameException){
            return ResponseEntity.status(400).build()
        }
        catch (e:SignUpBadPasswordException){
            return ResponseEntity.status(400).build()
        }
        catch (e:SignUpUsernameConflictException){
            return ResponseEntity.status(409).build()
        }
=======
    ) {
        userService.signUp(
            username = request.username,
            password = request.password,
            image = request.image
        )
>>>>>>> 6f4df81c3a062e525f0ffbd2e4c8d81cb3ec020e
    }

    @PostMapping("/api/v1/signin")
    fun signIn(
        @RequestBody request: SignInRequest,
<<<<<<< HEAD
    ): ResponseEntity<SignInResponse> {
        //TODO()
        return try {
            val user = userService.signIn(request.username,request.password)
            ResponseEntity.ok().body(SignInResponse(user.getAccessToken()))
        } catch (e:Exception){
            ResponseEntity.status(404).build()
        }
=======
    ): SignInResponse {
        val user = userService.signIn(
            username = request.username,
            password = request.password
        )

        return SignInResponse(user.getAccessToken())
>>>>>>> 6f4df81c3a062e525f0ffbd2e4c8d81cb3ec020e
    }

    @GetMapping("/api/v1/users/me")
    fun me(
<<<<<<< HEAD
        @RequestHeader(name = "Authorization", required = false) authorizationHeader: String?,
    ): ResponseEntity<UserMeResponse> {
        //TODO()
        if(authorizationHeader==null) return ResponseEntity.status(401).build()
        return try {
            val token= authorizationHeader.removePrefix("Bearer ")
            val user = userService.authenticate(token)
            ResponseEntity.ok().body(UserMeResponse(user.username,user.image))
        }
        catch (e:AuthenticateException){
            ResponseEntity.status(401).build()
        }
=======
        @Authenticated user: User,
    ): UserMeResponse {
        return UserMeResponse(
            username = user.username,
            image = user.image
        )
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
>>>>>>> 6f4df81c3a062e525f0ffbd2e4c8d81cb3ec020e
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
