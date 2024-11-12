package com.box.domain.entity

class SignUpDataEntity(
    val username: String,
    val email: String,
    val password: CharArray,
    val repeatPassword: CharArray
) {
    override fun hashCode(): Int {
        var result = username.hashCode()
        result = 31 * result + email.hashCode()
        result = 31 * result + password.hashCode()
        result = 31 * result + repeatPassword.hashCode()
        return result.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SignUpDataEntity

        if (username != other.username) return false
        if (email != other.email) return false
        if (!password.contentEquals(other.password)) return false
        if (!repeatPassword.contentEquals(other.repeatPassword)) return false

        return true
    }
}