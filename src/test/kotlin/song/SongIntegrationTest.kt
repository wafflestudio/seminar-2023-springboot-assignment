package com.wafflestudio.seminar.spring2023.song

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SongIntegrationTest @Autowired constructor(
    private val mvc: MockMvc,
) {
    @Test
    fun `곡명을 조회한다`() {
        mvc.perform(
            get("/api/v1/songs?keyword=Seven")
        ).andExpect(status().`is`(200))
    }

    @Test
    fun `앨범을 조회한다`() {
        mvc.perform(
            get("/api/v1/albums?keyword=Seven")
        ).andExpect(status().`is`(200))
    }
}
