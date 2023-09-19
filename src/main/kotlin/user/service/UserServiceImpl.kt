package com.wafflestudio.seminar.spring2023.user.service

import com.wafflestudio.seminar.spring2023.user.repository.UserEntity
import com.wafflestudio.seminar.spring2023.user.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private var id : Long = 0L
) : UserService {

    override fun signUp(username: String, password: String, image: String): User {
<<<<<<< HEAD
        //TODO("Not yet implemented")
        if(username.length < 4) throw SignUpBadUsernameException()
        if(password.length < 4) throw SignUpBadPasswordException()
        if(userRepository.findByUsername(username) != null) throw SignUpUsernameConflictException()

        userRepository.save(UserEntity(id++, username, password, image))
        return User(username,image)

    }

    override fun signIn(username: String, password: String): User {
        //TODO("Not yet implemented")
        var userEntity:UserEntity? = userRepository.findByUsername(username)
        if(userEntity == null) throw SignInUserNotFoundException()
        if(userEntity.password.equals(password))
            return User(userEntity.username,userEntity.image)
        else
            throw SignInInvalidPasswordException()

    }

    override fun authenticate(accessToken: String): User {
        //TODO("Not yet implemented")
        val userEntity: UserEntity = userRepository.findByUsername(accessToken.reversed()) ?: throw AuthenticateException()
        return User(userEntity.username,userEntity.image)
=======
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
>>>>>>> 6f4df81c3a062e525f0ffbd2e4c8d81cb3ec020e
    }
}

fun User(entity: UserEntity) = User(
    id = entity.id,
    username = entity.username,
    image = entity.image,
)
