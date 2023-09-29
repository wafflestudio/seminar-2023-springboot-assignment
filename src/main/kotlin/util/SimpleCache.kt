package com.wafflestudio.seminar.spring2023.util

import java.time.Instant

class SimpleCache<K, V>(private val ttl:Long) {
    private val cache = HashMap<K, CacheItem<V>>()

    fun get(key: K): V? {
        val item = cache[key] ?: return null
        return if (item.isExpired()) {
            cache.remove(key)
            null
        } else {
            item.value
        }
    }

    fun put(key: K, value: V) {
        cache[key] = CacheItem(value, ttl)
    }

    private data class CacheItem<V>(val value: V, val ttl: Long) {
        private val creationTime = Instant.now()

        fun isExpired(): Boolean {
            return Instant.now().isAfter(creationTime.plusSeconds(ttl))
        }
    }
}