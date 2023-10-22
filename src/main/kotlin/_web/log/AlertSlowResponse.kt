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

/**
 * 스펙:
 *  1. 3초 이상 걸린 응답을 "[API-RESPONSE] GET /api/v1/playlists/7, took 3132ms, PFCJeong" 꼴로 로깅 (logging level은 warn)
 *  2. 3초 이상 걸린 응답을 "[API-RESPONSE] GET /api/v1/playlists/7, took 3132ms, PFCJeong" 꼴로 슬랙 채널에 전달 (http method, path, 걸린 시간, 본인의 깃허브 아이디)
 *  3. RestTemplate을 사용하여 아래와 같이 요청을 날린다.
 *      curl --location 'https://slack.com/api/chat.postMessage' \
 *           --header 'Authorization: Bearer $slackToken' \
 *           --header 'Content-Type: application/json' \
 *           --data '{ "text":"[API-RESPONSE] GET /api/v1/playlists/7, took 3132ms, PFCJeong", "channel": "#spring-assignment-channel"}'
 *  4. 위 요청의 응답은 "{ "ok": true }"로 온다. invoke 함수는 이 "ok" 응답 값을 반환.
 *  5. 슬랙 API의 성공 여부와 상관 없이, 우리 서버의 응답은 정상적으로 내려가야 한다.
 */
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
