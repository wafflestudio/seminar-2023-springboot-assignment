package com.wafflestudio.seminar.spring2023.user.service

import com.wafflestudio.seminar.spring2023.user.repository.UserEntity
import com.wafflestudio.seminar.spring2023.user.repository.UserRepository
import com.wafflestudio.seminar.spring2023.user.repository.toUser
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
) : UserService {

    companion object {
        const val MIN_USERNAME_LENGTH = 4
        const val MIN_PASSWORD_LENGTH = 4
    }
    override fun signUp(username: String, password: String, image: String): User {
        if (username.length < MIN_USERNAME_LENGTH) {
            throw SignUpBadUsernameException()
        }
        if (password.length < MIN_PASSWORD_LENGTH) {
            throw SignUpBadPasswordException()
        }
        userRepository.findByUsername(username)?.let {
            throw SignUpUsernameConflictException()
        }

        val userEntity = UserEntity(username = username, password = password, image = image)
        userRepository.save(userEntity)
        return userEntity.toUser()
    }

    override fun signIn(username: String, password: String): User {
        val userEntity = userRepository.findByUsername(username)
            ?: throw SignInUserNotFoundException()

        if (userEntity.password != password) {
            throw SignInInvalidPasswordException()
        }
        return userEntity.toUser()
    }

    override fun authenticate(accessToken: String): User {
        val usernameFromToken = accessToken.reversed() // Since getAccessToken is simply reversing the username
        val userEntity = userRepository.findByUsername(usernameFromToken)
            ?: throw AuthenticateException()
        return userEntity.toUser()
    }
}
