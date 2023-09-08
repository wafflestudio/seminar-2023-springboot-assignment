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
        if (userRepository.findByUsername(username) != null) {
            throw SignUpUsernameConflictException()
        }
        userRepository.save(UserEntity(0L, username, password, image))
        return User(username, image)
    }

    override fun signIn(username: String, password: String): User {
        val userEntity = userRepository.findByUsername(username)
                ?:throw SignInUserNotFoundException()
        if (userEntity.password != password) {
            throw SignInInvalidPasswordException()
        }
        return User(userEntity.username, userEntity.image)
    }

    override fun authenticate(accessToken: String): User {
        val decodedUsername = accessToken.reversed()
        val currentUser: UserEntity = userRepository.findByUsername(decodedUsername)
                ?: throw AuthenticateException()
        return User(currentUser.username, currentUser.image)
    }
}
