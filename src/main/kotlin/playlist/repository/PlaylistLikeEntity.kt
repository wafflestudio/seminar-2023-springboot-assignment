package com.wafflestudio.seminar.spring2023.playlist.repository

import com.wafflestudio.seminar.spring2023.user.repository.UserEntity //user 필드를 정의해주기 위해서는 다른 폴더(User)에 있는 엔티티 클래스를 데려와야 한다
import jakarta.persistence.*
@Entity(name = "playlist_likes") //playlist_likes 테이블을 매핑
class PlaylistLikeEntity(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long = 0L,
        @ManyToOne(fetch = FetchType.LAZY) //이건 이제 익숙한 annotation
        @JoinColumn(name = "user_id") // 다대일 연관관계에서 연관관계의 주인임을 표현(외래키 명시)
        val user: UserEntity,
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "playlist_id")
        val playlist: PlaylistEntity,

)
