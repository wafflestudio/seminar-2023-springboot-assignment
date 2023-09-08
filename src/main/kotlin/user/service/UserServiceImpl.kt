package com.wafflestudio.seminar.spring2023.user.service

import com.wafflestudio.seminar.spring2023.user.repository.UserEntity
import com.wafflestudio.seminar.spring2023.user.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
) : UserService {

    override fun signUp(username: String, password: String, image: String): User {
        // check username and password length
        if (username.length < 4)
            throw SignUpBadUsernameException()
        if (password.length < 4)
            throw SignUpBadPasswordException()

        // check username conflict
        if (userRepository.existsByUsername(username))
            throw SignUpUsernameConflictException()

        return userRepository.saveAndFlush(
            UserEntity(
                username = username,
                password = password,
                image = image,
            )
        ).toUser()
    }

    override fun signIn(username: String, password: String): User {
        val user = userRepository.findByUsername(username) ?: throw SignInUserNotFoundException()
        if (user.password != password)
            throw SignInInvalidPasswordException()
        return user.toUser()
    }

    override fun authenticate(accessToken: String): User {
        val username = accessToken.reversed()
        val user = userRepository.findByUsername(username) ?: throw AuthenticateException()
        return user.toUser()
    }
}
