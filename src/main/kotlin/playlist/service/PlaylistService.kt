package com.wafflestudio.seminar.spring2023.playlist.service

import com.wafflestudio.seminar.spring2023.playlist.service.SortPlaylist.Type

interface PlaylistService {
    fun getGroups(sortType: Type = Type.DEFAULT): List<PlaylistGroup>
    fun get(id: Long): Playlist
}
