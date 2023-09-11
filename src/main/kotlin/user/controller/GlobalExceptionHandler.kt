package com.wafflestudio.seminar.spring2023.user.controller

import com.wafflestudio.seminar.spring2023.user.service.*
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(SignUpBadUsernameException::class, SignUpBadPasswordException::class)
    fun handleSignUpBadRequest(): ResponseEntity<Unit> = ResponseEntity.status(HttpStatus.BAD_REQUEST).build()

    @ExceptionHandler(SignUpUsernameConflictException::class)
    fun handleSignUpConflict(): ResponseEntity<Unit> = ResponseEntity.status(HttpStatus.CONFLICT).build()

    @ExceptionHandler(SignInUserNotFoundException::class, SignInInvalidPasswordException::class)
    fun handleSignInNotFound(): ResponseEntity<Unit> = ResponseEntity.status(HttpStatus.NOT_FOUND).build()

    @ExceptionHandler(AuthenticateException::class)
    fun handleAuthenticationFailed(): ResponseEntity<Unit> = ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
}