package com.wafflestudio.seminar.spring2023

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.http.ResponseEntity
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@EnableScheduling
@SpringBootApplication
class SeminarApplication

fun main() {
    runApplication<SeminarApplication>()
}