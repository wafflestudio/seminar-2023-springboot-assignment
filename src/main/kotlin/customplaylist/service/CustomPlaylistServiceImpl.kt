package com.wafflestudio.seminar.spring2023.customplaylist.service

import com.wafflestudio.seminar.spring2023.customplaylist.repository.CustomPlaylistEntity
import com.wafflestudio.seminar.spring2023.customplaylist.repository.CustomPlaylistRepository
import com.wafflestudio.seminar.spring2023.song.repository.SongEntity
import com.wafflestudio.seminar.spring2023.song.repository.SongRepository
import com.wafflestudio.seminar.spring2023.song.service.Song
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * 스펙:
 *  1. 커스텀 플레이리스트 생성시, 자동으로 생성되는 제목은 "내 플레이리스트 #{내 커스텀 플레이리스트 갯수 + 1}"이다.
 *  2. 곡 추가 시  CustomPlaylistSongEntity row 생성, CustomPlaylistEntity의 songCnt의 업데이트가 atomic하게 동작해야 한다. (둘 다 모두 성공하거나, 둘 다 모두 실패해야 함)
 *
 * 조건:
 *  1. Synchronized 사용 금지.
 *  2. 곡 추가 요청이 동시에 들어와도 동시성 이슈가 없어야 한다.(PlaylistViewServiceImpl에서 동시성 이슈를 해결한 방법과는 다른 방법을 사용할 것)
 *  3. JPA의 변경 감지 기능을 사용해야 한다.
 */
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
