package com.wafflestudio.seminar.spring2023.song

import jakarta.transaction.Transactional
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.mock.http.server.reactive.MockServerHttpRequest.post
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@Transactional
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SongIntegrationTest @Autowired constructor(
    private val mvc: MockMvc,
) {
    @Test
    fun `곡 검색 시 200 응답`(){
        mvc.perform(
                get("/api/v1/songs?keyword=Seven")
        ).andExpect(status().`is`(200))
    }
    @Test
    fun `앨범 검색 시 200 응답`(){
        mvc.perform(
                get("/api/v1/albums?keyword=Seven")
        ).andExpect(status().`is`(200))
    }
}
