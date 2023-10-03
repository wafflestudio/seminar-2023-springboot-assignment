package com.wafflestudio.seminar.spring2023.playlist.service

import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Service

@Primary
@Service
class PlaylistServiceImpl : PlaylistService {

    override fun getGroups(): List<PlaylistGroup> { //플레이리스트 그룹 조회 기능
        TODO()
        //제한 조건
        //오픈 상태의 플레이리스트 그룹을 조회, 연관된 플레이리스트가 없는 경우 결과에서 제외
        //오픈 상태의 플레이리스트 그룹을 조회, 연관된 플레이리스트가 없는 경우 결과에서 제외, 쿼리 횟수는 1개로 제한
    }

    override fun get(id: Long): Playlist { //플레이리스트 조회 기능(미로그인 상태와 로그인 상태 구분해서 처리)
        TODO()
        //제한 조건
        //플레이리스트 조회
        //플레이리스트 조회, 쿼리 횟수는 2회로 제한
        //존재하지 않는 플레이리스트 조회시 에러 발생
    }
}
