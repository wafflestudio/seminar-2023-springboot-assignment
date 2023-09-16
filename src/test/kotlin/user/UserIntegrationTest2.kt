package com.wafflestudio.seminar.spring2023.user

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

// TODO: 추가 과제
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserIntegrationTest2 @Autowired constructor(
    private val mvc: MockMvc,
    private val mapper: ObjectMapper,
)  {

    @Test
    fun `회원가입시에 유저 이름 혹은 비밀번호가 정해진 규칙에 맞지 않는 경우 400 응답을 내려준다`() {
        // bad username
        mvc.perform(
            MockMvcRequestBuilders.post("/api/v2/signup")
                .content(
                    mapper.writeValueAsString(
                        mapOf(
                            "username" to "bad",
                            "password" to "correct",
                            "image" to "https://wafflestudio.com/images/icon_intro.svg"
                        )
                    )
                )
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().`is`(400))

        // bad password
        mvc.perform(
            MockMvcRequestBuilders.post("/api/v2/signup")
                .content(
                    mapper.writeValueAsString(
                        mapOf(
                            "username" to "correct",
                            "password" to "bad",
                            "image" to "https://wafflestudio.com/images/icon_intro.svg"
                        )
                    )
                )
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().`is`(400))

        mvc.perform(
            MockMvcRequestBuilders.post("/api/v2/signup")
                .content(
                    mapper.writeValueAsString(
                        mapOf(
                            "username" to "test-${javaClass.name}-1",
                            "password" to "spring",
                            "image" to "https://wafflestudio.com/images/icon_intro.svg"
                        )
                    )
                )
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().`is`(200))
    }

    @Test
    fun `회원가입시에 이미 해당 유저 이름이 존재하면 409 응답을 내려준다`() {
        mvc.perform(
            MockMvcRequestBuilders.post("/api/v2/signup")
                .content(
                    mapper.writeValueAsString(
                        mapOf(
                            "username" to "test-${javaClass.name}-2",
                            "password" to "correct",
                            "image" to "https://wafflestudio.com/images/icon_intro.svg"
                        )
                    )
                )
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().`is`(200))

        mvc.perform(
            MockMvcRequestBuilders.post("/api/v2/signup")
                .content(
                    mapper.writeValueAsString(
                        mapOf(
                            "username" to "test-${javaClass.name}-2",
                            "password" to "correct2",
                            "image" to "https://wafflestudio.com/images/icon_intro2.svg"
                        )
                    )
                )
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().`is`(409))
    }

    @Test
    fun `로그인 정보가 정확하지 않으면 404 응답을 내려준다`() {
        mvc.perform(
            MockMvcRequestBuilders.post("/api/v2/signup")
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

        // not exist username
        mvc.perform(
            MockMvcRequestBuilders.post("/api/v2/signin")
                .content(
                    mapper.writeValueAsString(
                        mapOf(
                            "username" to "test-${javaClass.name}-404",
                            "password" to "correct",
                        )
                    )
                )
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().`is`(404))

        // wrong password
        mvc.perform(
            MockMvcRequestBuilders.post("/api/v2/signin")
                .content(
                    mapper.writeValueAsString(
                        mapOf(
                            "username" to "test-${javaClass.name}-3",
                            "password" to "bad",
                        )
                    )
                )
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().`is`(404))

        mvc.perform(
            MockMvcRequestBuilders.post("/api/v2/signin")
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
    }

    @Test
    fun `잘못된 인증 토큰으로 인증시 401 응답을 내려준다`() {
        mvc.perform(
            MockMvcRequestBuilders.post("/api/v2/signup")
                .content(
                    mapper.writeValueAsString(
                        mapOf(
                            "username" to "test-${javaClass.name}-4",
                            "password" to "correct",
                            "image" to "https://wafflestudio.com/images/icon_intro.svg"
                        )
                    )
                )
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().`is`(200))

        mvc.perform(
            MockMvcRequestBuilders.get("/api/v2/users/me")
                .header("Authorization", "Bearer bad")
        )
            .andExpect(MockMvcResultMatchers.status().`is`(401))

        mvc.perform(
            MockMvcRequestBuilders.get("/api/v2/users/me")
                .header("Authorization", "Bearer ${"test-${javaClass.name}-4".reversed()}")
        )
            .andExpect(MockMvcResultMatchers.status().`is`(200))
            .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("test-${javaClass.name}-4"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.image").value("https://wafflestudio.com/images/icon_intro.svg"))
    }
}
