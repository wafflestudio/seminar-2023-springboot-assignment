package com.wafflestudio.seminar.spring2023.song

import com.wafflestudio.seminar.spring2023.QueryCounter
import com.wafflestudio.seminar.spring2023.song.repository.AlbumRepository
import com.wafflestudio.seminar.spring2023.song.repository.ArtistRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@Transactional
@SpringBootTest
class AlbumRepositoryTest @Autowired constructor(
    private val albumRepository: AlbumRepository,
    private val artistRepository: ArtistRepository,
    private val queryCounter: QueryCounter,
) {

    @Test
    fun `엔티티 캐싱`() {
        val (_, queryCount) = queryCounter.count {
            // Hibernate: select a1_0.id,a1_0.name from artists a1_0 where a1_0.id=?
            val artist = artistRepository.findById(1L).get()

            println(artist.name)

            // album Id 1이 캐싱되어 있기 때문에 추가 쿼리 X
            val artist2 = artistRepository.findById(1L).get()

            println(artist2.name)
        }

        assertThat(queryCount).isEqualTo(1)
    }

    @Test
    fun `OneToMany 테이블 탐색`() {
        val (_, queryCount) = queryCounter.count {
            // Hibernate: select a1_0.id,a1_0.name from artists a1_0 where a1_0.id=?
            val artist = artistRepository.findById(1L).get()

            println("name: ${artist.name}")

            // Hibernate: select a1_0.artist_id,a1_0.id,a1_0.image,a1_0.title from albums a1_0 where a1_0.artist_id=?
            println("albums: ${artist.albums.map { it.title }}")
        }

        assertThat(queryCount).isEqualTo(2)
    }

    @Test
    fun `ManyToOne 테이블 탐색`() {
        val (_, queryCount) = queryCounter.count {
            // Hibernate: select a1_0.id,a2_0.id,a2_0.name,a1_0.image,a1_0.title from albums a1_0 left join artists a2_0 on a2_0.id=a1_0.artist_id where a1_0.id=?
            val album = albumRepository.findById(1L).get()

            println("title: ${album.title}")

            // FetchType.EAGER로 인해 조인하여 쿼리하기 때문에 추가 쿼리 X
            println("artist: ${album.artist.name}")
        }

        assertThat(queryCount).isEqualTo(1)
    }

    @Test
    fun `OneToMany JOIN FETCH`() {
        val (_, queryCount) = queryCounter.count {
            // Hibernate: select a1_0.id,a2_0.artist_id,a2_0.id,a2_0.image,a2_0.title,a1_0.name from artists a1_0 left join albums a2_0 on a1_0.id=a2_0.artist_id where a1_0.id=?
            // JOIN FETCH로 단 한번의 쿼리만 발생
            val artist = artistRepository.findByIdWithJoinFetch(1L)!!

            println("name: ${artist.name}")

            println("albums: ${artist.albums.map { it.title }}")
        }

        assertThat(queryCount).isEqualTo(1)
    }
}