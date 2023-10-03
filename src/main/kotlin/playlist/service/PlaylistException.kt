package com.wafflestudio.seminar.spring2023.playlist.service

import com.wafflestudio.seminar.spring2023.user.service.UserException

sealed class PlaylistException : RuntimeException()

class PlaylistNotFoundException : PlaylistException()

class PlaylistAlreadyLikedException : PlaylistException()

class PlaylistNeverLikedException : PlaylistException()

