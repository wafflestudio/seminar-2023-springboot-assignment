package com.wafflestudio.seminar.spring2023._web.log

import org.junit.jupiter.api.Test
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.kotlin.any
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.transaction.annotation.Transactional

@Transactional
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LogIntegrationTest @Autowired constructor(
    private val mvc: MockMvc,
    @MockBean private val logRequest: LogRequest,
    @MockBean private val alertSlowResponse: AlertSlowResponse,
){

    @Test
    fun `API 요청이 들어왔을 때 path를 로깅`() {
        mvc.perform(
            MockMvcRequestBuilders.get("/api/v1/playlist-groups")
        )

        verify(logRequest, times(1))
            .invoke(any())
    }

    @Test
    fun `API 응답이 3초를 넘어가면 path와 응답 속도를 로깅`() {
        mvc.perform(
            MockMvcRequestBuilders.get("/api/v1/playlists/1")
        )

        verify(alertSlowResponse, times(0))
            .invoke(any())

        mvc.perform(
            MockMvcRequestBuilders.get("/api/v1/playlists/7")
        )

        verify(alertSlowResponse, times(1))
            .invoke(any())
    }
}
