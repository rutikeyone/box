package com.box.domain.repository

import android.util.Base64
import com.box.data.repository.SecureUtilsRepository
import java.security.SecureRandom
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec

class DefaultSecureUtilsRepository: SecureUtilsRepository {
    private val secureRandom = SecureRandom()

    override fun generateSalt(): ByteArray {
        val salt = ByteArray(50 + secureRandom.nextInt(50))
        secureRandom.nextBytes(salt)
        return salt
    }

    override fun passwordToHash(password: CharArray, salt: ByteArray): ByteArray {
        val iterations = 1000
        val keyLength = 160
        val keySpec = PBEKeySpec(password, salt, iterations, keyLength)
        val keyFactory = SecretKeyFactory.getInstance("PBKDF2withHmacSHA1")
        return keyFactory.generateSecret(keySpec).encoded
    }

    override fun bytesToString(bytes: ByteArray): String {
        return Base64.encodeToString(bytes, Base64.DEFAULT)
    }

    override fun stringToBytes(value: String): ByteArray {
        return Base64.decode(value, Base64.DEFAULT)
    }
}