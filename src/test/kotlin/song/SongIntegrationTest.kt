package com.wafflestudio.seminar.spring2023.song

import org.springframework.transaction.annotation.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.junit.jupiter.api.Test
import org.hamcrest.Matchers.* //Matcher 사용해서 제목 일치 확인

@Transactional
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SongIntegrationTest @Autowired constructor(
    private val mvc: MockMvc,
) {
    @Test
    fun `키워드가 들어간 제목의 곡 검색`() {
        mvc.perform(get("/api/v1/songs?keyword=Seven") //요청 경로 설정
                .contentType(MediaType.APPLICATION_JSON)) //요청 내용 타입 설정
                .andExpect(status().isOk) //200번대 응답인 경우에만 테스트 통과
                .andExpect(jsonPath("$.songs[*].title", everyItem(matchesPattern(".*Seven.*"))))
                //응답받은 내용들이 모두 제목에 키워드를 보유하고 있는 패턴이 맞아야 테스트 통과
    }

    @Test
    fun `키워드가 들어간 제목의 앨범 검색`() {
        mvc.perform(get("/api/v1/albums?keyword=Seven")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.albums[*].title", everyItem(matchesPattern(".*Seven.*"))))

    }
}
