package com.wafflestudio.seminar.spring2023

import org.springframework.scheduling.TaskScheduler
import org.springframework.stereotype.Component
import java.time.Instant
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ScheduledFuture

@Component
class CacheTTLImpl (
    private val taskScheduler: TaskScheduler
        ) {
    private val cacheMap: ConcurrentHashMap<Any, Any> = ConcurrentHashMap()
    private val evictionManager: ConcurrentHashMap<Any, ScheduledFuture<*>> = ConcurrentHashMap()
    private val ttlMillis: Long = 10000L

    fun getFromCache(key: Any): Any? {
        return cacheMap[key]
    }

    fun addToCache(key: Any, value: Any) {
        val task: ScheduledFuture<*> = taskScheduler.schedule({
            cacheMap.remove(key)
        }, Instant.now().plusMillis(ttlMillis))
        evictionManager[key]?.cancel(true)
        evictionManager[key] = task
        cacheMap[key] = value
    }
}