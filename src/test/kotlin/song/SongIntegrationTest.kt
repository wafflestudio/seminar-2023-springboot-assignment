package com.wafflestudio.seminar.spring2023.song

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SongIntegrationTest @Autowired constructor(
    private val mvc: MockMvc,
) {
    @Test
    fun `곡 조회`() {
        mvc.perform(
                MockMvcRequestBuilders.get("/api/v1/songs").param("keyword","Don't")
        )
                .andExpect(MockMvcResultMatchers.status().`is`(200))
    }

    @Test
    fun `앨범 조회`() {
        mvc.perform(
                MockMvcRequestBuilders.get("/api/v1/albums").param("keyword","One")
        )
                .andExpect(MockMvcResultMatchers.status().`is`(200))
    }
}
