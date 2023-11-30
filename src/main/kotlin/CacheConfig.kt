package com.wafflestudio.seminar.spring2023

import com.github.benmanes.caffeine.cache.Caffeine
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.Duration

@EnableConfigurationProperties(CacheProperties::class)
@Configuration
class CacheConfig(
    private val cacheProperties: CacheProperties,
) {

    @Bean
    fun cache(): Caffeine<Any, Any> {
        return Caffeine.newBuilder()
            .maximumSize(cacheProperties.size)
            .expireAfterWrite(cacheProperties.ttl)
    }
}

@ConfigurationProperties("cache")
data class CacheProperties(
    val ttl: Duration,
    val size: Long,
)
