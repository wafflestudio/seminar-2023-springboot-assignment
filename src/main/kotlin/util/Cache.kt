package com.wafflestudio.seminar.spring2023.util

import java.time.Instant

class Cache<K, V>(val ttl: Long) {
    private val cache = HashMap<K, CacheItem<V>>()
    operator fun get(key: K): V? {
        val item = cache[key] ?: return null
        return if (item.isExpired()) {
            remove(key)
            null
        } else item.value
    }

    operator fun set(key: K, value: V) {
        cache[key] = CacheItem(value, ttl)
    }

    private fun remove(key: K) {
        cache.remove(key)
    }

    data class CacheItem<V>(val value: V, val ttl: Long) {
        private val createdAt = Instant.now()

        fun isExpired(): Boolean {
            return Instant.now().isAfter(createdAt.plusSeconds(ttl))
        }
    }
}