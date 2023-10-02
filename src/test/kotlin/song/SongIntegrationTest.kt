package com.wafflestudio.seminar.spring2023.song

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.transaction.annotation.Transactional

@Transactional
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SongIntegrationTest @Autowired constructor(
    private val mvc: MockMvc,
) {
    @Test
    fun `곡 제목 - 키워드 검색`() {
        mvc.perform(
            MockMvcRequestBuilders.get("/api/v1/songs?keyword=Seven")
        )
            .andExpect(status().isOk())
            .andExpect(jsonPath("\$.songs[0].title").value("Seventeen"))
    }

    @Test
    fun `앨범 제목 - 키워드 검색`() {
        mvc.perform(
            MockMvcRequestBuilders.get("/api/v1/albums?keyword=Seven")
        )
            .andExpect(status().isOk())
            .andExpect(jsonPath("\$.albums[0].title").value("Seventeen"))
    }
}