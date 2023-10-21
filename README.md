# seminar-2023-springboot-assignment

2023 스프링 세미나 과제 레포

## week3 사전과제

- [week2](https://github.com/wafflestudio/seminar-2023/blob/main/spring/week2/week2.pdf) 복습 (멀티 스레딩, 스프링 MVC의 스레드 모델, 동기화)
- [데이터베이스 락](https://unluckyjung.github.io/db/2022/03/07/Optimistic-vs-Pessimistic-Lock/) 예습

## week3 과제

### 1. 배치 구현
배치란 대량의 반복적인 데이터 작업을 완료하는 데 사용하는 방법입니다.

운영팀은 플레이리스트를 구성하는데 필요한 대량의 앨범 정보를 주기적으로 업데이트하려 합니다. 

운영자가 주기적으로 앨범 정보가 담긴 json 파일을 업로드 할 것이고, 우리는 이를 처리하기 위한 기능을 개발해야 합니다. 

`AdminController`와 `AdminBatchServiceImpl`을 구현해주세요. 

*-/src/main/kotlin/admin/controller/AdminController.kt*
```kotlin
@RestController
class AdminController {

    @PostMapping("/admin/v1/batch/albums")
    fun insertAlbums(
        @RequestPart("albums.txt") file: MultipartFile,
    ) {
        TODO()
    }
}
```
*-/src/main/kotlin/admin/service/AdminBatchServiceImpl.kt*
```kotlin
@Service
class AdminBatchServiceImpl : AdminBatchService {

  override fun insertAlbums(albumInfos: List<BatchAlbumInfo>) {
    TODO("Not yet implemented")
  }
}
```

데이터가 많으면 배치 작업이 오래 걸려 운영 상의 불편함을 겪을 수 있습니다. 따라서 아래 제약 조건이 지켜져야 합니다.
- 배치 작업은 최대 **500ms** 안에 완료되어야 합니다. (500ms는 꼭 지켜지지 않아도 됩니다. 다만 성능 향상을 위한 로직이 코드에 반영되어 있어야 합니다.)

앨범 A와 이에 수록된 곡 B,C가 있다고 할 때, 곡 C에 대한 Insert 작업이 실패할 수 있습니다. 이 경우, 앨범-곡 정보가 부정확하게 업데이트 될 수 있습니다. 따라서 아래 조건이 지켜져야 합니다.
- 한 앨범과 앨범의 아티스트, 그리고 앨범에 수록된 곡들에 대한 Insert 작업은 모두 **Atomic** 해야합니다. 

### 2. 플레이리스트에 조회 수 기능 도입
기획팀은 플레이리스트에 조회 수 기능을 도입하여, 유저들에게 인기가 많은 플레이리스트를 먼저 노출시키고 싶어 합니다.

플레이리스트 정렬 기준은 기본 정렬, 전체 조회 수 정렬, 최근 1시간 조회 수 정렬이 있습니다. 

조회 수 기능 도입을 위해 `PlaylistViewServiceImpl`을 구현해주세요.

*-/src/main/kotlin/playlist/service/PlaylistViewServiceImpl.kt*
```kotlin
@Service
class PlaylistViewServiceImpl : PlaylistViewService, SortPlaylist {
    
  override fun create(playlistId: Long, userId: Long, at: LocalDateTime): Future<Boolean> {
    return CompletableFuture.completedFuture(false) // FIXME
  }

  override fun invoke(playlists: List<PlaylistBrief>, type: Type, at: LocalDateTime): List<PlaylistBrief> {
    return when (type) {
      Type.DEFAULT -> playlists
      else -> TODO("Not yet implemented")
    }
  }
```

기획팀에서는 어뷰저들이 조회 수를 조작하여 인기 순위를 왜곡하는 것을 원하지 않습니다. 따라서 아래 제약 조건이 지켜져야 합니다.

- 같은 유저-같은 플레이리스트의 조회 수는 **1분에 1개**까지만 허용한다.

기획팀에서는 조회 수 기능 추가로 인해 플레이리스트 조회 응답이 느려지거나 실패하는 **사이드 이펙트**를 원하지 않습니다. 따라서 아래 조건이 지켜져야 합니다.

- 조회 수 업데이트가 오래 걸리더라도, 플레이리스트 조회 API 응답은 이에 영향을 받지 않는다.
- 조회 수 업데이트가 실패하더라도, 플레이리스트 조회 API 응답은 성공적으로 내려가야 한다.

기획팀에서는 특정 플레이리스트에 대한 조회 수가 급증하여 동시 요청이 들어오더라도, 조회 수 **증가량이 모두 반영**되기를 원합니다. 따라서 아래 조건이 지켜져야 합니다.

- 플레이리스트 조회 요청이 동시에 여러 번 들어오더라도, 조회 수 증가량은 실제 들어온 조회 수와 일치해야 한다. (조회 수 업데이트 작업이 실패하지 않는다는 전제 하에)

### 3. 서버 모니터링
개발팀 회의에서 서버 모니터링이 불가능한 점에 대한 문제 제기가 있었습니다. 이에, 서버 모니터링을 위한 로깅을 추가하기로 결정하였습니다.

서버 모니터링을 위해 LogInterceptor, LogRequest, AlertSlowResponse를 구현해주세요.

*-/src/main/kotlin/_web/log/LogInterceptor.kt*
```kotlin
@Component
class LogInterceptor(
    private val logRequest: LogRequest,
    private val logSlowResponse: AlertSlowResponse,
) : HandlerInterceptor {

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        // FIXME
        return super.preHandle(request, response, handler)
    }

    override fun afterCompletion(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
        ex: Exception?,
    ) {
        // FIXME
        super.afterCompletion(request, response, handler, ex)
    }
}
```

*-/src/main/kotlin/_web/log/LogRequest.kt*
```kotlin
@Component
class LogRequestImpl : LogRequest {
    private val logger = LoggerFactory.getLogger(javaClass)

    override operator fun invoke(request: Request) {
        TODO()
    }
}
```

*-/src/main/kotlin/_web/log/AlertSlowResponse.kt*
```kotlin
@Component
class AlertSlowResponseImpl : AlertSlowResponse {
    private val logger = LoggerFactory.getLogger(javaClass)

    override operator fun invoke(slowResponse: SlowResponse): Future<Boolean> {
        TODO()
    }
}
```

개발팀에서는 유저로부터 들어오는 모든 요청을 로깅하려 합니다. 뿐만 아니라, 응답 속도가 임계치(**3초**)를 넘을 경우 이를 바로 인지하고 싶어합니다.

- 들어오는 모든 요청을 "[API-REQUEST] GET /api/v1/playlist-groups" 꼴로 로깅
- 3초 이상 걸린 응답을 "[API-RESPONSE] GET /api/v1/playlists/7, took 3132ms, PFCJeong" 꼴로 로깅 (각각 요청 method, path, 걸린 시간, 본인의 깃허브 아이디)
- 3초 이상 걸린 응답을 "[API-RESPONSE] GET /api/v1/playlists/7, took 3132ms, PFCJeong" 꼴로 슬랙 채널에 전달


### 4. 커스텀 플레이리스트 기능 내부 테스트
기획팀은 유저가 자신만의 플레이리스트를 만들어 곡을 추가할 수 있는 새로운 기능을 도입하고 싶어 합니다. 아직 릴리즈 일정은 잡혀있지 않기 때문에 내부 테스트를 위한 개발만 진행하면 됩니다.

CustomPlaylistServiceImpl를 구현해주세요.

*-/src/main/kotlin/custom-playlist/service/CustomPlaylistServiceImpl.kt*
```kotlin
@Service
class CustomPlaylistServiceImpl : CustomPlaylistService {

    override fun get(userId: Long, customPlaylistId: Long): CustomPlaylist {
        TODO("Not yet implemented")
    }

    override fun gets(userId: Long): List<CustomPlaylistBrief> {
        TODO("Not yet implemented")
    }

    override fun create(userId: Long): CustomPlaylistBrief {
        TODO("Not yet implemented")
    }

    override fun patch(userId: Long, customPlaylistId: Long, title: String): CustomPlaylistBrief {
        TODO("Not yet implemented")
    }

    override fun addSong(userId: Long, customPlaylistId: Long, songId: Long): CustomPlaylistBrief {
        TODO("Not yet implemented")
    }
}
```
아직 최종 기획이 나와 있지 않기 때문에 개발적인 요구사항만 만족시키면 됩니다. 개발 요구사항은 CustomPlaylistServiceImpl에서 확인할 수 있습니다.

## week3 과제 주의 사항

- 기본적으로 모든 테스트가 성공해야 합니다. `./gradlew test`
- 요구 사항을 테스트 코드로 모두 검증하기 어려운 측면이 있습니다. (테스트 코드를 보강해야 하는데, 시간이 될지 모르겠어요)
- 따라서 테스트가 성공하더라도, 모든 요구 사항에 대한 로직이 코드에 반영되어 있지 않으면 과제는 실패 처리하려 합니다.
- README.md와 각 클래스에 명시된 과제 스펙을 모두 꼼꼼히 체크해주세요.
- 이번 과제는 private 레포지토리에서 진행합니다. [private_fork](https://gist.github.com/0xjac/85097472043b697ab57ba1b1c7530274)에 나온대로 private 레포지토리를 만든 후, Collaborator로 @PFCJeong, @davin111을 추가해주세요. 과제는 PR로 올려주시고 저희에게 노티 주시면 됩니다.
- 이번 과제는 미리 시작하여 피드백을 받으면서 진행하는 편이 좋겠습니다.
