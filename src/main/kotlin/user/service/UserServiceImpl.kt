package com.wafflestudio.seminar.spring2023.user.service

import com.wafflestudio.seminar.spring2023.user.repository.UserEntity
import com.wafflestudio.seminar.spring2023.user.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
) : UserService {

    override fun signUp(username: String, password: String, image: String): User {
        //조건에 따른 예외 처리
        //유저 이름과 비밀번호는 4글자 이상
        if(username.length < 4) throw SignUpBadUsernameException()
        if(password.length < 4) throw SignUpBadPasswordException()
        //존재하는 유저 이름으로는 가입 불가
        if(userRepository.findByUsername(username) != null ) throw SignUpUsernameConflictException()

        //예외 사항이 아니라면 받은 유저정보를 데이터베이스에 등록
        val newUser= UserEntity(id=0, username = username, password = password, image = image)
        userRepository.save(newUser)

        //반환값이 존재하는 함수이므로 명시
        return User(username = username, image=image)

    }

    override fun signIn(username: String, password: String): User {
        //조건에 따른 예외처리
        //데이터베이스에 입력되는 유저명이 존재하는지 확인(Elvis 연산자 사용)
        val userEntity = userRepository.findByUsername(username) ?: throw SignInUserNotFoundException()
        //비밀번호가 해당하는 유저명에 맞는지 확인
        if(userEntity.password != password) throw SignInInvalidPasswordException()

        //예외 없는 경우 반환 형식에 맞는 반환값 제공 (image는 따로 입력받지 않았으므로 데이터베이스에서 뽑아서 반환)
        return User(username = username, image = userEntity.image)

    }

    override fun authenticate(accessToken: String): User {
        //조건에 따른 예외처리
        //유저 식별을 위한 인증 토큰이 정확한 지 확인
        //accesstoken을 뒤집어서 검색한다는 아이디어에 맞춘 해법
        val rversedUsername = accessToken.reversed()
        val thisUser : UserEntity = userRepository.findByUsername(rversedUsername) ?: throw AuthenticateException()

        //반환값 처리
        return User(thisUser.username,thisUser.image)
    }
}
