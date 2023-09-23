package com.wafflestudio.seminar.spring2023.playlist.service

import org.springframework.stereotype.Service

// TODO: 캐시 TTL이 10초가 되도록 캐시 구현체를 구현 (추가 과제)
@Service
class PlaylistServiceCacheImpl(
    private val impl: PlaylistServiceImpl
) : PlaylistService {


// // ChatGPT 답변
//    import org.springframework.scheduling.TaskScheduler
//    import org.springframework.stereotype.Component
//    import java.time.Instant
//    import java.util.concurrent.ConcurrentHashMap
//    import java.util.concurrent.ScheduledFuture
//
//    @Component
//    class CacheWithTTL<K, V>(private val taskScheduler: TaskScheduler) {
//
//        private data class CacheItem<V>(val value: V, val timestamp: Instant)
//
//        private val ttl: Long = 10_000  // 10초 (밀리초 단위)
//        private val cacheMap: MutableMap<K, CacheItem<V>> = mutableMapOf()
//        private val cleanupTasks: ConcurrentHashMap<K, ScheduledFuture<*>> = ConcurrentHashMap()
//
//        fun put(key: K, value: V) {
//            cacheMap[key] = CacheItem(value, Instant.now())
//
//            // 이전 cleanUp 작업이 있으면 취소할 필요 없음. 각 항목마다 독립적으로 작업이 스케줄링됨.
//            val future = taskScheduler.schedule({ cleanUpForKey(key) }, Instant.now().plusMillis(ttl))
//            cleanupTasks[key] = future
//        }
//
//        fun get(key: K): V? {
//            val cacheItem = cacheMap[key] ?: return null
//
//            if (isExpired(cacheItem.timestamp)) {
//                cacheMap.remove(key)
//                cleanupTasks.remove(key)?.cancel(false)
//                return null
//            }
//
//            return cacheItem.value
//        }
//
//        private fun isExpired(timestamp: Instant): Boolean {
//            return timestamp.plusMillis(ttl).isBefore(Instant.now())
//        }
//
//        private fun cleanUpForKey(key: K) {
//            val cacheItem = cacheMap[key]
//            if (cacheItem != null && isExpired(cacheItem.timestamp)) {
//                cacheMap.remove(key)
//                cleanupTasks.remove(key)
//            }
//        }
//
//        fun remove(key: K) {
//            cacheMap.remove(key)
//            cleanupTasks.remove(key)?.cancel(false)
//        }
//
//        fun clear() {
//            cacheMap.clear()
//            cleanupTasks.values.forEach { it.cancel(false) }
//            cleanupTasks.clear()
//        }
//    }


    override fun getGroups(): List<PlaylistGroup> {
        TODO("Not yet implemented")
    }

    override fun get(id: Long): Playlist {
        TODO("Not yet implemented")
    }
}
