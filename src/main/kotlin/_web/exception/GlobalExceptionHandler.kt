package com.wafflestudio.seminar.spring2023._web.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler
    fun handleAuthException(e: AuthenticateException) = ResponseEntity.status(HttpStatus.UNAUTHORIZED).build<Unit>()
}
