package com.wafflestudio.seminar.spring2023.user.service

import com.wafflestudio.seminar.spring2023.user.repository.UserEntity
import com.wafflestudio.seminar.spring2023.user.repository.UserRepository
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
            throw SignUpBadUsernameException()}
        if (password.length < MIN_PASSWORD_LENGTH) {
            throw SignUpBadPasswordException()}
        if (userRepository.findByUsername(username) != null) {
            throw SignUpUsernameConflictException()
        }
        val userEntity = UserEntity(username = username, password = password, image = image)
        userRepository.save(userEntity)
        return User(userEntity.username,userEntity.image)

        }


    override fun signIn(username: String, password: String): User {
        val userEntity = userRepository.findByUsername(username)
            ?:throw SignInUserNotFoundException()

        if (userEntity.password != password) {
            throw SignInInvalidPasswordException()
        }
        return User(userEntity.username,userEntity.image)

    }

    override fun authenticate(accessToken: String): User {
        val usernameFromToken = accessToken.reversed()
        val userEntity = userRepository.findByUsername(usernameFromToken)
            ?: throw AuthenticateException()
        return User(userEntity.username,userEntity.image)

    }
}
