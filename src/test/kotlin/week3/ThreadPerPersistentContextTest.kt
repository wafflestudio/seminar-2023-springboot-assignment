package com.wafflestudio.seminar.spring2023.week3

import com.wafflestudio.seminar.spring2023.song.repository.SongEntity
import com.wafflestudio.seminar.spring2023.song.repository.SongRepository
import org.hibernate.LazyInitializationException
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import java.util.concurrent.Executors

@Disabled
@SpringBootTest
class ThreadPerPersistentContextTest @Autowired constructor(
    private val songRepository: SongRepository,
) {
    private val threads = Executors.newFixedThreadPool(1)

    @Transactional
    @Test
    fun `다른 스레드에서 조회한 엔티티를 가지고, 현재 스레드에서 영속성 컨텍스트 관련 기능을 사용할 수 없다`() {
        val songFromCurrentThread = songRepository.findById(1L).get()

        assertDoesNotThrow {
            println(songFromCurrentThread.album.title) // 영속성 컨텍스트를 통해 album을 lazy load
        }

        val songFromTheOtherThread = threads.submit<SongEntity> { songRepository.findById(1L).get() }.get()

        assertThrows<LazyInitializationException> {
            println(songFromTheOtherThread.album.title) // 영속성 컨텍스트를 통해 album을 lazy load하려 하지만 다른 스레드(영속성 컨텍스트)에서 조회한 것이기 때문에 에러 발생
        }
    }
}
