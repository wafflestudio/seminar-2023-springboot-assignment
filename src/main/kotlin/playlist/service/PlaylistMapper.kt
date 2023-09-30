package com.wafflestudio.seminar.spring2023.playlist.service

import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistEntity
import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistGroupEntity
import com.wafflestudio.seminar.spring2023.song.repository.SongEntity
import com.wafflestudio.seminar.spring2023.song.service.SongMapper

class PlaylistMapper {
    companion object {
        fun toPlaylistDTO(entity: PlaylistEntity, songs: List<SongEntity>): Playlist {
            return Playlist(
                id = entity.id,
                title = entity.title,
                subtitle = entity.subtitle,
                image = entity.image,
                songs = songs.map { song -> SongMapper.toSongDTO(song) }
            )
        }

        fun toPlayListGroupDTO(entity: PlaylistGroupEntity): PlaylistGroup {
            return PlaylistGroup(
                id = entity.id,
                title = entity.title,
                playlists = entity.playlists.map { playlist ->
                    PlaylistBrief(
                        id = playlist.id,
                        title = playlist.title,
                        subtitle = playlist.subtitle,
                        image = playlist.image
                    )
                }
            )
        }
    }
}