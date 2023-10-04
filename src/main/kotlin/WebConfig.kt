package com.wafflestudio.seminar.spring2023

import com.wafflestudio.seminar.spring2023.user.service.Authenticated
import com.wafflestudio.seminar.spring2023.user.service.User
import com.wafflestudio.seminar.spring2023.user.service.UserService
import org.springframework.context.annotation.Configuration
import org.springframework.core.MethodParameter
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig(
    private val userArgumentResolver: UserArgumentResolver,
) : WebMvcConfigurer {

    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        resolvers.add(userArgumentResolver)
    }
}

@Component
class UserArgumentResolver(
    private val userService: UserService,
) : HandlerMethodArgumentResolver {

    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.parameterType == User::class.java
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?,
    ): User? {
        return runCatching {
            val accessToken = requireNotNull(
                webRequest.getHeader("Authorization")?.split(" ")?.get(1)
            )

            userService.authenticate(accessToken)
        }.getOrElse {
            if (parameter.hasParameterAnnotation(Authenticated::class.java)) {
                throw AuthenticateException()
            } else {
                null
            }
        }
    }
}

@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler
    fun handleAuthException(e: AuthenticateException) = ResponseEntity.status(HttpStatus.UNAUTHORIZED).build<Unit>()
}

class AuthenticateException : RuntimeException()
