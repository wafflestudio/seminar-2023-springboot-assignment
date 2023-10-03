package com.wafflestudio.seminar.spring2023.playlist.service

import com.wafflestudio.seminar.spring2023.user.service.User

interface PlaylistLikeService {
    fun exists(playlistId: Long, userId: Long): Boolean
    fun create(playlistId: Long, userId: Long)
    fun delete(playlistId: Long, userId: Long)
}
