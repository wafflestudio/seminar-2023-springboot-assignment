package com.wafflestudio.seminar.spring2023.playlist.service

sealed class PlaylistException : RuntimeException()

class PlaylistNotFoundException : PlaylistException()

class PlaylistAlreadyLikedException : PlaylistException()

class PlaylistNeverLikedException : PlaylistException()
