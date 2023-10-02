package com.wafflestudio.seminar.spring2023.song

import jakarta.transaction.Transactional
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
class SongIntegrationTest @Autowired constructor(
    private val mvc: MockMvc,
) {
    @Test
    fun `제목에 키워드를 포함하는 곡 검색`() {
        mvc.perform(
            get("/api/v1/songs")
                .param("keyword", "Shape")
        )
            .andExpect(status().`is`(200))
            .andExpect(jsonPath("$.songs.length()").value(2))
            .andExpect(jsonPath("$.songs[0].title").value("Shapes"))
            .andExpect(jsonPath("$.songs[1].title").value("Shape of You"))
    }

    @Test
    fun `제목에 키워드를 포함하는 앨범 검색`() {
        mvc.perform(
                get("/api/v1/albums")
                    .param("keyword", "GUT")
        )
            .andExpect(status().`is`(200))
            .andExpect(jsonPath("$.albums.length()").value(1))
            .andExpect(jsonPath("$.albums[0].title").value("GUTS"))
    }
}
