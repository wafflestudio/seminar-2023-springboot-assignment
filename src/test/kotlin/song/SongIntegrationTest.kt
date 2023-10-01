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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SongIntegrationTest @Autowired constructor(
    private val mvc: MockMvc,
    private val mapper: ObjectMapper,
    ) {
    @Test
    fun `곡 이름으로 검색 결과 확인`(){
        mvc.perform(
                get("/api/v1/songs").param("keyword","Don't")
        )
                .andExpect(status().`is`(200))
                .andExpect(jsonPath("songs.[1].title").value("Don't Get Me Wrong"))
        mvc.perform(
                get("/api/v1/songs").param("keyword","????????")
        )
                .andExpect(status().`is`(200))
                .andExpect(jsonPath("songs.length()").value(0))
    }
    @Test
    fun `앨범 이름으로 검색 결과 확인`(){
        mvc.perform(
                get("/api/v1/albums").param("keyword","end")
        )
                .andExpect(status().`is`(200))
                .andExpect(jsonPath("albums.[0].title").value("Old Friend"))
        mvc.perform(
                get("/api/v1/albums").param("keyword","????????")
        )
                .andExpect(status().`is`(200))
                .andExpect(jsonPath("albums.length()").value(0))
    }
}
