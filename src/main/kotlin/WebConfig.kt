package com.wafflestudio.seminar.spring2023

import com.wafflestudio.seminar.spring2023.user.service.AuthenticateException
<<<<<<< HEAD:src/main/kotlin/user/UserArgumentResolver.kt
=======
import com.wafflestudio.seminar.spring2023.user.service.Authenticated
>>>>>>> 6f4df81c3a062e525f0ffbd2e4c8d81cb3ec020e:src/main/kotlin/WebConfig.kt
import com.wafflestudio.seminar.spring2023.user.service.User
import com.wafflestudio.seminar.spring2023.user.service.UserService
import org.springframework.context.annotation.Configuration
import org.springframework.core.MethodParameter
import org.springframework.stereotype.Component
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
<<<<<<< HEAD:src/main/kotlin/user/UserArgumentResolver.kt
        //TODO()
        return parameter.parameterType.equals(User::class.java)
=======
        return parameter.parameterType == User::class.java
>>>>>>> 6f4df81c3a062e525f0ffbd2e4c8d81cb3ec020e:src/main/kotlin/WebConfig.kt
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?,
<<<<<<< HEAD:src/main/kotlin/user/UserArgumentResolver.kt
    ): User {
        //TODO()
        val token = webRequest.getHeader("Authorization")?.removePrefix("Bearer ") ?: throw AuthenticateException()

        return userService.authenticate(token)
    }
}
=======
    ): User? {
        return runCatching {
            val accessToken = requireNotNull(
                webRequest.getHeader("Authorization")?.split(" ")?.get(1)
            )
>>>>>>> 6f4df81c3a062e525f0ffbd2e4c8d81cb3ec020e:src/main/kotlin/WebConfig.kt

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
