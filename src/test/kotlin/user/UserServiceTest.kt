package com.wafflestudio.seminar.spring2023.user

import com.wafflestudio.seminar.spring2023._web.exception.AuthenticateException
import com.wafflestudio.seminar.spring2023.user.service.SignInInvalidPasswordException
import com.wafflestudio.seminar.spring2023.user.service.SignInUserNotFoundException
import com.wafflestudio.seminar.spring2023.user.service.SignUpBadPasswordException
import com.wafflestudio.seminar.spring2023.user.service.SignUpBadUsernameException
import com.wafflestudio.seminar.spring2023.user.service.SignUpUsernameConflictException
import com.wafflestudio.seminar.spring2023.user.service.UserService
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class UserServiceTest @Autowired constructor(
    private val userService: UserService,
) {

    @Test
    fun `유저 이름과 비밀번호는 4글자 이상이어야 한다`() {
        assertThrows<SignUpBadUsernameException> {
            userService.signUp(
                username = "bad",
                password = "correctPassword",
                image = "https://wafflestudio.com/images/icon_intro.svg"
            )
        }

        assertThrows<SignUpBadPasswordException> {
            userService.signUp(
                username = "test-${javaClass.name}-1",
                password = "bad",
                image = "https://wafflestudio.com/images/icon_intro.svg"
            )
        }

        val user = assertDoesNotThrow {
            userService.signUp(
                username = "test-${javaClass.name}-1",
                password = "correctPassword",
                image = "https://wafflestudio.com/images/icon_intro.svg"
            )
        }

        assertThat(user.username).isEqualTo("test-${javaClass.name}-1")
        assertThat(user.image).isEqualTo("https://wafflestudio.com/images/icon_intro.svg")
    }

    @Test
    fun `이미 존재하는 유저 이름으로 가입할 수 없다`() {
        assertDoesNotThrow {
            userService.signUp(
                username = "test-${javaClass.name}-2",
                password = "spring",
                image = "https://wafflestudio.com/images/icon_intro.svg"
            )
        }

        assertThrows<SignUpUsernameConflictException> {
            userService.signUp(
                username = "test-${javaClass.name}-2",
                password = "springspring",
                image = "https://wafflestudio.com/images/icon_intro2.svg"
            )
        }
    }


    @Test
    fun `로그인시 유저 이름과 비밀번호가 정확해야 한다`() {
        assertDoesNotThrow {
            userService.signUp(
                username = "test-${javaClass.name}-3",
                password = "spring",
                image = "https://wafflestudio.com/images/icon_intro.svg"
            )
        }

        assertThrows<SignInUserNotFoundException> {
            userService.signIn(
                username = "test-${javaClass.name}-404",
                password = "spring",
            )
        }

        assertThrows<SignInInvalidPasswordException> {
            userService.signIn(
                username = "test-${javaClass.name}-3",
                password = "winter",
            )
        }

        assertDoesNotThrow {
            userService.signIn(
                username = "test-${javaClass.name}-3",
                password = "spring",
            )
        }
    }

    @Test
    fun `유저 식별을 위한 인증 토큰이 정확해야한다`() {
        val user = assertDoesNotThrow {
            userService.signUp(
                username = "test-${javaClass.name}-4",
                password = "spring",
                image = "https://wafflestudio.com/images/icon_intro.svg"
            )
        }

        assertThrows<AuthenticateException> {
            userService.authenticate("wrongAccessToken")
        }

        assertDoesNotThrow {
            userService.authenticate(user.getAccessToken())
        }
    }
}
