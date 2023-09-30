package com.wafflestudio.seminar.spring2023.song

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SongIntegrationTest @Autowired constructor(
    private val mvc: MockMvc,
) {
    @Test
    fun `있는 노래 제목을 검색했을 때 존재해야 함`() {
        mvc.perform(
            MockMvcRequestBuilders.get("/api/v1/songs")
                .param("keyword", "boy")
                .contentType(MediaType.APPLICATION_JSON)

        )
            .andExpect(status().`is`(200))
            .andExpect(jsonPath("$.songs").exists())

        mvc.perform(
            MockMvcRequestBuilders.get("/api/v1/songs")
                .param("keyword", "seven")
                .contentType(MediaType.APPLICATION_JSON)

        )
            .andExpect(status().`is`(200))
            .andExpect(jsonPath("$.songs").exists())
    }

    @Test
    fun `없는 노래 제목을 검색했을 때 존재하지 않아야 함`() {
        mvc.perform(
            MockMvcRequestBuilders.get("/api/v1/songs")
                .param("keyword", "wow")
                .contentType(MediaType.APPLICATION_JSON)

        )
            .andExpect(status().isNotFound())

    }


}
