/*
package com.wafflestudio.seminar.spring2023.playlist.service

import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistEntity
import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistGroupEntity
import com.wafflestudio.seminar.spring2023.song.repository.SongEntity
//import com.wafflestudio.seminar.spring2023.song.service.SongEntityMapper

class PlaylistMapper {
    companion object {
        fun toPlaylist(entity: PlaylistEntity, songs: List<SongEntity>): Playlist {
            return Playlist(
                id = entity.id,
                title = entity.title,
                subtitle = entity.subtitle,
                image = entity.image,
                songs = SongEntityMapper.mapSongEntitiesToSongs(songs)
            )
        }

        fun toPlayListGroup(entity: PlaylistGroupEntity): PlaylistGroup {
            return PlaylistGroup(
                id = entity.id,
                title = entity.title,
                playlists = entity.playlists.map { playlist -> toPlayListBrief(playlist) }
            )
        }
        private fun toPlayListBrief(entity: PlaylistEntity): PlaylistBrief {
            return PlaylistBrief(
                id = entity.id,
                title = entity.title,
                subtitle = entity.subtitle,
                image = entity.image
            )
        }
    }
}*/