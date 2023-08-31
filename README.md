# seminar-2023-springboot-assignment

2023 스프링 세미나 과제 레포

## Requirements
1. [자바 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) 설치 후 아래와 같이 버전 체크
```commandline
➜  ~ java --version
java 17.0.6 2023-01-17 LTS
Java(TM) SE Runtime Environment (build 17.0.6+9-LTS-190)
Java HotSpot(TM) 64-Bit Server VM (build 17.0.6+9-LTS-190, mixed mode, sharing)
```
2. [Intellij IDEA 2023.2.1](https://www.jetbrains.com/ko-kr/idea/download) 설치

## week0 사전 과제
1. 깃 클론 후 인텔리제이에서 실행
```commandline
git clone https://github.com/wafflestudio/seminar-2023-springboot-assignment
```
2. master 브랜치에서 서버 실행(Run 'SeminarApplication')<img width="1509" alt="스크린샷 2023-08-31 오후 1 37 40" src="https://github.com/wafflestudio/seminar-2023-springboot-assignment/assets/76547957/5805c4cb-b08c-4356-bc95-cb1e502e510a">
3. 브라우저를 킨 후 http://localhost:8080 접속<img width="1785" alt="스크린샷 2023-08-31 오후 1 39 44" src="https://github.com/wafflestudio/seminar-2023-springboot-assignment/assets/76547957/648d7b52-d360-470b-872e-b252424ccf0d">
4. 치트 시트 활용하여 KotlinTest.kt 풀기 <img width="1150" alt="스크린샷 2023-08-31 오후 1 45 09" src="https://github.com/wafflestudio/seminar-2023-springboot-assignment/assets/76547957/6d792f98-08f6-4bb9-8751-66e19108b277">


## week0 과제

### 1. 구현해야하는 API

로그인을 위한 3개의 api를 구현해야 합니다. api 명세는 아래에서 확인할 수 있습니다.

*-/src/test/kotlin/user/UserApi.http*

```agsl
POST http://localhost:8080/api/v1/signup
Content-Type: application/json

{
  "username": "wafflestudio",
  "password": "spring",
  "image": "https://wafflestudio.com/images/icon_intro.svg"
}

...
```

### 2. 도메인 명세

회원가입, 로그인, 프로필 조회 기능에는 여러가지 조건이 존재합니다. 예를 들어, 회원가입 시 닉네임과 비밀번호는 최소 4자 이상이어야 합니다. 도메인 명세는 아래 테스트 파일에서 확인할 수 있습니다.

*-/src/test/kotlin/user/UserServiceTest.kt*

```agsl
@Test
fun `유저 이름과 비밀번호는 4글자 이상이어야 한다`() {
    assertThrows<SignUpBadUsernameException> {
        ...
    }   

    ...
}
```

UserService 인터페이스를 상속 받은 **UserServiceMockImpl**을 구현하여 UserServiceTest가 성공하도록 해야합니다.

*-/src/main/kotlin/user/service/UserServiceMockImpl.kt*

```agsl
class UserServiceMockImpl(
    private val userRepository: UserRepository,
) : UserService {
    override fun signUp(username: String, password: String, image: String): User {
        TODO()
    }
    
    ...
}
```

### 3. API 명세

회원가입, 로그인, 프로필 조회 API는 유저 요청에 따라 알맞은 http 응답을 내려줘야 합니다. 예를 들어, 회원가입 시 닉네임과 비밀번호가 최소 4자 이상이 아니면 400 Bad Request 응답을 내려줘야
합니다. API 명세는 아래 테스트 파일에서 확인 가능합니다.

*-/src/test/kotlin/user/UserIntegrationTest.kt*

```agsl
@Test
fun `회원가입시에 유저 이름 혹은 비밀번호가 정해진 규칙에 맞지 않는 경우 400 응답을 내려준다`() {
    mvc.perform(
        post("/api/v1/signup")
        ...
    )
    
    ...
}
```

현재 UserController가 아직 구현이 되지 않은 TODO() 상태입니다. **UserController**를 구현하여 UserIntegrationTest가 성공하도록 해야합니다.

*-/src/main/kotlin/user/controller/UserController.kt*

```agsl
@RestController
class UserController(
    private val userService: UserService,
) {
    @PostMapping("/api/v1/signup")
    fun signup(
        @RequestBody request: SignUpRequest,
    ): ResponseEntity<Unit> {
        TODO()
    }
    
    ...
}
```
