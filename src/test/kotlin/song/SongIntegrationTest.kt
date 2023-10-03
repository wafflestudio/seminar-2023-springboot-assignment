package com.wafflestudio.seminar.spring2023.song

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
) {
    @Test
    fun `음악 찾기`() {
        mvc.perform(
            get("/api/v1/songs?keyword=Remaster")
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.songs[0].title").value("Black Dog - Remaster"))

    }

    @Test
    fun `앨범 찾기`() {
        mvc.perform(
            get("/api/v1/albums?keyword=Love")
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.albums[0].title").value("Lover"))
    }
}
