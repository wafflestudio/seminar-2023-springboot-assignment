package com.wafflestudio.seminar.spring2023.playlist.service

interface PlaylistService {
    fun getGroups(): List<PlaylistGroup>
    fun get(id: Long): Playlist
}
