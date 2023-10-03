package com.wafflestudio.seminar.spring2023.playlist.repository

import jakarta.persistence.*

@Entity(name = "playlist_groups") //playlist_groups 테이블을 매핑(이 이름은 er 다이어그램이나 스키마를 보고 알 수 있다)
class PlaylistGroupEntity (

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id :Long =0L,
        val title: String,
        val open: Boolean,
        @OneToMany(mappedBy = "group") //연관관계의 주인이 아님
        val playlists: List<PlaylistEntity>

)