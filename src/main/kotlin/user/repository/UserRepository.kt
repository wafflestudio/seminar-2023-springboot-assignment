package com.wafflestudio.seminar.spring2023.user.repository

import org.springframework.data.jpa.repository.JpaRepository

<<<<<<< HEAD
interface UserRepository : JpaRepository<UserEntity, Long>{
    fun findByUsername(Username : String): UserEntity?
=======
interface UserRepository : JpaRepository<UserEntity, Long> {
    fun findByUsername(username: String): UserEntity?
>>>>>>> 6f4df81c3a062e525f0ffbd2e4c8d81cb3ec020e
}
