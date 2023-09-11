package com.wafflestudio.seminar.spring2023.user.service

import com.wafflestudio.seminar.spring2023.user.repository.UserEntity
import com.wafflestudio.seminar.spring2023.user.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
) : UserService {

    override fun signUp(username: String, password: String, image: String): User {
        if (username.length < 4) {
            throw SignUpBadUsernameException()
        }
        if (password.length < 4) {
            throw SignUpBadPasswordException()
        }
        if (userRepository.existsByUsername(username)) {
            throw SignUpUsernameConflictException()
        }
        userRepository.save(UserEntity(username = username, password = password, image = image))
        return User(username, image)
    }

    override fun signIn(username: String, password: String): User {
        userRepository.findByUsername(username)?.let { user ->
            if (user.password != password) {
                throw SignInInvalidPasswordException()
            }
            return User(user.username, user.image)
        } ?: throw SignInUserNotFoundException()
    }

    override fun authenticate(accessToken: String): User {
        userRepository.findByUsername(accessToken.reversed())?.let { user ->
            return User(user.username, user.image)
        } ?: throw AuthenticateException()
    }
}
