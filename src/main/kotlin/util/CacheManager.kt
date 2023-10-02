package com.wafflestudio.seminar.spring2023.util

import java.util.concurrent.ConcurrentHashMap

// ttl은 초 단위
class CacheManager<K, V>(private val ttl:Long) {
    private val cacheMap = ConcurrentHashMap<K, Cache<V>>()

    fun get(id:K):V {
        if (isCacheMiss(id)) {
            throw CacheMissException()
        }
        return cacheMap.getValue(id).data
    }

    fun put(id:K, data:V) {
        val now = System.currentTimeMillis()
        val cache = Cache(
            data = data,
            expireTime = now + ttl * 1_000
        )
        cacheMap[id] = cache
    }

    fun isCacheMiss(id:K):Boolean {
        if (!cacheMap.containsKey(id)) {
            return true
        }
        val now = System.currentTimeMillis()
        val expireTime = cacheMap.getValue(id).expireTime
        return expireTime <= now
    }
}

data class Cache<T>(val data:T, val expireTime:Long) {
}