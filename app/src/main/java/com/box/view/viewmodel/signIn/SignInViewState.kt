package com.box.view.viewmodel.signIn

import com.box.domain.entity.field.EmailField
import com.box.domain.entity.field.PasswordField

data class SignInViewState(
    val email: EmailField = EmailField(),
    val password: PasswordField = PasswordField()
) {
    val isCanSignIn = email.isValid && password.isValid
}