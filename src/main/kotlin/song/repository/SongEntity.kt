package com.wafflestudio.seminar.spring2023.song.repository

import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistSongEntity //playlist 폴더 안에 있는 엔티티를 불러와야함
import jakarta.persistence.*

@Entity(name = "songs") //songs 테이블을 매핑
class SongEntity (

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long = 0L,
        val title : String,
        val duration : Int = 0,
        @OneToMany(mappedBy = "song")
        val playlistSongs: List<PlaylistSongEntity>,
        @OneToMany(mappedBy = "song")
        val songArtists: List<SongArtistEntity>,
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "album_id") //외래키 명시
        val album: AlbumEntity,

)