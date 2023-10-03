package com.wafflestudio.seminar.spring2023.playlist.service

import org.springframework.stereotype.Service

@Service
class PlaylistLikeServiceImpl : PlaylistLikeService {

    override fun exists(playlistId: Long, userId: Long): Boolean { //아래 두 기능을 구현하기 위해서 이미 좋아요가 되어 있는지 확인하는 기능
        TODO()
        //좋아요 여부 조회
    }

    override fun create(playlistId: Long, userId: Long) { //좋아요 추가 기능
        TODO()
        //제한 조건
        //존재하지 않는 플레이리스트에 좋아요를 누를 수 없다
        //이미 좋아요를 누른 플레이리스트는 또 좋아요를 누를 수 없다
    }

    override fun delete(playlistId: Long, userId: Long) { //좋아요 삭제 기능
        TODO()
        //제한 조건
        //좋아요를 누르지 않은 플레이리스트에 좋아요 취소를 누를 수 없다
    }
}
