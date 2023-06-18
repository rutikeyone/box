package com.box.domain.entity.field

enum class PasswordValidationStatus {
    PURE, EMPTY, INVALID, VALID
}

data class PasswordField(
    val value: String = "",
    val status: PasswordValidationStatus = PasswordValidationStatus.PURE,
) {
    val isValid: Boolean = status == PasswordValidationStatus.VALID
    val isPure: Boolean = status == PasswordValidationStatus.PURE

    companion object {
        fun validate(value: String?): PasswordValidationStatus {
            if(value.isNullOrEmpty()) return PasswordValidationStatus.EMPTY
            else if(value.length < 8) return PasswordValidationStatus.INVALID
            return PasswordValidationStatus.VALID
        }
    }
}