package com.wafflestudio.seminar.spring2023.playlist.repository

import com.wafflestudio.seminar.spring2023.song.repository.SongEntity // song 필드 처리를 위해서는 song 폴더에 있는 엔티티를 불러와주어야 햔다
import jakarta.persistence.*

@Entity(name = "playlist_songs") //playlist_songs 테이블을 매핑
class PlaylistSongEntity (

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long =0L,
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "playlist_id") //연관관계의 주인임을 표시
        val playlist: PlaylistEntity,
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "song_id")
        val song: SongEntity,

)