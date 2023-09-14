package com.wafflestudio.seminar.spring2023.user.service

import com.wafflestudio.seminar.spring2023.user.repository.UserEntity
import com.wafflestudio.seminar.spring2023.user.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
) : UserService {
    override fun signUp(username: String, password: String, image: String): User {
        if (username.length<4) throw SignUpBadUsernameException()
        if (password.length<4) throw SignUpBadPasswordException()
        val dupeUser = userRepository.findByusername(username)
        if (!dupeUser.isEmpty()) throw SignUpUsernameConflictException()
        val signUpUserEntity = UserEntity(username = username, password = password, image = image)
        userRepository.save(signUpUserEntity)
        return User(signUpUserEntity.username, signUpUserEntity.image)
    }

    override fun signIn(username: String, password: String): User {
        val signInUser = userRepository.findByusername(username)
        if (signInUser.isEmpty()) throw SignInUserNotFoundException()
        if (signInUser[0].password==password){
            return User(signInUser[0].username, signInUser[0].image)
        }
        throw SignInInvalidPasswordException()
    }

    override fun authenticate(accessToken: String): User {
        val accessUser = userRepository.findByusername(accessToken.reversed())
        if (accessUser.isEmpty()) throw AuthenticateException()
        return User(accessUser[0].username, accessUser[0].image)
    }
}
