package com.wafflestudio.seminar.spring2023.song

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SongIntegrationTest @Autowired constructor(
    private val mvc: MockMvc,
    private val objectMapper: ObjectMapper,
) {
    @Test
    fun `키워드를 이용한 노래 검색`() {
        mvc.perform(
            get("/api/v1/songs")
                .param("keyword", "vampire")
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.songs.length()").value(1))
            .andExpect(jsonPath("$.songs[0].title").value("vampire"))
            .andExpect(jsonPath("$.songs[0].artists[0].name").value("Olivia Rodrigo"))
            .andExpect(jsonPath("$.songs[0].album").value("GUTS"))
    }

    @Test
    fun `노래 검색 중 해당하는 값이 없으면 빈 리스트를 반환`(){
        mvc.perform(
            get("/api/v1/songs")
                .param("keyword", "vampire123")
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.songs.length()").value(0))
    }

    @Test
    fun `키워드를 이용한 앨범 검색`() {
        mvc.perform(
            get("/api/v1/albums")
                .param("keyword", "GUTS")
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.albums.length()").value(1))
            .andExpect(jsonPath("$.albums[0].title").value("GUTS"))
            .andExpect(jsonPath("$.albums[0].artist.name").value("Olivia Rodrigo"))
    }
}
