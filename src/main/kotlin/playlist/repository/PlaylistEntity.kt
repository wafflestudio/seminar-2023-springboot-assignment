package com.wafflestudio.seminar.spring2023.playlist.repository

//er 다이어그램 보고 각각의 엔티티를 구성
//OneToMany,ManyToOne의 연관관계에 유의해서 annotation 작성 및 변수 지정 -> ManyToOne은 과한 쿼리 발생 제한을 위해 FetchType.LAZY 옵션 사용
import jakarta.persistence.*
//사용하고자 하는 annotation 일일히 불러오기 귀찮으므로 한번에 불러오기
@Entity(name = "playlists") //playlists 테이블을 매핑
class PlaylistEntity (

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY) //왜 쓰는지는 잘 모르겠는데 일단 써야 되는거 같다...
        val id: Long,
        val title: String,
        val subtitle: String,
        val image: String,
        @OneToMany(mappedBy = "playlist") //일대다 관계에서 연관관계의 주인이 아님을 표시
        val playlistLikes: List<PlaylistLikeEntity>,
        @ManyToOne(fetch = FetchType.LAZY) //다대일 연관관계에서 과한 쿼리 생성을 방지
        @JoinColumn(name = "group_id") //연관관계의 주인이므로 외래키를 명시
        val group: PlaylistGroupEntity,
        @OneToMany(mappedBy = "playlist")
        val playlistSongs: List<PlaylistSongEntity>,

)