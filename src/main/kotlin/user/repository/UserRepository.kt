package com.wafflestudio.seminar.spring2023.user.repository

import org.apache.catalina.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.Optional

interface UserRepository : JpaRepository<UserEntity, Long> {
    fun findByUsername(username: String): UserEntity?

    override fun findById(id: Long): Optional<UserEntity>
}
