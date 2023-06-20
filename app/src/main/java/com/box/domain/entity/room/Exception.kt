package com.box.domain.entity.room

open class AppException(): RuntimeException()

class AuthException(): AppException()

class StorageException(): AppException()