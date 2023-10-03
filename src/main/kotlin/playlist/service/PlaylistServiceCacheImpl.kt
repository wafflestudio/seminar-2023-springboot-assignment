package com.wafflestudio.seminar.spring2023.playlist.service

import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Service
import java.time.Instant

//이 캐시 레이어를 두는 방식에 대해서는 추후에 추가 공부 필요
class NormalCache<Key, Value>(private val ttl:Long) { //캐시 구현체 클래스를 따로 정의 -> 만들고 저장하는 등의 기능을 넣는다
    //Key는 cache의 key, Value는 캐시 레이어에 넣고 싶은 값의 종류를 의미(플레이 리스트 등등)
    private data class CacheRecord<Value>(val value: Value, val ttl: Long) { //캐시의 생성과 만료에 대한 내용 정의
        private val creationTime = Instant.now() //딱 캐시 생성 순간을 기록
        fun isExpired(): Boolean {return Instant.now().isAfter(creationTime.plusSeconds(ttl))}
        //지금 이 순간이 creationTime으로부터 ttl만큼 지나면 캐시가 만료되었다고 알려주는 함수
    }

    private val cache = HashMap<Key, CacheRecord<Value>>() //HashMap 사용해서 cache 덩어리 생성

    fun get(key: Key): Value? { //주어진 키를 바탕으로 캐시 레이어에서 캐시값을 찾아주는 함수 구현
        val item = cache[key] ?: return null //없으면 널 반환
        return if (item.isExpired()) { cache.remove(key) //만약 캐시 유효기간이 지나서 만료되었으면 cache 덩어리에서 해당 값(키) 지우고 널 반환
            null
        } else { item.value } //만료 아니라면 찾고자 하는 캐시 값 반환
    }

    fun put(key: Key, value: Value) {
        cache[key] = CacheRecord(value, ttl) //캐시 덩어리에 key를 기준으로 그 값과 ttl을 함께 넣어주는 함수
    }
}

// TODO: 캐시 TTL이 10초가 되도록 캐시 구현체를 구현 (추가 과제)
@Service
class PlaylistServiceCacheImpl(
    private val impl: PlaylistServiceImpl, //기존 playlistservice에 캐시 레이어에 저장하고 거기서 뽑아내는 기능을 추가한다(상속)
) : PlaylistService {
    //플레이리스트 그룹과 플레이리스트에 대해 각각 ttl이 10초가 되는 캐시 구현체 생성
    private val groupCache = NormalCache<Unit, List<PlaylistGroup>>(10)
    private val playlistCache = NormalCache<Long, Playlist>(10)
    override fun getGroups(): List<PlaylistGroup> {
        return groupCache.get(Unit) ?: run {
            //getgroup 명령시에 groupcache에 들어있는게 있으면 여기서 빼내주고, 없으면 캐시 레이어에 구현체의 형태로 받은 데이터를 넣어준다
            val data = impl.getGroups()
            groupCache.put(Unit, data)
            data
        }
    }

    override fun get(id: Long): Playlist {
        return playlistCache.get(id) ?: run {
            //getg 명령시에 playlistcache에 들어있는게 있으면 여기서 빼내주고, 없으면 캐시 레이어에 구현체의 형태로 받은 데이터를 넣어준다
            val data = impl.get(id)
            playlistCache.put(id, data)
            data
        }
    }
}



