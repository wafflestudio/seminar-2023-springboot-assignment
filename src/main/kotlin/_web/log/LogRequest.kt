package com.wafflestudio.seminar.spring2023._web.log

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

interface LogRequest {
    operator fun invoke(request: Request)
}

@Component
class LogRequestImpl : LogRequest {
    private val logger = LoggerFactory.getLogger(javaClass)

    override operator fun invoke(request: Request) {
        if (request.path.startsWith("/api") || request.path.startsWith("/admin")) {
            logger.info("[API-REQUEST] ${request.method} ${request.path}")
        }
    }
}

data class Request(
    val method: String,
    val path: String,
)
