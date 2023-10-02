package com.wafflestudio.seminar.spring2023.song

import jakarta.transaction.Transactional
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@Transactional
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SongIntegrationTest @Autowired constructor(
    private val mvc: MockMvc,
) {

    @Test
    fun testSearchSongs() {
        val keyword = "Seven" // Replace with the keyword you want to search

        mvc.perform(
            MockMvcRequestBuilders.get("/api/v1/songs")
            .param("keyword", keyword))
            .andExpect(status().isOk)
        // Add more assertions as needed for the response content
    }

    @Test
    fun testSearchAlbums() {
        val keyword = "Seven" // Replace with the keyword you want to search

        mvc.perform(MockMvcRequestBuilders.get("/api/v1/albums")
            .param("keyword", keyword))
            .andExpect(status().isOk)
        // Add more assertions as needed for the response content
    }
}
