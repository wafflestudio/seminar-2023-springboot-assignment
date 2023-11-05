package com.wafflestudio.seminar.spring2023.customplaylist.service

import com.wafflestudio.seminar.spring2023.customplaylist.repository.CustomPlaylistEntity
import com.wafflestudio.seminar.spring2023.customplaylist.repository.CustomPlaylistRepository
import com.wafflestudio.seminar.spring2023.song.repository.SongEntity
import com.wafflestudio.seminar.spring2023.song.repository.SongRepository
import com.wafflestudio.seminar.spring2023.song.service.Song
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CustomPlaylistServiceImpl(
    private val customPlaylistRepository: CustomPlaylistRepository,
    private val songRepository: SongRepository,
) : CustomPlaylistService {

    override fun get(userId: Long, customPlaylistId: Long): CustomPlaylist {
        val customPlaylist = customPlaylistRepository.findByIdWithSongs(id = customPlaylistId, userId = userId)

        val songs = songRepository.findAllByIdWithJoinFetch(ids = customPlaylist.songs.map { it.song.id })

        return CustomPlaylist(customPlaylist, songs)
    }

    override fun gets(userId: Long): List<CustomPlaylistBrief> {
        return customPlaylistRepository.findAllByUserId(userId).map(::CustomPlaylistBrief)
    }

    @Transactional
    override fun create(userId: Long): CustomPlaylistBrief {
        val nextId = customPlaylistRepository.countByUserId(userId) + 1

        val created = customPlaylistRepository.save(
            CustomPlaylistEntity(
                userId = userId,
                title = "내 플레이리스트 #${nextId}",
            )
        )

        return CustomPlaylistBrief(created)
    }

    @Transactional
    override fun patch(userId: Long, customPlaylistId: Long, title: String): CustomPlaylistBrief {
        val customPlaylist = customPlaylistRepository.findByIdAndUserId(id = customPlaylistId, userId = userId)
            ?: throw CustomPlaylistNotFoundException()

        customPlaylist.title = title

        return CustomPlaylistBrief(customPlaylist)
    }

    @Transactional
    override fun addSong(userId: Long, customPlaylistId: Long, songId: Long): CustomPlaylistBrief {
        val customPlaylist = customPlaylistRepository.findByIdAndUserIdForUpdate(id = customPlaylistId, userId = userId)
            ?: throw CustomPlaylistNotFoundException()

        val song = songRepository.findById(songId).orElseThrow(::SongNotFoundException)

        customPlaylist.addSong(song)

        return CustomPlaylistBrief(customPlaylist)
    }
}

private fun CustomPlaylist(customPlaylistEntity: CustomPlaylistEntity, songEntities: List<SongEntity>) = CustomPlaylist(
    id = customPlaylistEntity.id,
    title = customPlaylistEntity.title,
    songs = songEntities.map(::Song)
)

private fun CustomPlaylistBrief(customPlaylistEntity: CustomPlaylistEntity) = CustomPlaylistBrief(
    id = customPlaylistEntity.id,
    title = customPlaylistEntity.title,
    songCnt = customPlaylistEntity.songCnt,
)
