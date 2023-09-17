package com.wafflestudio.seminar.spring2023.user.service

import com.wafflestudio.seminar.spring2023.user.repository.UserEntity
import com.wafflestudio.seminar.spring2023.user.repository.UserRepository
import org.springframework.data.annotation.Id
import org.springframework.stereotype.Service
import java.lang.reflect.Member

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
) : UserService {

    override fun signUp(username: String, password: String, image: String): User {
        if (username.length < 4) throw SignUpBadUsernameException()
        else if (password.length < 4) throw SignUpBadPasswordException()
        else if (userRepository.existsByUsername(username)) throw SignUpUsernameConflictException()
        else {
            userRepository.save(UserEntity(username = username, password = password, image = image))
            return User(username, image)
        }
    }

    override fun signIn(username: String, password: String): User {
        val userEntity = userRepository.findByUsername(username)
        if (userEntity is UserEntity) {
            if (userEntity.password != password) throw SignInInvalidPasswordException()
            else return User(username= userEntity.username, image = userEntity.image)
        } else throw SignInUserNotFoundException()
    }

    override fun authenticate(accessToken: String): User {
        val userEntity = userRepository.findByUsername(accessToken.reversed())
        if (userEntity is UserEntity) return User(username= userEntity.username, image = userEntity.image)
        else throw AuthenticateException()
    }
}
