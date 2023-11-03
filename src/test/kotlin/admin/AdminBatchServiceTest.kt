package com.wafflestudio.seminar.spring2023.admin

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.wafflestudio.seminar.spring2023.admin.service.AdminBatchService
import com.wafflestudio.seminar.spring2023.admin.service.BatchAlbumInfo
import com.wafflestudio.seminar.spring2023.song.repository.AlbumRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.io.Resource
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.support.TransactionTemplate

@SpringBootTest
class AdminBatchServiceTest @Autowired constructor(
    @Value("classpath:albums.txt") private val albumFile: Resource,
    private val adminBatchService: AdminBatchService,
    private val albumRepository: AlbumRepository,
    private val objectMapper: ObjectMapper,
    private val txManager: PlatformTransactionManager,
) {

    @Test
    fun `json으로 정리된 앨범 데이터를 DB 인서트, 배치 작업은 500ms보다 빨리 완료되어야 함`() {
        val albumInfos: List<BatchAlbumInfo> = objectMapper.readValue(albumFile.inputStream.reader().readText())
        val start = System.currentTimeMillis()

        adminBatchService.insertAlbums(albumInfos)

        assertThat((System.currentTimeMillis() - start)).isLessThan(500)

        TransactionTemplate(txManager).executeWithoutResult {
            albumInfos.forEach { albumInfo ->
                val album = albumRepository.findByTitle(albumInfo.title)!!

                assertThat(albumInfo.songs.map { it.title }.toSet()).isEqualTo(album.songs.map { it.title }.toSet())
                assertThat(albumInfo.artist).isEqualTo(album.artist.name)

                albumInfo.songs.forEach { songInfo ->
                    val song = album.songs.first { it.title == songInfo.title }
                    assertThat(songInfo.artists.toSet()).isEqualTo(song.artists.map { it.artist.name }.toSet())
                }
            }
        }
    }
}