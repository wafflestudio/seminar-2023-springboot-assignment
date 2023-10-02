package com.wafflestudio.seminar.spring2023.common.util

import java.util.concurrent.ConcurrentHashMap

class CacheUtils<K, V>(
    private val TTL: Long
    )
{
    private val cache = ConcurrentHashMap<K, CacheEntry<V>>()

    fun get(key: K): V? {
        return if (cache[key] == null) {
            null
        } else if (cache[key]!!.isExpired()) {
            cache.remove(key)
            null
        } else {
            cache[key]!!.value
        }
    }

    fun put(key: K, value: V) {
        cache[key] = CacheEntry(value, System.currentTimeMillis() + TTL)
    }

    class CacheEntry<V>(
        val value: V,
        private val expireAt: Long
    ) {
        fun isExpired(): Boolean {
            return System.currentTimeMillis() > expireAt
        }
    }
}
