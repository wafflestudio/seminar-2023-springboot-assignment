package com.wafflestudio.seminar.spring2023.user.service

import com.wafflestudio.seminar.spring2023.user.repository.UserEntity
import com.wafflestudio.seminar.spring2023.user.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
) : UserService {

    override fun signUp(username: String, password: String, image: String): User {
        //유저의 이름이 4자 미만이면 SignUpBadUsernameException() 발생
        if(username.length < 4) throw SignUpBadUsernameException()
        //유저의 비밀번호가 4자 미만미면 SignUpBadPasswordException() 발생
        if(password.length < 4) throw SignUpBadPasswordException()

        // 가입하는 유저가 중복된 이름인지 확인
        userRepository.findByUsername(username)?.let {
            throw SignUpUsernameConflictException()
        }

        //중복이 아니라면 새로운 유저 생성
        val userEntity = UserEntity(username= username,password = password,image = image)
        val savedUser = userRepository.save(userEntity)

        return User( username= savedUser.username, image = savedUser.image)
    }

    override fun signIn(username: String, password: String): User {
        // findByUsername을 통해 일치하는 회원이 있는지 확인
        val userEntity = userRepository.findByUsername(username)
            ?: throw SignInUserNotFoundException()
        // 비밀번호가 맞는지 확인
        if(userEntity.password != password){
            throw SignInInvalidPasswordException()
        }
        // 모두 일치한다면 유저 반환
        return User(username= userEntity.username, image = userEntity.image)
    }

    override fun authenticate(accessToken: String): User {
        // 토큰을 다시 유저 이름으로 변환
        val username = accessToken.reversed()
        // 유저 네임을 통해 찾고 엘비스 연산자를 통해 null 확인
        val userEntity = userRepository.findByUsername(username)
            ?: throw AuthenticateException()
        // 다 통과한다면 유저 반환
        return User(username = userEntity.username, image = userEntity.image)
    }
}
