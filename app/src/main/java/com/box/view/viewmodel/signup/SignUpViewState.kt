package com.box.view.viewmodel.signup

import com.box.domain.entity.field.ConfirmedPassword
import com.box.domain.entity.field.EmailField
import com.box.domain.entity.field.PasswordField
import com.box.domain.entity.field.UsernameField

data class SignUpViewState(
    val email: EmailField = EmailField(),
    val username: UsernameField = UsernameField(),
    val password: PasswordField = PasswordField(),
    val confirmedPassword: ConfirmedPassword = ConfirmedPassword(),
    val signUpInProgress: Boolean = false,
) {
    val isCanSignUp = email.isValid
            && password.isValid
            && username.isValid
            && confirmedPassword.isValid
            && !signUpInProgress

    val enableView = !signUpInProgress
}