package com.wafflestudio.seminar.spring2023.user.service

import com.wafflestudio.seminar.spring2023.user.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
) : UserService {

    override fun signUp(username: String, password: String, image: String): User {
        println("hello")
        TODO("Not yet implemented")
    }

    override fun signIn(username: String, password: String): User {
        TODO("Not yet implemented")
    }

    override fun authenticate(accessToken: String): User {
        TODO("Not yet implemented")
    }
}
