package com.wafflestudio.seminar.spring2023.user.service

import com.wafflestudio.seminar.spring2023.user.repository.UserEntity
import com.wafflestudio.seminar.spring2023.user.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
) : UserService {

    override fun signUp(username: String, password: String, image: String): User {
        when {
            username.length < 4 -> throw SignUpBadUsernameException()
            password.length < 4 -> throw SignUpBadPasswordException()
            userRepository.findByUsername(username) != null -> throw SignUpUsernameConflictException()
        }
        userRepository.save(UserEntity(username = username, password = password, image = image))
        return User(username = username, image = image)
    }

    override fun signIn(username: String, password: String): User {
        val user = userRepository.findByUsername(username) ?: throw SignInUserNotFoundException()
        if(password != user.password) throw SignInInvalidPasswordException()
        return User(username = username, image = user.image)
    }

    override fun authenticate(accessToken: String): User {
        val user = userRepository.findByUsername(accessToken.reversed()) ?: throw AuthenticateException()
        return User(username = user.username, image = user.image)
    }
}
