package com.wafflestudio.seminar.spring2023.playlist.service

import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Service
import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistEntity
import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistGroupRepository
import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistRepository
import com.wafflestudio.seminar.spring2023.song.repository.SongRepository
import com.wafflestudio.seminar.spring2023.song.service.Artist
import com.wafflestudio.seminar.spring2023.song.service.Song
//각 서비스의 반환 타입은 controller에서의 response들을 보면 알 수 있다.
//각 서비스에 들어가야하는 변수가 어떤 것인지도 controller를 보면 알 수 있다.
@Primary
@Service
class PlaylistServiceImpl(
        private val playlistGroupRepository: PlaylistGroupRepository,
        private val playlistRepository: PlaylistRepository,
        private val songRepository: SongRepository //써야하는 인터페이스 주입 받기
) : PlaylistService {

    override fun getGroups(): List<PlaylistGroup> { //플레이리스트 그룹 조회 기능
        //제한 조건
        //오픈 상태의 플레이리스트 그룹을 조회, 연관된 플레이리스트가 없는 경우 결과에서 제외
        //오픈 상태의 플레이리스트 그룹을 조회, 연관된 플레이리스트가 없는 경우 결과에서 제외, 쿼리 횟수는 1개로 제한
        val playlistGroupEntities = playlistGroupRepository.findAllWithPlaylists().filter { it.playlists.isNotEmpty() }.filter { it.open }
        //비어있는 플레이리스트 그룹은 제외하고 플레이리스트 그룹 모두 검색해서 엔티티로 받아옴
        return playlistGroupEntities.map { grEntity ->
            PlaylistGroup(
                    id = grEntity.id,
                    title = grEntity.title,
                    playlists = grEntity.playlists.map { plEntity->
                        PlaylistBrief( id = plEntity.id, title = plEntity.title, subtitle = plEntity.subtitle, image = plEntity.image )
                    }.toList()//타입(List) 맞춰주기
            )
        }
    }

    override fun get(id: Long): Playlist { //플레이리스트 조회 기능(플레이 리스트 속 노래 정보를 같이 반환해줘야 함)(미로그인 상태와 로그인 상태 구분해서 처리)
        //제한 조건
        //플레이리스트 조회
        //플레이리스트 조회, 쿼리 횟수는 2회로 제한
        //존재하지 않는 플레이리스트 조회시 에러 발생
        val playlistEntity = playlistRepository.findByIdWithSongs(id = id) ?: throw PlaylistNotFoundException() //아이디에 맞는 플레이리스트 받아오기(없으면 에러)
        val songIds = playlistEntity.playlistSongs.map { it.song.id } //플레이리스트의 노래 정보 반환을 위한 노래 아이디 추출
        val songs = songRepository.findSongEntitiesByIdsWithArtists(songIds).map { sEntity ->  //플레이리스트의 노래 정보 준비
                    Song(id = sEntity.id,
                            title = sEntity.title,
                            artists = sEntity.songArtists.map { saEntity -> val aArtist = saEntity.artist
                                Artist(id = aArtist.id, name = aArtist.name)
                            }.toList(), //타입(List) 맞춰주기
                            album = sEntity.album.title,
                            image = sEntity.album.image,
                            duration = sEntity.duration.toString() //문자열 처리 필요
                    )
                }.toList() //타입(List) 맞춰주기

        return Playlist(id = playlistEntity.id, title = playlistEntity.title, subtitle = playlistEntity.subtitle, image = playlistEntity.image, songs = songs)
        //타입(Playlist)에 맞게 플레이리스트 반환
    }
}
