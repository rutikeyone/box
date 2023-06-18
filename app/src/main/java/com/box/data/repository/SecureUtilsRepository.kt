package com.box.data.repository

interface SecureUtilsRepository {
    fun generateSalt(): ByteArray

    fun passwordToHash(password: CharArray, salt: ByteArray): ByteArray

    fun bytesToString(bytes: ByteArray): String

    fun stringToBytes(value: String): ByteArray
}