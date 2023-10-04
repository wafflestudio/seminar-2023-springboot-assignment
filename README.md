# seminar-2023-springboot-assignment

2023 스프링 세미나 과제 레포

## week2 사전과제

[Week2 미리보기](https://github.com/wafflestudio/seminar-2023-springboot-assignment/commit/f420cff8e268180b66a3d69dd9d78b28a2f683d0)

### 1. PersistenceContextTest

*-/src/test/kotlin/week2/PersistenceContextTest.kt*

- 영속성 컨텍스트
    - 지연 로딩
    - 엔티티 캐싱
    - 스레드간 공유 X
```kotlin
@Test
fun `영속성 컨텍스트 없이 지연로딩`() {}

@Test
fun `영속성 컨텍스트 안에서 지연로딩`() {}

@Test
fun `스레드 단위의 엔티티 캐싱`() {}
```

### 2. Synchronization

*-/src/test/kotlin/week2/SynchronizationTest.kt*
```kotlin
@Test
fun `동기화 없이 좋아요 따닥 생성`() {}

@Test
fun `동기화 사용하여 좋아요 따닥 생성`() {}
```

*-/src/main/kotlin/playlist/service/PlaylistLikeServiceImpl.kt*
```kotlin
@Service
class PlaylistLikeServiceImpl(
    private val playlistLikeRepository: PlaylistLikeRepository,
    private val playlistRepository: PlaylistRepository,
) : PlaylistLikeService {

    @Synchronized
    override fun createSynchronized(playlistId: Long, userId: Long) {
        create(playlistId, userId)
    }
}
```

### 3. Configuration, Properties, Bean 생성

*-src/main/kotlin/CacheConfig.kt*

```kotlin
@EnableConfigurationProperties(CacheProperties::class)
@Configuration
class CacheConfig(
    private val cacheProperties: CacheProperties,
) {

    @Bean
    fun cache(): Caffeine<Any, Any> {
        return Caffeine.newBuilder()
            .maximumSize(cacheProperties.size)
            .expireAfterWrite(cacheProperties.ttl)
    }
}

@ConfigurationProperties("cache")
data class CacheProperties(
    val ttl: Duration,
    val size: Long,
)
```
*-src/main/resources/application.yaml*
```yaml
cache:
  ttl: 10s
  size: 100
```
