package com.wafflestudio.seminar.spring2023.admin.service

interface AdminBatchService {
    fun insertAlbums(albumInfos: List<BatchAlbumInfo>)
}

data class BatchAlbumInfo(
    val artist: String,
    val title: String,
    val image: String,
    val songs: List<BatchSongInfo>,
) {
    data class BatchSongInfo(
        val title: String,
        val artists: List<String>,
        val duration: Int,
    )
}
