package com.wafflestudio.seminar.spring2023.playlist.service

import org.springframework.stereotype.Service
import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistLikeEntity
import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistLikeRepository
import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistRepository
import com.wafflestudio.seminar.spring2023.user.repository.UserRepository
//각 서비스의 반환 타입은 controller에서의 response들을 보면 알 수 있다.
//각 서비스에 들어가야하는 변수가 어떤 것인지도 controller를 보면 알 수 있다.
//사용가능한 에러의 종류는 playlistexception에 정의되어 있다. (나중에 컨트롤러에서 handlermethod로 각 에러에 대한 응답 코드를 정의해줘야 한다)
@Service
class PlaylistLikeServiceImpl(
        private val playlistLikeRepository: PlaylistLikeRepository,
        private val userRepository: UserRepository,
        private val playlistRepository: PlaylistRepository,//써야하는 인터페이스들 주입 받기
) : PlaylistLikeService {

    override fun exists(playlistId: Long, userId: Long): Boolean { //아래 두 기능을 구현하기 위해서 이미 좋아요가 되어 있는지 확인하는 기능
        //좋아요 여부 조회
        return playlistLikeRepository.existsByPlaylistIdAndUserId(playlistId, userId) //bool형으로 좋아요가 있는지 알려주기
    }

    override fun create(playlistId: Long, userId: Long) { //좋아요 추가 기능
        //제한 조건
        //존재하지 않는 플레이리스트에 좋아요를 누를 수 없다
        //이미 좋아요를 누른 플레이리스트는 또 좋아요를 누를 수 없다
        if (exists(playlistId, userId)) throw PlaylistAlreadyLikedException() //이미 좋아요 눌렀으면 에러 발생

        val playlist = playlistRepository.findById(playlistId).orElseThrow { PlaylistNotFoundException() }
        //플레이 리스트 아이디로 플레이리스트 받아오기(기본 제공 함수 findById 사용), 없으면 에러 발생
        val user = userRepository.findById(userId).get()
        //유저 아이디로 유저 받아오기(기본 제공 함수 findById 사용)

        val playlistLike = PlaylistLikeEntity(playlist = playlist, user = user) //에러 없으면 playlistlikeentity 추가
        playlistLikeRepository.save(playlistLike)
    }

    override fun delete(playlistId: Long, userId: Long) { //좋아요 삭제 기능
        //제한 조건
        //좋아요를 누르지 않은 플레이리스트에 좋아요 취소를 누를 수 없다
        playlistRepository.findById(playlistId).orElseThrow { PlaylistNotFoundException() }
        //플레이 리스트 아이디로 플레이리스트 받아오기(기본 제공 함수 findById 사용), 없으면 에러 발생

        if (!exists(playlistId, userId)) throw PlaylistNeverLikedException() //좋아요가 없는데 지우려고 하면 에러 발생

        playlistLikeRepository.deleteByPlaylistIdAndUserId(playlistId, userId) //에러 없으면 playlistlikeentity 지워주기
    }
}
