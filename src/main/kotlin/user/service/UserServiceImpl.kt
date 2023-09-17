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
        //데이터베이스에 있는 유저 데이터를 다 꺼내서 입력된 인증 토큰과 데이터들의 인증 토큰중 맞는게 하나라도 있는지 확인
        //대조 과정에서 user.getAccessToken()함수를 써야 인증 토큰을 꺼낼 수 있으므로, userRepository의 데이터 리스트를 user들의 리스트로 바꿔줄 필요가 있음(.map()사용)
        return userRepository.findAll() //다 꺼내기
                .map{User(it.username,it.image)} //user 형의 리스트로 다 바꿔줌
                .find{ it.getAccessToken() == accessToken} //인증토큰 확인
                ?: throw AuthenticateException()
    }
}
