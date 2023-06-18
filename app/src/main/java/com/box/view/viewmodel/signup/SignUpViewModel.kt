package com.box.view.viewmodel.signup

import androidx.lifecycle.MutableLiveData
import com.box.domain.entity.field.ConfirmedPassword
import com.box.domain.entity.field.EmailField
import com.box.domain.entity.field.PasswordField
import com.box.domain.entity.field.UsernameField
import com.box.view.screens.base.BaseViewModel
import com.box.view.utils.share

class SignUpViewModel: BaseViewModel() {
    private val _state = MutableLiveData(SignUpViewState())
    val state = _state.share()

    fun changeEmail(value: String) {
        val prevState = _state.value ?: return
        if(prevState.email.isPure && value.isEmpty()) return
        val status = EmailField.validate(value)
        val email = EmailField(value, status)
        _state.value = prevState.copy(email = email)
    }

    fun changePassword(value: String) {
        val prevState = _state.value ?: return
        if(prevState.password.isPure && value.isEmpty()) return
        val status = PasswordField.validate(value)
        val password = PasswordField(value, status)
        val confirmedStatus = ConfirmedPassword.validate(password.value, prevState.confirmedPassword.value)
        val confirmedPassword = prevState.confirmedPassword.copy(status = confirmedStatus)
        _state.value = prevState.copy(password = password, confirmedPassword = confirmedPassword)
    }

    fun changeUsername(value: String) {
        val prevState = _state.value ?: return
        if(prevState.username.isPure && value.isEmpty()) return
        val status = UsernameField.validate(value)
        val username = UsernameField(value, status)
        _state.value = prevState.copy(username = username)
    }

    fun changeConfirmedPassword(value: String) {
        val prevState = _state.value ?: return
        if(prevState.confirmedPassword.isPure && value.isEmpty()) return
        val status = ConfirmedPassword.validate(value, prevState.password.value)
        val confirmedPassword = ConfirmedPassword(value, status)
        _state.value = prevState.copy(confirmedPassword = confirmedPassword)
    }

    fun createAccount() {}
}