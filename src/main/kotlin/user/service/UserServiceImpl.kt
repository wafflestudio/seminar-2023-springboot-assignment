package com.wafflestudio.seminar.spring2023.user.service

import org.springframework.stereotype.Service

@Service
class UserServiceImpl : UserService {

    override fun signUp(username: String, password: String, image: String): User {
        TODO("Not yet implemented")
    }

    override fun signIn(username: String, password: String): User {
        TODO("Not yet implemented")
    }

    override fun authenticate(accessToken: String): User {
        TODO("Not yet implemented")
    }
}
