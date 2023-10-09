package com.wafflestudio.seminar.spring2023.song

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.wafflestudio.seminar.spring2023.song.controller.SearchAlbumResponse
import com.wafflestudio.seminar.spring2023.song.controller.SearchSongResponse
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SongIntegrationTest @Autowired constructor(
    private val mvc: MockMvc,
) {
    private val mapper = jacksonObjectMapper()

    @Test
    fun `곡 검색`() {
        val response = mvc.get("/api/v1/songs?keyword=Don't")
            .andExpect {
                status { isOk() }
            }
            .andReturn()
            .response

        val songs = mapper.readValue(response.contentAsString, SearchSongResponse::class.java).songs

        assertThat(songs.map { it.id }).isEqualTo(listOf(829L, 295, 494, 482, 523, 359, 1538, 487))
    }

    @Test
    fun `앨범 검색`() {
        val response = mvc.get("/api/v1/albums?keyword=One")
            .andExpect {
                status { isOk() }
            }
            .andReturn()
            .response

        val albums = mapper.readValue(response.contentAsString, SearchAlbumResponse::class.java).albums

        assertThat(albums.map { it.id }).isEqualTo(listOf(34L, 59, 108))
    }
}
