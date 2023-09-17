package com.wafflestudio.seminar.spring2023.user.service

import com.wafflestudio.seminar.spring2023.user.repository.UserEntity
import com.wafflestudio.seminar.spring2023.user.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
) : UserService {
    fun convertToUser(userEntity: UserEntity): User {
        return User(
                username = userEntity.username,
                image = userEntity.image
        )
    }
    override fun signUp(username: String, password: String, image: String): User {

        if (username.length < 4) {
            throw SignUpBadUsernameException()
        }
        if (password.length < 4) {
            throw SignUpBadPasswordException()
        }
        userRepository.findByUsername(username)?.let {
            throw SignUpUsernameConflictException()
        }

        val userEntity = UserEntity(username = username, password = password, image = image)
        userRepository.save(userEntity)

        return convertToUser(userEntity)
    }

    override fun signIn(username: String, password: String): User {

        val findUser = userRepository.findByUsername(username)

        findUser?.let {
            if (findUser.password != password) {
                throw SignInInvalidPasswordException()
            }
        }?: run {
            throw SignInUserNotFoundException()
        }

        return convertToUser(findUser)
    }

    override fun authenticate(accessToken: String): User {

        val usernameFromToken = accessToken.reversed()
        val findUser = userRepository.findByUsername(usernameFromToken)?:let {
            throw AuthenticateException()
        }

        return convertToUser(findUser)
    }
}
