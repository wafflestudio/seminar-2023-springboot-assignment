package com.wafflestudio.seminar.spring2023.kotlin

import com.wafflestudio.seminar.spring2023.kotlin.SeminarDetail.Type
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDate

// KotlinCheetSheet.kt 참고
class KotlinTest {

    @Test
    fun `9월 3일 이후에 시작하는 SeminarDetail 목록을, id 기준으로 내림차순 정렬`() {
        val answer: List<SeminarDetail> = seminarDetails
            .filter { it.startAt > LocalDate.parse("2023-09-03") }
            .sortedByDescending { it.id }

        assertThat(answer).isEqualTo(answers1)
    }

    @Test
    fun `모바일 타입의 SeminarBrief 목록을, 시작날짜를 기준으로 오름차순 정렬`() {
        // FIXME: seminaDetails에 filter, sortedBy, map을 사용하여 구현 (KotlinCheetSheet.kt 참고)
        val answer: List<SeminarBrief> = seminarDetails
            .filter { it.type == Type.MOBILE }
            .sortedBy { it.startAt }
            .map { SeminarBrief(it.name, it.startAt) }

        assertThat(answer).isEqualTo(answer)
    }

    @Test
    fun `타입별 세미나 갯수 카운트`() {
        // FIXME:  seminaDetails에 forEach, when을 사용하여 구현 (KotlinCheetSheet.kt 참고)
        var mobileCnt = 0
        var webCnt = 0
        var serverCnt = 0

        seminarDetails.forEach {
            when (it.type) {
                Type.MOBILE -> mobileCnt++
                Type.WEB -> webCnt++
                Type.SERVER -> serverCnt++
            }
        }

        assertThat(mobileCnt).isEqualTo(2)
        assertThat(webCnt).isEqualTo(1)
        assertThat(serverCnt).isEqualTo(2)
    }
}

private val seminarDetails = listOf(
    SeminarDetail(
        id = 1,
        name = "IOS",
        type = Type.MOBILE,
        startAt = LocalDate.parse("2023-09-05"),
    ),
    SeminarDetail(
        id = 2,
        name = "ANDROID",
        type = Type.MOBILE,
        startAt = LocalDate.parse("2023-09-03"),
    ),
    SeminarDetail(
        id = 3,
        name = "REACT",
        type = Type.WEB,
        startAt = LocalDate.parse("2023-09-04"),
    ),
    SeminarDetail(
        id = 4,
        name = "DJANGO",
        type = Type.SERVER,
        startAt = LocalDate.parse("2023-09-02"),
    ),
    SeminarDetail(
        id = 5,
        name = "SPRING",
        type = Type.SERVER,
        startAt = LocalDate.parse("2023-09-06"),
    )
)

private val answers1 = listOf(
    SeminarDetail(
        id = 5,
        name = "SPRING",
        type = Type.SERVER,
        startAt = LocalDate.parse("2023-09-06"),
    ),
    SeminarDetail(
        id = 3,
        name = "REACT",
        type = Type.WEB,
        startAt = LocalDate.parse("2023-09-04"),
    ),
    SeminarDetail(
        id = 1,
        name = "IOS",
        type = Type.MOBILE,
        startAt = LocalDate.parse("2023-09-05"),
    ),
)

private val answers2 = listOf(
    SeminarBrief(
        name = "ANDROID",
        startAt = LocalDate.parse("2023-09-03"),
    ),
    SeminarBrief(
        name = "IOS",
        startAt = LocalDate.parse("2023-09-05"),
    ),
)
