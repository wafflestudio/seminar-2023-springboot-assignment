package com.wafflestudio.seminar.spring2023.playlist.service

<<<<<<< HEAD
import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistGroupRepository
import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistRepository
import com.wafflestudio.seminar.spring2023.song.repository.SongRepository
=======
import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistEntity
import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistGroupEntity
import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistGroupRepository
import com.wafflestudio.seminar.spring2023.playlist.repository.PlaylistRepository
import com.wafflestudio.seminar.spring2023.playlist.service.SortPlaylist.Type
import com.wafflestudio.seminar.spring2023.song.repository.SongEntity
import com.wafflestudio.seminar.spring2023.song.repository.SongRepository
import com.wafflestudio.seminar.spring2023.song.service.Song
>>>>>>> 12f7e172d693b44192e792143ceb21d43e0204a1
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Service

@Primary
@Service
class PlaylistServiceImpl(
    private val playlistGroupRepository: PlaylistGroupRepository,
    private val playlistRepository: PlaylistRepository,
    private val songRepository: SongRepository,
<<<<<<< HEAD
) : PlaylistService {

    override fun getGroups(): List<PlaylistGroup> {
        //TODO()
        val playlistGroupEntities = playlistGroupRepository.findByOpenWithJoinFetch(true)
        val playlistGroups =  playlistGroupEntities.map { it.toPlaylistGroup() }
        val nonEmptyPlaylistGroups: MutableList<PlaylistGroup> = mutableListOf()
        for(playlistGroup in playlistGroups) {
            if (playlistGroup.playlists.isNotEmpty())
                nonEmptyPlaylistGroups += playlistGroup
        }
        return nonEmptyPlaylistGroups
    }

    override fun get(id: Long): Playlist {
        //TODO()
        val playlistEntity = playlistRepository.findByIdWithJoinFetch(id) ?: throw PlaylistNotFoundException()
        val songEntities =
            songRepository.findByIdInJoinFetch(playlistEntity.playlistSongs.map { it.song }.map { it.id })
        return playlistEntity.toPlaylist()
=======
    private val sortPlaylist: SortPlaylist,
) : PlaylistService {

    override fun getGroups(sortType: Type): List<PlaylistGroup> =
        playlistGroupRepository.findAllOpenWithAnyPlaylists()
            .map(::PlaylistGroup)
            .map { pg -> pg.copy(playlists = sortPlaylist(pg.playlists, sortType)) }

    override fun get(id: Long): Playlist {
        val playlist = playlistRepository.findByIdWithSongs(id) ?: throw PlaylistNotFoundException()

        val songs = songRepository.findAllByIdWithJoinFetch(ids = playlist.songs.map { it.song.id })

        // 과제를 위해 id가 7인 경우 예외적으로 느린 응답
        if (playlist.id == 7L) {
            Thread.sleep(1000 * 3)
        }

        return Playlist(playlist, songs)
>>>>>>> 12f7e172d693b44192e792143ceb21d43e0204a1
    }
}

private fun PlaylistGroup(entity: PlaylistGroupEntity) = PlaylistGroup(
    id = entity.id,
    title = entity.title,
    playlists = entity.playlists.map(::PlaylistBrief)
)

private fun PlaylistBrief(entity: PlaylistEntity) = PlaylistBrief(
    id = entity.id,
    title = entity.title,
    subtitle = entity.subtitle,
    image = entity.image,
)

private fun Playlist(entity: PlaylistEntity, songEntities: List<SongEntity>) = Playlist(
    id = entity.id,
    title = entity.title,
    subtitle = entity.subtitle,
    image = entity.image,
    songs = songEntities.map(::Song)
)
