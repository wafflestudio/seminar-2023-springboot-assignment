package com.wafflestudio.seminar.spring2023.user

import java.lang.annotation.ElementType
import java.lang.annotation.RetentionPolicy

@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class AuthUser {

}