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
        } else {
            val userList = userRepository.findAll()
            for (users in userList) {
                if (users.username.equals(username)) {
                    throw SignUpUsernameConflictException()
                }
            }
            val userEntity = UserEntity(username = username, password = password, image = image)
            userRepository.save(userEntity)
            return User(userEntity.username, userEntity.image)
        }
    }

    override fun signIn(username: String, password: String): User {
        val userList = userRepository.findAll()
        for (users in userList) {
            if (users.username.equals(username)) {
                if (users.password.equals(password)) {
                    return User(username, password)
                } else {
                    throw SignInInvalidPasswordException()
                }
            }
        }
        throw SignInUserNotFoundException()
    }

    override fun authenticate(accessToken: String): User {
        val userList = userRepository.findAll()
        for (users in userList) {
            if (users.username.equals(accessToken.reversed())) {
                return User(users.username, users.image)
            }
        }
        throw AuthenticateException()
    }
}
