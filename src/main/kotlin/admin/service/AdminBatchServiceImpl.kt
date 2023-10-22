package com.wafflestudio.seminar.spring2023.admin.service

import com.wafflestudio.seminar.spring2023.song.repository.AlbumEntity
import com.wafflestudio.seminar.spring2023.song.repository.AlbumRepository
import com.wafflestudio.seminar.spring2023.song.repository.ArtistEntity
import com.wafflestudio.seminar.spring2023.song.repository.ArtistRepository
import com.wafflestudio.seminar.spring2023.song.repository.SongArtistEntity
import com.wafflestudio.seminar.spring2023.song.repository.SongEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.support.TransactionTemplate
import java.util.concurrent.Executors

@Service
class AdminBatchServiceImpl(
    private val artistRepository: ArtistRepository,
    private val albumRepository: AlbumRepository,
    txManager: PlatformTransactionManager,
) : AdminBatchService {
    private val txTemplate = TransactionTemplate(txManager)
    private val threads = Executors.newFixedThreadPool(16)

    override fun insertAlbums(albumInfos: List<BatchAlbumInfo>) {
        val jobs = albumInfos
            .map { albumInfo ->
                threads.submit {
                    txTemplate.executeWithoutResult { insertAlbum(albumInfo) }
                }
            }

        jobs.forEach { it.get() }
    }

    private fun insertAlbum(albumInfo: BatchAlbumInfo) {
        if (albumRepository.findByTitle(albumInfo.title) != null) {
            return
        }

        val artist = artistRepository.run {
            findByName(albumInfo.artist) ?: save(ArtistEntity(name = albumInfo.artist))
        }

        val album = albumRepository.save(
            AlbumEntity(
                title = albumInfo.title,
                image = albumInfo.image,
                artist = artist,
            )
        )

        artist.albums.add(album)

        albumInfo.songs.forEach { songInfo ->
            val existArtists = artistRepository.findByNameIn(songInfo.artists)
            val newArtists = songInfo.artists.filter { it !in existArtists.map { it.name } }
                .map { artistRepository.save(ArtistEntity(name = it)) }

            val newSong = SongEntity(
                title = songInfo.title,
                duration = songInfo.duration,
                album = album,
            )

            newSong.artists.addAll(
                (existArtists + newArtists).map {
                    SongArtistEntity(
                        song = newSong,
                        artist = it
                    )
                }
            )

            album.songs.add(newSong)
        }
    }
}
