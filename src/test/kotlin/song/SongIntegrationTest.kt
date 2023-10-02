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
    fun `곡 조회`() {
        mvc.perform(
            MockMvcRequestBuilders.get("/api/v1/songs?keyword=Seven")
        )
            .andExpect(MockMvcResultMatchers.status().`is`(200))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.songs[0].title").value("Seventeen"))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.songs[0].album").value("Seventeen"))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.songs[0].artists[0].name").value("Joyner Lucas"))
    }
    @Test
    fun `앨범 조회`() {
        mvc.perform(
            MockMvcRequestBuilders.get("/api/v1/albums?keyword=Seven")
        )
            .andExpect(MockMvcResultMatchers.status().`is`(200))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.albums[0].title").value("Seventeen"))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.albums[0].artist.name").value("Joyner Lucas"))
    }
}
