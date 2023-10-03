package com.wafflestudio.seminar.spring2023.song.service

import org.springframework.stereotype.Service

@Service
class SongServiceImpl : SongService {

    override fun search(keyword: String): List<Song> { //곡 검색 기능
        TODO()
        //제한 조건
        //제목에 키워드를 포함한 곡 검색, 제목 길이가 짧은 순으로 정렬
        //제목에 키워드를 포함한 곡 검색, 제목 길이가 짧은 순으로 정렬, 쿼리 횟수는 1개로 제한
    }

    override fun searchAlbum(keyword: String): List<Album> { //앨범 검색 기능
        TODO()
        //제한 조건
        //제목에 키워드를 포함한 앨범 검색, 제목 길이가 짧은 순으로 정렬
        //제목에 키워드를 포함한 앨범 검색, 제목 길이가 짧은 순으로 정렬, 쿼리 횟수는 1개로 제한
    }
}
