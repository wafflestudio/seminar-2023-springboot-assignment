package com.wafflestudio.seminar.spring2023.admin.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.wafflestudio.seminar.spring2023.admin.service.AdminBatchService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
class AdminController(
    private val adminBatchService: AdminBatchService,
    private val objectMapper: ObjectMapper,
) {

    @PostMapping("/admin/v1/batch/albums")
    fun insertAlbums(
        @RequestPart("albums.txt") file: MultipartFile,
    ) {
        adminBatchService.insertAlbums(
            albumInfos = objectMapper.readValue(file.inputStream.reader().readText())
        )
    }
}
