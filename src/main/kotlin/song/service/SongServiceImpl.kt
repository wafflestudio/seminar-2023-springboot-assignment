package com.wafflestudio.seminar.spring2023.song.service

import org.springframework.stereotype.Service
import com.wafflestudio.seminar.spring2023.song.repository.AlbumRepository
import com.wafflestudio.seminar.spring2023.song.repository.SongRepository

@Service
class SongServiceImpl(
        private val songRepository: SongRepository,
        private val albumRepository: AlbumRepository //필요한 인터페이스 주입 받아오기!
) : SongService {

    override fun search(keyword: String): List<Song> { //곡 검색 기능
        //제한 조건
        //제목에 키워드를 포함한 곡 검색, 제목 길이가 짧은 순으로 정렬
        //제목에 키워드를 포함한 곡 검색, 제목 길이가 짧은 순으로 정렬, 쿼리 횟수는 1개로 제한
        val songEntities = songRepository.findByTitleContainingWithArtists(keyword=keyword) //키워드를 포함한 노래의 songentitiy를 다 받아온다(리스트로)

        return songEntities.map { sEntity-> //songEntity로서 곡에 대한 정보 전체를 controller로 넘겨 준다
            Song(id = sEntity.id,
                    title = sEntity.title,
                    artists = sEntity.songArtists.map { saEntity-> val aArtist = saEntity.artist
                        Artist(id= aArtist.id, name = aArtist.name) }.toList(),
                    album = sEntity.album.title,
                    image = sEntity.album.image,
                    duration = sEntity.duration.toString())}.toList() //반환 형태(Song)에 맞게 리스트로 바꿔주기
    }

    override fun searchAlbum(keyword: String): List<Album> { //앨범 검색 기능
        //제한 조건
        //제목에 키워드를 포함한 앨범 검색, 제목 길이가 짧은 순으로 정렬
        //제목에 키워드를 포함한 앨범 검색, 제목 길이가 짧은 순으로 정렬, 쿼리 횟수는 1개로 제한
        val albumEntities = albumRepository.findByTitleContainingWithArtistOrderByTitleAsc(keyword = keyword)
        return albumEntities.map { aEntity ->
            Album(id = aEntity.id,
                    title = aEntity.title,
                    image = aEntity.image,
                    artist = Artist(id = aEntity.artist.id, name = aEntity.artist.name)
            )
        } //여기는 toList 안해줘도 에러가 안나는데 그 이유를 모르겠다...
    }
}
