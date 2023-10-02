package com.wafflestudio.seminar.spring2023.song

import com.wafflestudio.seminar.spring2023.QueryCounter
import com.wafflestudio.seminar.spring2023.song.service.SongService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@Transactional
@SpringBootTest
class SongServiceTest @Autowired constructor(
    private val songService: SongService,
    private val queryCounter: QueryCounter,
) {
    @Test
    fun `제목에 키워드를 포함한 곡 검색, 제목 길이가 짧은 순으로 정렬`() {
        val songs = songService.search("Don't")

        assertThat(songs.map { it.id }).isEqualTo(listOf(829L, 295, 494, 482, 523, 359, 1538, 487))
    }

    @Test
    fun `제목에 키워드를 포함한 곡 검색, 제목 길이가 짧은 순으로 정렬, 쿼리 횟수는 1개로 제한`() {
        val (songs, queryCount) = queryCounter.count { songService.search("Don't") }

        assertThat(songs.map { it.id }).isEqualTo(listOf(829L, 295, 494, 482, 523, 359, 1538, 487))

        assertThat(queryCount).isLessThanOrEqualTo(1)
    }

    @Test
    fun `제목에 키워드를 포함한 앨범 검색, 제목 길이가 짧은 순으로 정렬`() {
        val albums = songService.searchAlbum("One")

        assertThat(albums.map { it.id }).isEqualTo(listOf(34L, 59, 108))
    }

    @Test
    fun `제목에 키워드를 포함한 앨범 검색, 제목 길이가 짧은 순으로 정렬, 쿼리 횟수는 1개로 제한`() {
        val (albums, queryCount) = queryCounter.count { songService.searchAlbum("One") }

        assertThat(albums.map { it.id }).isEqualTo(listOf(34L, 59, 108))

        assertThat(queryCount).isLessThanOrEqualTo(1)
    }
}