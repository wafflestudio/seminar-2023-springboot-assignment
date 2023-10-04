package com.wafflestudio.seminar.spring2023.week2

import com.wafflestudio.seminar.spring2023.QueryCounter
import com.wafflestudio.seminar.spring2023.QueryCounter.Result
import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistRepository
import com.wafflestudio.seminar.spring2023.song.repository.AlbumRepository
import org.assertj.core.api.Assertions.assertThat
import org.hibernate.LazyInitializationException
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import java.util.concurrent.Executors

@SpringBootTest
class PersistenceContextTest @Autowired constructor(
    private val albumRepository: AlbumRepository,
    private val playlistRepository: PlaylistRepository,
    private val queryCounter: QueryCounter,
) {
    private val threadPool = Executors.newFixedThreadPool(4)

    @Test
    fun `영속성 컨텍스트 없이 지연로딩`() {
        val album = albumRepository.findById(1L).get()

        assertThrows<LazyInitializationException> {
            println(album.artist.name)
        }

        val (_, queryCount) = queryCounter.count {
            albumRepository.findById(1L).get()
        }

        // 영속성 컨텍스트가 없기 때문에 캐싱 되지 않음.
        assertThat(queryCount).isEqualTo(1)
    }

    @Transactional // 기본적으로 Transactional이 영속성 컨텍스트 생존 범위
    @Test
    fun `영속성 컨텍스트 안에서 지연로딩`() {
        val album = albumRepository.findById(1L).get()

        assertDoesNotThrow {
            println(album.artist.name)
        }

        val (_, queryCount) = queryCounter.count {
            albumRepository.findById(1L).get()
        }

        // 영속성 컨텍스트가 있기 때문에 캐싱됨.
        assertThat(queryCount).isEqualTo(0)
    }

    @Transactional // 기본적으로 Transactional이 영속성 컨텍스트 생존 범위, 그러나 스레드 간에 영속성 컨텍스트를 공유하지 않는다.
    @Test
    fun `스레드 단위의 엔티티 캐싱`() {
        // 첫 번째 쿼리: DB 조회 후 영속성 컨텍스트에 캐시
        val (threadName, queryCount) = queryCounter.count {
            playlistRepository.findById(1L).get()

            Thread.currentThread().name
        }

        println("First query executed by Thread($threadName)")

        assertThat(queryCount).isEqualTo(1)

        // 두 번째 쿼리: 첫 번째 쿼리와 같은 스레드에서 발생. 캐싱된 데이터 조회
        val (threadName2, queryCount2) = queryCounter.count {
            playlistRepository.findById(1L).get()

            Thread.currentThread().name
        }

        println("Second query executed by Thread($threadName2)")

        assertThat(queryCount2).isEqualTo(0)

        // 세 번째 쿼리: 이전 쿼리들과 다른 스레드에서 발생. 즉, 다른 영속성 컨텍스트를 갖고 있기 때문에 DB 조회.
        val taskByOtherThread = threadPool.submit<Result<String>> {
            queryCounter.count {
                playlistRepository.findById(1L).get()

                Thread.currentThread().name
            }
        }

        val (threadName3, queryCount3) = taskByOtherThread.get()

        println("Third query executed by Thread($threadName3)")

        assertThat(queryCount3).isEqualTo(1)
    }
}
