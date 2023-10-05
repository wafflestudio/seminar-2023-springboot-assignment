package com.wafflestudio.seminar.spring2023.playlist.repository

import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface PlaylistRepository : JpaRepository<PlaylistEntity, Long> {
    @Query("""
select p from playlists p
join fetch p.playlist_songs ps
join fetch ps.song s
join fetch s.song_artists sa
join fetch sa.artist
join fetch s.album a
join fetch a.artist
where p.id = :id
    """)
    fun findPlaylistEntityById(id: Long): PlaylistEntity?
}

//join fetch p.playlist_songs ps
//left join fetch ps.song s
//left join fetch s.album a
//inner join a.artist