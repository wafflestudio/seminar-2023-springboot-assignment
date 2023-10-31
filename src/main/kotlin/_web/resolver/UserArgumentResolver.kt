package com.wafflestudio.seminar.spring2023._web.resolver

import com.wafflestudio.seminar.spring2023._web.exception.AuthenticateException
import com.wafflestudio.seminar.spring2023.user.service.Authenticated
import com.wafflestudio.seminar.spring2023.user.service.User
import com.wafflestudio.seminar.spring2023.user.service.UserService
import org.springframework.core.MethodParameter
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer

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
