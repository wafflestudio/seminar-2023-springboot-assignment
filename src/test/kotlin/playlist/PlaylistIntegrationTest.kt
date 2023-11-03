package com.wafflestudio.seminar.spring2023.playlist

<<<<<<< HEAD
import com.fasterxml.jackson.databind.ObjectMapper
=======
>>>>>>> 12f7e172d693b44192e792143ceb21d43e0204a1
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
<<<<<<< HEAD
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
=======
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.transaction.annotation.Transactional
>>>>>>> 12f7e172d693b44192e792143ceb21d43e0204a1

@Transactional
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PlaylistIntegrationTest @Autowired constructor(
    private val mvc: MockMvc,
    private val mapper: ObjectMapper,
) {

    @Test
<<<<<<< HEAD
    fun `로그인하지 않은 상태로 플레이리스트 그룹 조회`(){

        mvc.perform(
            MockMvcRequestBuilders.get("/api/v1/playlist-groups")
        )
            .andExpect(MockMvcResultMatchers.status().`is`(200))
    }


    @Test
    fun `플레이리스트가 존재하지 않는 경우 402 응답을 내려준다`() {
        mvc.perform(
            MockMvcRequestBuilders.get("/api/v1/playlists/{id}",444000222L)
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().`is`(402))

        mvc.perform(
            MockMvcRequestBuilders.post("/api/v1/signup")
                .content(
                    mapper.writeValueAsString(
                        mapOf(
                            "username" to "test-${javaClass.name}-3",
                            "password" to "correct",
                            "image" to "https://wafflestudio.com/images/icon_intro.svg"
                        )
                    )
                )
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().`is`(200))

        mvc.perform(
            MockMvcRequestBuilders.post("/api/v1/signin")
                .content(
                    mapper.writeValueAsString(
                        mapOf(
                            "username" to "test-${javaClass.name}-3",
                            "password" to "correct",
                        )
                    )
                )
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().`is`(200))

        mvc.perform(
            MockMvcRequestBuilders.post("/api/v1/playlists/{id}/likes",444000222L)
                .header("Authorization", "Bearer ${"test-${javaClass.name}-3".reversed()}")
        )
            .andExpect(MockMvcResultMatchers.status().`is`(402))
    }

    @Test
    fun `플레이리스트 좋아요 관련 테스트`(){
        mvc.perform(
            MockMvcRequestBuilders.post("/api/v1/signup")
                .content(
                    mapper.writeValueAsString(
                        mapOf(
                            "username" to "test-${javaClass.name}",
                            "password" to "correct",
                            "image" to "https://wafflestudio.com/images/icon_intro.svg"
                        )
                    )
                )
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().`is`(200))

        mvc.perform(
            MockMvcRequestBuilders.post("/api/v1/signin")
                .content(
                    mapper.writeValueAsString(
                        mapOf(
                            "username" to "test-${javaClass.name}",
                            "password" to "correct",
                        )
                    )
                )
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().`is`(200))

        mvc.perform(
            MockMvcRequestBuilders.delete("/api/v1/playlists/{id}/likes",1L)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer ${"test-${javaClass.name}".reversed()}")
        )
            .andExpect(MockMvcResultMatchers.status().`is`(403))


        mvc.perform(
            MockMvcRequestBuilders.post("/api/v1/playlists/{id}/likes",1L)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer ${"test-${javaClass.name}".reversed()}")
        )
            .andExpect(MockMvcResultMatchers.status().`is`(200))

        mvc.perform(
            MockMvcRequestBuilders.post("/api/v1/playlists/{id}/likes",1L)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer ${"test-${javaClass.name}".reversed()}")
        )
            .andExpect(MockMvcResultMatchers.status().`is`(403))

        mvc.perform(
            MockMvcRequestBuilders.delete("/api/v1/playlists/{id}/likes",1L)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer ${"test-${javaClass.name}".reversed()}")
        )
            .andExpect(MockMvcResultMatchers.status().`is`(200))
    }

=======
    fun `공개된 플레이리스트 그룹을 조회`() {
        mvc.perform(
            MockMvcRequestBuilders.get("/api/v1/playlist-groups")
        )
            .andExpect(status().isOk())
            .andExpectAll(
                jsonPath("\$.groups[0].id").value("1"),
                jsonPath("\$.groups[1].id").value("3")
            )
    }

    @Test
    fun `미로그인 상태로 플레이리스트 조회`() {
        mvc.perform(
            MockMvcRequestBuilders.get("http://localhost:8080/api/v1/playlists/404404404")
        ).andExpect(status().`is`(404))

        mvc.perform(
            MockMvcRequestBuilders.get("http://localhost:8080/api/v1/playlists/1")
        )
            .andExpect(status().isOk())
            .andExpectAll(
                jsonPath("\$.playlist.songs[0].id").value(2),
                jsonPath("\$.playlist.songs[1].id").value(8)
            )
    }

    @Test
    fun `로그인 상태로 플레이리스트 조회`() {
        mvc.perform(
            MockMvcRequestBuilders
                .get("http://localhost:8080/api/v1/playlists/1")
                .header("Authorization", "Bearer gnirps")
        )
            .andExpect(status().isOk())
            .andExpectAll(
                jsonPath("\$.playlist.songs[0].id").value(2),
                jsonPath("\$.playlist.songs[1].id").value(8)
            )
    }

    @Test
    fun `플레이리스트 좋아요`() {
        mvc.perform(
            MockMvcRequestBuilders.post("http://localhost:8080/api/v1/playlists/1/likes")
        ).andExpect(status().`is`(401))

        mvc.perform(
            MockMvcRequestBuilders
                .post("http://localhost:8080/api/v1/playlists/404404404/likes")
                .header("Authorization", "Bearer gnirps")
        ).andExpect(status().`is`(404))

        mvc.perform(
            MockMvcRequestBuilders
                .post("http://localhost:8080/api/v1/playlists/1/likes")
                .header("Authorization", "Bearer gnirps")
        ).andExpect(status().`is`(200))

        mvc.perform(
            MockMvcRequestBuilders
                .post("http://localhost:8080/api/v1/playlists/1/likes")
                .header("Authorization", "Bearer gnirps")
        ).andExpect(status().`is`(409))
    }

    @Test
    fun `플레이리스트 좋아요 취소`() {
        mvc.perform(
            MockMvcRequestBuilders.delete("http://localhost:8080/api/v1/playlists/1/likes")
        ).andExpect(status().`is`(401))

        mvc.perform(
            MockMvcRequestBuilders
                .delete("http://localhost:8080/api/v1/playlists/1/likes")
                .header("Authorization", "Bearer gnirps")
        ).andExpect(status().`is`(404))

        mvc.perform(
            MockMvcRequestBuilders
                .post("http://localhost:8080/api/v1/playlists/1/likes")
                .header("Authorization", "Bearer gnirps")
        ).andExpect(status().`is`(200))

        mvc.perform(
            MockMvcRequestBuilders
                .delete("http://localhost:8080/api/v1/playlists/1/likes")
                .header("Authorization", "Bearer gnirps")
        ).andExpect(status().`is`(200))
    }
>>>>>>> 12f7e172d693b44192e792143ceb21d43e0204a1
}
