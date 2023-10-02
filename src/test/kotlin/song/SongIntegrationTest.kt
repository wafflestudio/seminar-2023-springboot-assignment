package com.wafflestudio.seminar.spring2023.song

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SongIntegrationTest @Autowired constructor(
    private val mvc: MockMvc,
) {
    @Test
    fun `제목에 키워드를 포함한 곡을 검색하면 200 응답과 곡 정보를 내려준다`() {
        // Search with keyword "Don't"
        val result = mvc.perform(
            MockMvcRequestBuilders.get("/api/v1/songs?keyword=Happier")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().`is`(200))
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andReturn().response.contentAsString
        print(result);
    }

    @Test
    fun `제목에 키워드를 포함한 앨범을 검색하면 200 응답과 앨범 정보를 내려준다`() {
        // Search with keyword "Don't"
        val result = mvc.perform(
            MockMvcRequestBuilders.get("/api/v1/albums?keyword=Deluxe")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().`is`(200))
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andReturn().response.contentAsString
        print(result);
    }
}
