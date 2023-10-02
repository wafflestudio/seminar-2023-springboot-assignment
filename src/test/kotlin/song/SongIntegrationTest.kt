package com.wafflestudio.seminar.spring2023.song

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SongIntegrationTest @Autowired constructor(
    private val mvc: MockMvc,
    private val mapper: ObjectMapper,
) {
    @Test
    fun `곡의 제목 검색 후 올바른 응답을 내려주는지 확인`() {
        mvc.perform(
            get("/api/v1/songs")
                .param("keyword", "Don't")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().`is`(200))
    }

    @Test
    fun `앨범 검색 후 올바른 응답을 내려주는지 확인`() {
        mvc.perform(
            get("/api/v1/albums")
                .param("keyword", "One")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().`is`(200))
    }



}
