package com.wafflestudio.seminar.spring2023.playlist.service

import com.wafflestudio.seminar.spring2023.playlist.service.SortPlaylist.Type
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Future

@Service
class PlaylistViewServiceImpl : PlaylistViewService, SortPlaylist {

    /**
     * 스펙:
     *  1. 같은 유저-같은 플레이리스트의 조회 수는 1분에 1개까지만 허용한다.
     *  2. PlaylistView row 생성, PlaylistEntity의 viewCnt 업데이트가 atomic하게 동작해야 한다. (둘 다 모두 성공하거나, 둘 다 모두 실패해야 함)
     *  3. 플레이리스트 조회 요청이 동시에 다수 들어와도, 요청이 들어온 만큼 조회 수가 증가해야한다. (동시성 이슈가 없어야 함)
     *  4. 성공하면 true, 실패하면 false 반환
     *
     * 조건:
     *  1. Synchronized 사용 금지.
     *  2. create 함수 처리가, 플레이리스트 조회 API 응답시간에 영향을 미치면 안된다.
     *  3. create 함수가 실패해도, 플레이리스트 조회 API 응답은 성공해야 한다.
     *  4. Future가 리턴 타입인 이유를 고민해보며 구현하기.
     */
    override fun create(playlistId: Long, userId: Long, at: LocalDateTime): Future<Boolean> {
        return CompletableFuture.completedFuture(false) // FIXME
    }

    override fun invoke(playlists: List<PlaylistBrief>, type: Type, at: LocalDateTime): List<PlaylistBrief> {
        return when (type) {
            Type.DEFAULT -> playlists
            else -> TODO("Not yet implemented")
        }
    }
}
