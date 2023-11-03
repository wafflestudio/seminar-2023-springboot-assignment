package com.wafflestudio.seminar.spring2023.week3

import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders

@Disabled
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MultiThreadTest @Autowired constructor(
    private val mvc: MockMvc,
) {

    @Test
    fun `플레이리스트 조회 싱글스레드-멀티스레드 비교`() {
        var start = System.currentTimeMillis()

        for (i in 0 until 1000) {
            mvc.perform(
                MockMvcRequestBuilders
                    .get("http://localhost:8080/api/v1/playlists/1") // v2는 플레이리스트 조회와 좋아요 조회를 순차 처리
                    .header("Authorization", "Bearer gnirps")
            )
        }

        val durationV1 = System.currentTimeMillis() - start

        start = System.currentTimeMillis()

        for (i in 0 until 1000) {
            mvc.perform(
                MockMvcRequestBuilders
                    .get("http://localhost:8080/api/v2/playlists/1") // v2는 플레이리스트 조회와 좋아요 조회를 병렬 처리
                    .header("Authorization", "Bearer gnirps")
            )
        }

        val durationV2 = System.currentTimeMillis() - start

        println("v1: $durationV1, v2: $durationV2")
    }
}
