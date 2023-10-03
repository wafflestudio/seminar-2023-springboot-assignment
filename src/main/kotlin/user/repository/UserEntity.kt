package com.wafflestudio.seminar.spring2023.user.repository

import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistLikeEntity //playlistlikeentity 불러오기
import jakarta.persistence.*

@Entity(name = "users")
class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    val username: String,
    val password: String,
    val image: String,
    @OneToMany(mappedBy = "user") //연관관계 추가
    val playlistLikes: MutableList<PlaylistLikeEntity> = mutableListOf() //뮤터블리스트로 정의
)
