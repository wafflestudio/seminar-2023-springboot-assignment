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
    fun `곡 조회`(){
        mvc.perform(
            MockMvcRequestBuilders.get("/api/v1/songs?keyword=Seven")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().`is`(200))
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.content().json("""
                {"songs":[
                          {"id":176,"title":"Seventeen","artists":[{"id":68,"name":"Joyner Lucas"}],"album":"Seventeen","image":"https://i.scdn.co/image/ab67616d00001e0286fd9893388c7e30b385567e","duration":"205"},
                          {"id":33,"title":"Seven (feat. Latto) (Clean Ver.)","artists":[{"id":8,"name":"정국"},{"id":9,"name":"Latto"}],"album":"Seven (feat. Latto)","image":"https://i.scdn.co/image/ab67616d00001e02bf5cce5a0e1ed03a626bdd74","duration":"184"},
                          {"id":35,"title":"Seven (feat. Latto) (Instrumental)","artists":[{"id":8,"name":"정국"},{"id":9,"name":"Latto"}],"album":"Seven (feat. Latto)","image":"https://i.scdn.co/image/ab67616d00001e02bf5cce5a0e1ed03a626bdd74","duration":"184"},
                          {"id":34,"title":"Seven (feat. Latto) (Explicit Ver.)","artists":[{"id":8,"name":"정국"},{"id":9,"name":"Latto"}],"album":"Seven (feat. Latto)","image":"https://i.scdn.co/image/ab67616d00001e02bf5cce5a0e1ed03a626bdd74","duration":"184"},
                          {"id":1754,"title":"Vaughan Williams / Orch. Jacob: English Folk Song Suite: I. March. Seventeen Come Sunday","artists":[{"id":458,"name":"Ralph Vaughan Williams"},{"id":414,"name":"London Symphony Orchestra"},{"id":459,"name":"Sir Adrian Boult"}],"album":"The Lark Ascending collection","image":"https://i.scdn.co/image/ab67616d00001e025e7ccf1bbabe68b4264be778","duration":"176"}
                ]}
            """))
    }

    @Test
    fun `앨범 조회`(){
        mvc.perform(
            MockMvcRequestBuilders.get("/api/v1/albums?keyword=Seven")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().`is`(200))
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.content().json("""
                {"albums":[
                          {"id":29,"title":"Seventeen","image":"https://i.scdn.co/image/ab67616d00001e0286fd9893388c7e30b385567e","artist":{"id":68,"name":"Joyner Lucas"}},
                          {"id":5,"title":"Seven (feat. Latto)","image":"https://i.scdn.co/image/ab67616d00001e02bf5cce5a0e1ed03a626bdd74","artist":{"id":8,"name":"정국"}}
                ]}
            """))
    }
}
