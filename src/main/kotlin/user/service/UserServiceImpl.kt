package com.wafflestudio.seminar.spring2023.user.service

import com.wafflestudio.seminar.spring2023.user.repository.UserEntity
import com.wafflestudio.seminar.spring2023.user.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserServiceImpl : UserService {

    @Autowired
    lateinit var userRepository: UserRepository

    override fun signUp(username: String, password: String, image: String): User {

        if (username.length < 4){
            throw SignUpBadUsernameException()
        }

        if(password.length < 4){
            throw SignUpBadPasswordException()
        }

        val existingUser = userRepository.findByUsername(username)
        if (existingUser != null) {
            throw SignUpUsernameConflictException()
        }

        val userEntity = UserEntity(userRepository.count(), username = username, password = password, image = image)
        val savedUser = userRepository.save(userEntity)


        val user = User(savedUser.username, savedUser.image)

        return user

    }

    override fun signIn(username: String, password: String): User {

        val userEntity = userRepository.findByUsername(username)

        if (userEntity == null){
            throw SignInUserNotFoundException()
        }
        if(userEntity.password != password) {
            throw SignInInvalidPasswordException()
        }

        val user = User(userEntity.username, userEntity.image)

        return user
    }

    override fun authenticate(accessToken: String): User {

        val userEntity = userRepository.findByUsername(accessToken.reversed()) // accessToken을 역으로 변환하여 사용자 검색

        if (userEntity == null) {
            throw AuthenticateException()
        }

        val user = User(userEntity.username, userEntity.image)

        return user

    }
}
