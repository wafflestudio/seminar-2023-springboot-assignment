package com.wafflestudio.seminar.spring2023.admin.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
class AdminController {

    @PostMapping("/admin/v1/batch/albums")
    fun insertAlbums(
        @RequestPart("albums.txt") file: MultipartFile,
    ) {
        TODO()
    }
}
