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
}