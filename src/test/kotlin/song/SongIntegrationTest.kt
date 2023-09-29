package com.wafflestudio.seminar.spring2023.song

import com.fasterxml.jackson.databind.ObjectMapper
import org.hamcrest.Matchers.contains
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SongIntegrationTest @Autowired constructor(
    private val mvc: MockMvc,
) {
    @Test
    fun`키워드가 들어간 노래 검색 확인`(){
        mvc.perform(
            get("/api/v1/songs")
                .param("keyword", "Don't")
        )
            .andExpect(status().`is`(200))
            .andExpect(jsonPath("$.songs[*].id").isArray())
            .andExpect(jsonPath("$.songs[*].id", contains(829, 295, 494, 482, 523, 359, 1538, 487)))
    }

    @Test
    fun`키워드가 들어간 앨범 검색 확인`(){
        mvc.perform(
            get("/api/v1/albums")
                .param("keyword", "One")
        )
            .andExpect(status().`is`(200))
            .andExpect(jsonPath("$.albums[*].id").isArray())
            .andExpect(jsonPath("$.albums[*].id", contains(34, 59, 108)))
    }
}
