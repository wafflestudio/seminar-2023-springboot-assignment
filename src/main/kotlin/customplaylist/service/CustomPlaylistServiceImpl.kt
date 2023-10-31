package com.wafflestudio.seminar.spring2023.customplaylist.service

import org.springframework.stereotype.Service

/**
 * 스펙:
 *  1. 커스텀 플레이리스트 생성시, 자동으로 생성되는 제목은 "내 플레이리스트 #{내 커스텀 플레이리스트 갯수 + 1}"이다.
 *  2. 곡 추가 시  CustomPlaylistSongEntity row 생성, CustomPlaylistEntity의 songCnt의 업데이트가 atomic하게 동작해야 한다. (둘 다 모두 성공하거나, 둘 다 모두 실패해야 함)
 *
 * 조건:
 *  1. Synchronized 사용 금지.
 *  2. 곡 추가 요청이 동시에 들어와도 동시성 이슈가 없어야 한다.(PlaylistViewServiceImpl에서 동시성 이슈를 해결한 방법과는 다른 방법을 사용할 것)
 *  3. JPA의 변경 감지 기능을 사용해야 한다.
 */
@Service
class CustomPlaylistServiceImpl : CustomPlaylistService {

    override fun get(userId: Long, customPlaylistId: Long): CustomPlaylist {
        TODO("Not yet implemented")
    }

    override fun gets(userId: Long): List<CustomPlaylistBrief> {
        TODO("Not yet implemented")
    }

    override fun create(userId: Long): CustomPlaylistBrief {
        TODO("Not yet implemented")
    }

    override fun patch(userId: Long, customPlaylistId: Long, title: String): CustomPlaylistBrief {
        TODO("Not yet implemented")
    }

    override fun addSong(userId: Long, customPlaylistId: Long, songId: Long): CustomPlaylistBrief {
        TODO("Not yet implemented")
    }
}
