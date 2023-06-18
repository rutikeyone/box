package com.box.domain.entity.field

enum class UsernameValidationStatus {
    PURE, INVALID, VALID
}

data class UsernameField(
    val value: String = "",
    val status: UsernameValidationStatus = UsernameValidationStatus.PURE
) {
    val isValid: Boolean = status == UsernameValidationStatus.VALID
    val isPure: Boolean = status == UsernameValidationStatus.PURE

    companion object {
        fun validate(value: String?): UsernameValidationStatus {
            if(value.isNullOrEmpty()) return UsernameValidationStatus.INVALID
            return UsernameValidationStatus.VALID
        }
    }
}