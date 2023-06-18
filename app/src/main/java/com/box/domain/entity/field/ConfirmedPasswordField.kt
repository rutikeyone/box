package com.box.domain.entity.field

enum class ConfirmedPasswordFieldValidationStatus {
    PURE, NOT_EQUAL_PASSWORD, VALID,
}

data class ConfirmedPassword(
    val value: String = "",
    val status: ConfirmedPasswordFieldValidationStatus = ConfirmedPasswordFieldValidationStatus.PURE,
) {
    val isValid: Boolean = status == ConfirmedPasswordFieldValidationStatus.VALID
    val isPure: Boolean = status == ConfirmedPasswordFieldValidationStatus.PURE

    companion object {
        fun validate(value: String, passwordValue: String): ConfirmedPasswordFieldValidationStatus {
            if(value != passwordValue) return ConfirmedPasswordFieldValidationStatus.NOT_EQUAL_PASSWORD
            return ConfirmedPasswordFieldValidationStatus.VALID
        }
    }
}