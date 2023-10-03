package com.wafflestudio.seminar.spring2023.playlist.controller

import com.wafflestudio.seminar.spring2023.playlist.service.*
import com.wafflestudio.seminar.spring2023.user.service.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
//요청과 응답을 처리하는 컨트롤러를 짜보자 -> 특정 요청이 들어왔을 때 올바른 서비스 로직으로 연결해주고, 서비스로부터 응답을 받아서 response 해주기
@RestController
class PlaylistController(
        private val playlistService: PlaylistService,
        private val playlistLikeService: PlaylistLikeService,
        private val userService: UserService, //써야하는 서비스 인터페이스들 주입 받기
) {

    @GetMapping("/api/v1/playlist-groups")
    fun getPlaylistGroup(): PlaylistGroupsResponse { //플레이리스트 그룹 조회
        val groups = playlistService.getGroups()
        return PlaylistGroupsResponse(groups) //playlistgroup의 list를 반환해줘야 한다
    }

    @GetMapping("/api/v1/playlists/{id}")
    fun getPlaylist( //플레이리스트 조회
        @PathVariable id: Long,//요청 경로의 가변 변수는 annotation으로 명시해 주는거 잊지 말기
        user: User?,
    ): PlaylistResponse {
        val playlist = playlistService.get(id) //id에 맞는 플레이리스트 받아오고
        val isLiked = user?.let { playlistLikeService.exists(id, it.id) } ?: false
        //좋아요 되어있는지 확인한 후(이게 플레이리스트 조회에 왜 필요한지는 잘 모르겠으나 이걸 가지고 플레이리스트가 좋아요 되어있는지까지 함께 표시하려는 것 같다)
        return PlaylistResponse(playlist, isLiked) //플레이리스트 응답
    }

    @PostMapping("/api/v1/playlists/{id}/likes")
    fun likePlaylist( //플레이 리스트 좋아요
        @PathVariable id: Long,
        @Authenticated user: User, //로그인 되어있어야 좋아요가 가능하므로 인증 필수 , 이 유저 데이터도 따로 받아와야 하는건가?
    ) {
        playlistLikeService.create(id, user.id)
    }

    @DeleteMapping("/api/v1/playlists/{id}/likes")
    fun undoLikePlaylist( //플레이 리스트 좋아요 취소
        @PathVariable id: Long,
        @Authenticated user: User,
    ) {
        playlistLikeService.delete(id, user.id)
    }

    //위에서 일일히 에러를 처리할 필요 없이, 위에서는 service를 바탕으로 한 로직만 생각하고, 실제 service에서 사용된 에러들은 여기서 핸들링 해주면 된다.
    @ExceptionHandler
    fun handleException(e: PlaylistException): ResponseEntity<Unit> {
        val status = when (e) {
            is PlaylistNotFoundException -> 404
            is PlaylistAlreadyLikedException, is PlaylistNeverLikedException -> 409
        } //try catch 구문보다 그냥 이게 편하다
        return ResponseEntity.status(status).build() //.build로 저번처럼 에러 응답 한방에 생성하기
    }
}

//아래는 미리 정의해두신 응답 클래스이다
//이 응답클래스가 구체적으로 http 명령으로 어떻게 변환되는지는 나도 잘 모르겠다.. 그냥 이거 쓰면 되겠지..
data class PlaylistGroupsResponse(val groups: List<PlaylistGroup>)

data class PlaylistResponse(val playlist: Playlist, val isLiked: Boolean)
