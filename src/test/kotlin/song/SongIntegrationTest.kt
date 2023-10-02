package com.wafflestudio.seminar.spring2023.song

import org.hamcrest.Matchers.containsString
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SongIntegrationTest @Autowired constructor(
    private val mvc: MockMvc,
) {
    val keyword = "Seven"

    @Test
    fun `앨범명 검색`() {
        mvc.perform(
            get("/api/v1/albums?keyword=$keyword")
        )
            .andExpect(status().`is`(200))
            .andExpect(jsonPath("$.albums[0].title", containsString("Seven")))
    }

    @Test
    fun `곡명 검색`() {
        mvc.perform(
            get("/api/v1/songs?keyword=$keyword")
        )
            .andExpect(status().`is`(200))
            .andExpect(jsonPath("$.songs[0].title", containsString("Seven")))

    }
}
