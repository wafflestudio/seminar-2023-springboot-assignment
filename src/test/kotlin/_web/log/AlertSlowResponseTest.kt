package com.wafflestudio.seminar.spring2023._web.log

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class AlertSlowResponseTest @Autowired constructor(
    private val alertSlowResponse: AlertSlowResponse,
) {

    @Test
    fun `슬랙 채널에 속도가 늦은 응답을 전달`() {
        assertThat(alertSlowResponse(SlowResponse("GET", "/test", 3200)).get()).isTrue()
    }
}
