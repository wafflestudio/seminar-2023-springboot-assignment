package com.wafflestudio.seminar.spring2023.user.service

class SignUpUsernameConflictException : RuntimeException()

class SignUpBadUsernameException : RuntimeException()

class SignUpBadPasswordException : RuntimeException()

class SignInUserNotFoundException : RuntimeException()

class SignInInvalidPasswordException : RuntimeException()

class AuthenticateException : RuntimeException()
