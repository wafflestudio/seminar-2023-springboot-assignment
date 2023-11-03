package com.wafflestudio.seminar.spring2023.customplaylist.service

interface CustomPlaylistService {
    fun get(userId: Long, customPlaylistId: Long): CustomPlaylist
    fun gets(userId: Long): List<CustomPlaylistBrief>
    fun create(userId: Long): CustomPlaylistBrief
    fun patch(userId: Long, customPlaylistId: Long, title: String): CustomPlaylistBrief
    fun addSong(userId: Long, customPlaylistId: Long, songId: Long): CustomPlaylistBrief
}
