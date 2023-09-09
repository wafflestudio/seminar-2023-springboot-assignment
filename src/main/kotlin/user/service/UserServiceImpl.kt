package com.wafflestudio.seminar.spring2023.user.service

import com.wafflestudio.seminar.spring2023.user.repository.UserEntity
import com.wafflestudio.seminar.spring2023.user.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
) : UserService {

    override fun signUp(username: String, password: String, image: String): User {
        if (userRepository.findByUsername(username) != null) throw SignUpUsernameConflictException()
        if (username.length < 4) throw SignUpBadUsernameException()
        if (password.length < 4) throw SignUpBadPasswordException()

        val newUserEntity = UserEntity(
            id = userRepository.count() + 1,
            username = username,
            password = password,
            image = image
        )
        userRepository.save(newUserEntity)
        return User(username = username, image = image)
    }

    override fun signIn(username: String, password: String): User {
        val userEntity = userRepository.findByUsername(username)
            ?: throw SignInUserNotFoundException()
        if (userEntity.password != password) throw SignInInvalidPasswordException()
        return User(username, userEntity.image)
    }

    override fun authenticate(accessToken: String): User {
        return userRepository.findAll()
            .map { User(it.username, it.image) }
            .find { it.getAccessToken() == accessToken }
            ?: throw AuthenticateException()
    }
}
