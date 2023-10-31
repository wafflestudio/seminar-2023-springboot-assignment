package com.wafflestudio.seminar.spring2023.week3

import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistRepository
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.support.TransactionTemplate

@Disabled
@SpringBootTest
class DirtyCheckTest @Autowired constructor(
    private val playlistRepository: PlaylistRepository,
    txManager: PlatformTransactionManager,
) {
    private val txTemplate = TransactionTemplate(txManager)

    @Test
    fun `JPA 변경 감지`() {
        txTemplate.execute { // @Transactional과 같은 역할
            val playlist = playlistRepository.findById(1L).get()

            playlist.viewCnt++
        }
    }
}
