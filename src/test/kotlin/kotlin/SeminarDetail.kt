package com.wafflestudio.seminar.spring2023.kotlin

import java.time.LocalDate

data class SeminarDetail(
    val id: Long,
    val name: String,
    val type: Type,
    val startAt: LocalDate,
) {
    enum class Type {
        MOBILE, WEB, SERVER
    }
}
