package com.wafflestudio.seminar.spring2023.week3

import com.wafflestudio.seminar.spring2023.QueryCounter
import com.wafflestudio.seminar.spring2023.user.repository.UserEntity
import com.wafflestudio.seminar.spring2023.user.repository.UserRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.concurrent.Executors

@Disabled
@SpringBootTest
class ThreadLocalTest @Autowired constructor(
    private val userRepository: UserRepository,
    private val queryCounter: QueryCounter,
) {
    private val threads = Executors.newFixedThreadPool(2)

    @Test
    fun `ThreadLocal은 쓰레드별로 서로 다른 값을 본다 - 기본`() {
        val threadLocalNum = ThreadLocal.withInitial { 1 } // 초기값 1

        assertThat(threadLocalNum.get()).isEqualTo(1)

        threadLocalNum.set(2) // 현재 스레드에서 2로 변경

        assertThat(threadLocalNum.get()).isEqualTo(2) // 2로 변경된 값 확인

        threads.submit {
            assertThat(threadLocalNum.get()).isEqualTo(1) // 다른 스레드에서 조회했기 때문에 초기값 1

            threadLocalNum.set(3) // 다른 스레드에서 3으로 변경

            assertThat(threadLocalNum.get()).isEqualTo(3) // 3으로 변경된 값 확인
        }
            .get()

        assertThat(threadLocalNum.get()).isEqualTo(2) // 다른 스레드에서 3으로 변경했기 때문에 현재 스레드에서는 여전히 2
    }

    @Test
    fun `ThreadLocal은 쓰레드별로 서로 다른 값을 본다 - QueryCounter`() {
        val (users, queryCount) = queryCounter.count {
            val userFromCurrentThread = userRepository.findByUsername("spring")!!
            val userFromTheOtherThread = threads.submit<UserEntity> { userRepository.findByUsername("spring") }.get()

            userFromCurrentThread to userFromTheOtherThread
        }

        assertThat(users.first.id).isEqualTo(users.second.id)
        assertThat(users.first.username).isEqualTo(users.second.username)
        assertThat(queryCount).isEqualTo(1) // userFromTheOtherThread는 다른 스레드에서 조회했기 때문에, 쿼리 횟수가 늘어나지 않는다.
    }
}
