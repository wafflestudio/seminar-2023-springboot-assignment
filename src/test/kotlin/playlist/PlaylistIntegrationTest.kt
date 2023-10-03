package com.wafflestudio.seminar.spring2023.playlist

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.hamcrest.Matchers.hasSize
import org.junit.jupiter.api.Test //api test 환경(spring에서 제공하는게 아니다!)
import org.springframework.transaction.annotation.Transactional

//MVC를 이용해서 직접 테스트 조건에 맞는 요청을 날리는 게 바로 통합 테스트(기능별 테스트가 아니라 api 단위로 테스트를 진행)
@Transactional
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PlaylistIntegrationTest @Autowired constructor(
    private val mvc: MockMvc, //mvc 주입 받기
) {
    //http 문서에 기록된 api별 테스트를 진행한다
    @Test
    fun `플레이리스트 그룹 조회`() {
        mvc.perform(get("/api/v1/playlist-groups") //요청 경로에 따른 mvc를 생성한다
                .contentType(MediaType.APPLICATION_JSON)) //요청 내용(body) 타입은 당연히 json
                .andExpect(status().isOk) //돌아오는 응답 status가 200번대인 경우에만 통과
    }
    //나머지 테스트도 위와 동일한 방식으로 짜주면 된다
    @Test
    fun `미로그인 상태로 플레이리스트 조회`() {
        mvc.perform(get("/api/v1/playlists/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)
    }

    @Test
    fun `로그인 상태로 플레이리스트 조회`() {
        mvc.perform(get("/api/v1/playlists/1")
                .header("Authorization", "Bearer gnirps")
                //로그인 된 유저에 대해서 작동해야 하므로, 요청 헤더에 인증을 받은 유저임을 표시해준다 (헤더에 Authroization : "Bearer gnirps"를 추가해준다)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)
    }

    @Test
    fun `플레이리스트 좋아요`() {
        mvc.perform(post("/api/v1/playlists/1/likes")
                .header("Authorization", "Bearer gnirps") //로그인 된 유저만 가능
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)
    }

    @Test
    fun `플레이리스트 좋아요 취소`() {
        mvc.perform(delete("/api/v1/playlists/1/likes")
                .header("Authorization", "Bearer gnirps") //로그인 된 유저만 가능
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)
    }
}
