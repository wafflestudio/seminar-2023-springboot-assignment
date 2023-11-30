package com.wafflestudio.seminar.spring2023.customplaylist.service

sealed class CustomPlaylistException : RuntimeException()

class CustomPlaylistNotFoundException : CustomPlaylistException()

class SongNotFoundException : CustomPlaylistException()
