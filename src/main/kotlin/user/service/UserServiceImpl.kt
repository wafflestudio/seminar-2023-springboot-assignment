package com.wafflestudio.seminar.spring2023.user.service

import com.wafflestudio.seminar.spring2023.user.repository.UserEntity
import com.wafflestudio.seminar.spring2023.user.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
) : UserService {
    override fun signUp(username: String, password: String, image: String): User {
        //TODO("Not yet implemented")
        if (username.length<4) throw SignUpBadUsernameException()
        if (password.length<4) throw SignUpBadPasswordException()
        val userL = userRepository.findAll()
        userL.forEach{
            if (it.username==username) throw SignUpUsernameConflictException()
        }
        val signUpUserEntity = UserEntity(username = username, password = password, image = image)
        userRepository.save(signUpUserEntity)
        return User(signUpUserEntity.username, signUpUserEntity.image)
    }

    override fun signIn(username: String, password: String): User {
        //TODO("Not yet implemented")
        val userL = userRepository.findAll()
        for (user1 in userL){
            if (user1.username==username) {
                var sU : UserEntity = user1;
                if (password!=sU.password) throw SignInInvalidPasswordException()
                return User(sU.username, sU.image)
            }
        }
        throw SignInUserNotFoundException()
    }

    override fun authenticate(accessToken: String): User {
        val userL = userRepository.findAll()
        userL.forEach{
            if (it.username == accessToken.reversed()) return User(it.username, it.image)
        }
        throw AuthenticateException()
    }
}
