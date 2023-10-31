package com.wafflestudio.seminar.spring2023._web.log

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

interface LogRequest {
    operator fun invoke(request: Request)
}

/**
 * 스펙:
 *  1. 들어오는 모든 요청을 "[API-REQUEST] GET /api/v1/playlist-groups" 꼴로 로깅
 */
@Component
class LogRequestImpl : LogRequest {
    private val logger = LoggerFactory.getLogger(javaClass)

    override operator fun invoke(request: Request) {
        TODO()
    }
}

data class Request(
    val method: String,
    val path: String,
)
