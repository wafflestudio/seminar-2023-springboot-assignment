package com.wafflestudio.seminar.spring2023.common

import java.time.Instant

/**
 * 캐시 구현체. TTL 10초 후 만료되는 간단한 Lookup 용 캐시.
 */
class SimpleCache<K, V> {

    companion object {
        private const val TTL = 10
    }

    private val cacheMap: HashMap<K, Item> = HashMap()

    /**
     * 캐시 조회.
     * 
     * 캐시 Miss시 [block] 실행 값을 캐싱하고 리턴한다.
     */
    fun get(key: K, block: () -> V): V {
        val item = cacheMap[key]
        if (item != null && !item.isExpired()) {
            return item.value
        }
        val value = block()
        cacheMap[key] = Item(value)
        return value
    }

    /**
     * TTL 정보를 담고 있는 캐시 Value.
     */
    private inner class Item(val value: V) {

        private val createdAt = Instant.now().epochSecond

        /**
         * [TTL]이 지난 경우 True를 리턴.
         */
        fun isExpired(): Boolean = (Instant.now().epochSecond - createdAt) > TTL

    }

}