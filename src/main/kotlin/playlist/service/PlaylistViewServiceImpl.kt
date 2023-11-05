package com.wafflestudio.seminar.spring2023.playlist.service

import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistRepository
import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistViewEntity
import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistViewRepository
import com.wafflestudio.seminar.spring2023.playlist.service.SortPlaylist.Type
import org.springframework.stereotype.Service
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.support.TransactionTemplate
import java.time.LocalDateTime
import java.util.concurrent.Executors
import java.util.concurrent.Future

@Service
class PlaylistViewServiceImpl(
    private val playlistRepository: PlaylistRepository,
    private val playlistViewRepository: PlaylistViewRepository,
    txManager: PlatformTransactionManager,
) : PlaylistViewService, SortPlaylist {
    private val executors = Executors.newFixedThreadPool(8)
    private val txTemplate = TransactionTemplate(txManager)

    override fun create(playlistId: Long, userId: Long, at: LocalDateTime): Future<Boolean> {
        return executors.submit<Boolean> {
            txTemplate.execute {
                if (shouldIgnore(playlistId = playlistId, userId = userId, at = at)) {
                    return@execute false
                }

                playlistViewRepository.save(
                    PlaylistViewEntity(
                        playlistId = playlistId,
                        userId = userId,
                        createdAt = at
                    )
                )

                playlistRepository.incrementViewCnt(playlistId)

                true
            }
        }
    }

    override fun invoke(playlists: List<PlaylistBrief>, type: Type, at: LocalDateTime): List<PlaylistBrief> {
        return when (type) {
            Type.DEFAULT -> playlists
            else -> {
                val playlistIds = playlists.map { it.id }

                val idToViewCnt = if (type == Type.VIEW) {
                    playlistRepository.findAllById(playlistIds)
                        .associate { it.id to it.viewCnt }
                } else {
                    playlistViewRepository.findAllByPlaylistIdInAndCreatedAtAfter(
                        playlistIds = playlistIds,
                        after = at.minusHours(1)
                    )
                        .groupBy { it.playlistId }
                        .mapValues { it.value.size }
                }

                playlists.sortedByDescending { idToViewCnt[it.id] ?: 0 }
            }
        }
    }

    private fun shouldIgnore(playlistId: Long, userId: Long, at: LocalDateTime): Boolean =
        playlistViewRepository.existsByPlaylistIdAndUserIdAndCreatedAtAfterAndCreatedAtBefore(
            playlistId = playlistId,
            userId = userId,
            after = at.minusMinutes(1),
            before = at
        )
}
