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

        val entity = userRepository.save(
                UserEntity(
                        username = username,
                        password = password,
                        image = image
                )
        )

        return User(entity)
    }

    override fun signIn(username: String, password: String): User {
        val entity = userRepository.findByUsername(username) ?: throw SignInUserNotFoundException()

        if (entity.password != password) {
            throw SignInInvalidPasswordException()
        }

        return User(entity)
    }

    override fun authenticate(accessToken: String): User {
        val entity = userRepository.findByUsername(accessToken.reversed()) ?: throw AuthenticateException()

        return User(entity)
    }
}

fun User(entity: UserEntity) = User(
        id = entity.id,
        username = entity.username,
        image = entity.image,
)