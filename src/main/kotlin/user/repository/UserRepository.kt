package com.wafflestudio.seminar.spring2023.user.repository

import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<UserEntity, Long>{
    fun findByUsername(name: String):
            UserEntity?
}
