package com.wafflestudio.seminar.spring2023

import org.hibernate.cfg.AvailableSettings
import org.hibernate.resource.jdbc.spi.StatementInspector
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component

@Component
class QueryCounter : StatementInspector {
    data class Result<K>(
        val value: K,
        val queryCount: Int,
    )

    private val isCounting: ThreadLocal<Boolean> = ThreadLocal.withInitial { false }
    private val queryCount: ThreadLocal<Int> = ThreadLocal.withInitial { 0 }

    override fun inspect(sql: String): String {
        if (isCounting.get()) {
            queryCount.set(queryCount.get() + 1)
        }

        return sql
    }

    fun <K> count(block: () -> K): Result<K> {
        isCounting.set(true)
        queryCount.set(0)

        val result = block()

        isCounting.set(false)

        return Result(result, queryCount.get())
    }
}

@Configuration
class HibernateConfig {
    @Bean
    fun hibernatePropertyConfig(queryCounter: QueryCounter): HibernatePropertiesCustomizer? {
        return HibernatePropertiesCustomizer { hibernateProperties: MutableMap<String?, Any?> ->
            hibernateProperties[AvailableSettings.STATEMENT_INSPECTOR] = queryCounter
        }
    }
}
