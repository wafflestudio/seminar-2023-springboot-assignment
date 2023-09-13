# seminar-2023-springboot-assignment

2023 스프링 세미나 과제 레포

## week1 사전과제

1. [MySQL 쿼리 기초 숙지](https://cocoon1787.tistory.com/762)

2. ER 다이어그램 숙지
![er.jpg](..%2F..%2FDownloads%2Fer.jpg)

3. H2 데이터베이스 콘솔 접속 (http://localhost:8080/h2-console)
- Driver.class: org.h2.Driver
- JDBC URL: jdbc:h2:mem:testdb
- User Name: sa
- Password: 1234
![스크린샷 2023-09-17 오후 10.29.02.png](..%2F..%2F..%2F..%2Fvar%2Ffolders%2Fjp%2F1fb2qd5n4x3ffjlzn6pcw2t40000gn%2FT%2FTemporaryItems%2FNSIRD_screencaptureui_CPyqNx%2F%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202023-09-17%20%EC%98%A4%ED%9B%84%2010.29.02.png)

4. 첫 쿼리 실행
- `show tables;`
![스크린샷 2023-09-17 오후 10.30.24.png](..%2F..%2F..%2F..%2Fvar%2Ffolders%2Fjp%2F1fb2qd5n4x3ffjlzn6pcw2t40000gn%2FT%2FTemporaryItems%2FNSIRD_screencaptureui_ybjas1%2F%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202023-09-17%20%EC%98%A4%ED%9B%84%2010.30.24.png)

5. 데이터 쿼리 연습
- (ex) 그룹 아이디가 1인 플레이리스트의 제목들 `select title from playlists where group_id = 1;`
- 아이디가 1인 아티스트가 발매한 앨범의 제목들
- 제목에 'Seven'이 포함된 노래의 제목들
- 제목이 'GUTS'인 앨범에 수록된 노래의 제목들
- 제목이 'Spotify 플레이리스트'인 플레이리스트 그룹에 속한 플레이리스트의 제목들
- 아이디가 1인 아티스트가 부른 노래의 제목들

## week1 과제

### 1. 구현해야하는 API

총 6개의 API를 구현해야 합니다. 아래에서 확인할 수 있습니다.

- */src/test/kotlin/playlist/PlaylistApi.http*
- */src/test/kotlin/song/SongApi.http*

### 2. Entity 설계
ER 다이어그램을 바탕으로, JPA 엔티티 클래스, 레포지토리를 작성해야 합니다. (미리 구현되어 있는 AlbumEntity, ArtistEntity 클래스를 수정해야 할 수도 있습니다.) 

### 3. 서비스 테스트
week1에서는 응답의 정확성 뿐만 아니라 효율성도 채점 기준에 포함됩니다.

JPA를 사용하면 쿼리를 직접 작성하지 않아도 되기 때문에 편리하지만, 개발자가 의도치 않은 쿼리가 발생하여 효율성을 크게 해칠 수 있습니다. (대표적으로 N+1 이슈, 일대다 페이징 이슈)

week1 과제에서는 JPQL과 Fetch Join을 통해 서비스에서 사용되는 쿼리 개수를 최대한 줄여야 합니다.

#### 3.1 JPQL, Fetch Join을 사용한 예제

*-/src/main/kotlin/song/repository/ArtistRepository.kt*

```kotlin
interface ArtistRepository : JpaRepository<ArtistEntity, Long> {
    @Query("SELECT a FROM artists a LEFT JOIN FETCH a.albums WHERE a.id = :id")
    fun findByIdWithJoinFetch(id: Long): ArtistEntity?
}
```

#### 3.2 테스트 예제

*-/src/test/kotlin/song/SongServiceTest.kt*


```kotlin
@Test
fun `제목에 키워드를 포함한 곡 검색, 제목 길이가 짧은 순으로 정렬, 쿼리 횟수는 1개로 제한`() {
    val (songs, queryCount) = queryCounter.count { songService.search("Don't") }

    assertThat(songs.map { it.id }).isEqualTo(listOf(829L, 295, 494, 482, 523, 359, 1538, 487))

    assertThat(queryCount).isLessThanOrEqualTo(1)
}
```

### 3. 통합 테스트
week1에서는 통합 테스트 코드를 직접 짜야합니다. 자유롭게 */src/test/kotlin/playlist/PlaylistIntegrationTest.kt*, */src/test/kotlin/song/SongIntegrationTest.kt*를 작성해주시면 됩니다.

*-/src/test/kotlin/playlist/PlaylistIntegrationTest.kt*

```kotlin
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PlaylistIntegrationTest @Autowired constructor(
    private val mvc: MockMvc,
) {
}
```

### 4. 추가 과제
플레이 리스트 그룹, 플레이 리스트는 자주 바뀌는 데이터가 아니기 때문에 캐시 레이어를 두기 좋은 도메인입니다. TTL이 10초인 캐시 구현체를 자유롭게 구현해주세요. 캐싱 관련 라이브러리는 사용할 수 없습니다.

*-/src/main/kotlin/playlist/service/PlaylistServiceCacheImpl.kt*

```kotlin
@Service
class PlaylistServiceCacheImpl(
    private val impl: PlaylistServiceImpl,
) : PlaylistService {

    override fun getGroups(): List<PlaylistGroup> {
        TODO("Not yet implemented")
    }

    override fun get(id: Long): Playlist {
        TODO("Not yet implemented")
    }
}
```
