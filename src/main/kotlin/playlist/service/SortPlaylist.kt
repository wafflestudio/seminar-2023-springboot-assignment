package com.wafflestudio.seminar.spring2023.playlist.service

import java.time.LocalDateTime

interface SortPlaylist {
    operator fun invoke(
        playlists: List<PlaylistBrief>,
        type: Type,
        at: LocalDateTime = LocalDateTime.now(),
    ): List<PlaylistBrief>

    enum class Type {
        DEFAULT, // 정렬 X
        VIEW, // 전체 조회 수 기준
        HOT, // 최근 1시간 동안의 조회 수 기준
    }
}
