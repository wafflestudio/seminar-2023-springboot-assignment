package com.wafflestudio.seminar.spring2023.util

sealed class CacheException : RuntimeException()

class CacheMissException : CacheException()