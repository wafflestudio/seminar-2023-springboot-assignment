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
        } else if (password.length < 4) {
            throw SignUpBadPasswordException()
        } else if (userRepository.findByUsername(username) != null) {
            throw SignUpUsernameConflictException()
        } else {
            val user = UserEntity(username=username, password=password, image=image)
            userRepository.save(user)
            return User(username, image)
        }
    }

    override fun signIn(username: String, password: String): User {
        val user = userRepository.findByUsername(username)
        if (user == null) {
            throw SignInUserNotFoundException()
        } else if (user.password != password) {
            throw SignInInvalidPasswordException()
        } else {
            return User(user.username, user.image)
        }
    }

    override fun authenticate(accessToken: String): User {
        val user = userRepository.findByUsername(accessToken.reversed())
        if (user == null) {
            throw AuthenticateException()
        } else {
            return User(user.username, user.image)
        }
    }
}
