package util

import com.wafflestudio.seminar.spring2023.playlist.service.Playlist
import com.wafflestudio.seminar.spring2023.util.CacheManager
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class CacheManagerTest {

    private val ttl = 10L
    private val cm = CacheManager<Long, Playlist>(ttl)

    @Test
    fun `캐시 저장한 것 가져오기`() {
        cm.put(1L, pl1)
        assert(cm.isCacheMiss(0L))
        assert(!cm.isCacheMiss(1L))

        val playlist1 = cm.get(1L)
        assertEquals(playlist1, pl1)

        Thread.sleep(ttl * 1000)
        // ttl초 지난 이후에는 cache miss 처리되어야 함
        assert(cm.isCacheMiss(1L))
    }
    
}

private val pl1 = Playlist(
    id = 1L,
    title = "제목",
    subtitle = "부제목",
    image = "image.jpg",
    songs = ArrayList()
    )
