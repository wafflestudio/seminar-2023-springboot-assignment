package com.wafflestudio.seminar.spring2023._web.log

import org.slf4j.LoggerFactory
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.http.HttpEntity
import org.springframework.stereotype.Component
import org.springframework.web.client.postForEntity
import java.util.concurrent.Executors
import java.util.concurrent.Future

interface AlertSlowResponse {
    operator fun invoke(slowResponse: SlowResponse): Future<Boolean>
}

data class SlowResponse(
    val method: String,
    val path: String,
    val duration: Long,
)

@Component
class AlertSlowResponseImpl(
    restTemplateBuilder: RestTemplateBuilder,
) : AlertSlowResponse {
    private val logger = LoggerFactory.getLogger(javaClass)
    private val threads = Executors.newFixedThreadPool(4)
    private val restTemplate = restTemplateBuilder
        .rootUri("https://slack.com")
        .defaultHeader("Authorization", "Bearer xoxb-5766809406786-6098325284464-Jzfs2DxOfD7DzCpccZhG6EfG")
        .build()

    override operator fun invoke(slowResponse: SlowResponse): Future<Boolean> {
        return threads.submit<Boolean> {
            val log = "[API-RESPONSE] ${slowResponse.method} ${slowResponse.path}, took ${slowResponse.duration}ms, PFCJeong"

            logger.warn(log)

            val slackResponse = restTemplate.postForEntity<SlackResponse>(
                "/api/chat.postMessage",
                HttpEntity(
                    mapOf(
                        "text" to log,
                        "channel" to "#spring-assignment-channel"
                    )
                )
            )

            slackResponse.body?.ok ?: false
        }
    }

    private data class SlackResponse(
        val ok: Boolean,
    )
}
