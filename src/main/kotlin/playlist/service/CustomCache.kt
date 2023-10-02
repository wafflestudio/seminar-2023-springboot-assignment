package com.wafflestudio.seminar.spring2023.playlist.service

import java.util.concurrent.ConcurrentHashMap

class CustomCache<K,V> {

    private val cacheMap = ConcurrentHashMap<K,CacheEntry<V>>()
    private val ttl = 10000
    fun get(key: K): V?{
        val cacheEntry = cacheMap[key]
        if(cacheEntry!=null){
            if(cacheEntry.isExpired())
                remove(key)
            else
                return cacheEntry.value
        }
        return null
    }
    fun put(key: K, value: V) {
        cacheMap[key] = CacheEntry(value,System.currentTimeMillis()+ttl)
    }
    fun remove(key: K) {
        cacheMap.remove(key)
    }
    fun clear() {
        cacheMap.clear()
    }

    private data class CacheEntry<V>(val value: V, val expirationTime: Long){
        fun isExpired() : Boolean{
            return System.currentTimeMillis() >= expirationTime
        }
    }
}
