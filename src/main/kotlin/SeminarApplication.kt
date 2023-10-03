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

@RestController //REST API 서버
class DemoController{

    @GetMapping("/users/{userId}") //GET METHOD, users/1, users/2, ...
    fun getDemoUser(
        @PathVariable userId: Long,
    ): ResponseEntity<DemoUser> {//HTTP, DemoUser
        val user = DemoUser(id = userId, name = "user-$userId")

        return ResponseEntity.status(200).body(user)
    }
}

class DemoUser(
    val id : Long,
    val name : String,
)