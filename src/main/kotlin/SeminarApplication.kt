package com.wafflestudio.seminar.spring2023

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
class SeminarApplication

fun main() {
    runApplication<SeminarApplication>()
}

@RestController
    class DemoController {
        @GetMapping("/users/{userId}")
        fun getDemoUser(
            @PathVariable userId: Long,
        ): ResponseEntity<DemoUser> {
            val user = DemoUser(Id = userId, name = "user-$userId")
            return ResponseEntity.ok(user)
        }
    }

    class DemoUser(
        val Id:Long,
        val name:String
    )