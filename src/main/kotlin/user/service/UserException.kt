package com.wafflestudio.seminar.spring2023.user.service

sealed class UserException : RuntimeException()

class SignUpUsernameConflictException : UserException()

class SignUpBadUsernameException : UserException()

class SignUpBadPasswordException : UserException()

class SignInUserNotFoundException : UserException()

class SignInInvalidPasswordException : UserException()

class AuthenticateException : UserException()

class UserNotFoundException : UserException()