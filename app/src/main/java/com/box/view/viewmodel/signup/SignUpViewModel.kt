package com.box.view.viewmodel.signup

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.box.R
import com.box.data.repository.AccountsRepository
import com.box.domain.entity.SignUpDataEntity
import com.box.domain.entity.field.ConfirmedPassword
import com.box.domain.entity.field.EmailField
import com.box.domain.entity.field.PasswordField
import com.box.domain.entity.field.UsernameField
import com.box.domain.entity.room.StorageException
import com.box.view.screens.base.BaseViewModel
import com.box.view.utils.Event
import com.box.view.utils.NavigationIntent
import com.box.view.utils.ToastIntent
import com.box.view.utils.share
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val accountsRepository: AccountsRepository,
): BaseViewModel() {
    private val _state = MutableLiveData(SignUpViewState())
    val state = _state.share()
    private val currentState: SignUpViewState
        get() = _state.value ?: throw IllegalStateException("State cannot be null")

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

    fun createAccount() = viewModelScope.launch{
        if(!currentState.isCanSignUp) return@launch;
        _state.value = currentState.copy(signUpInProgress = true)
        try {
            val data = SignUpDataEntity(
                username = currentState.username.value,
                email = currentState.email.value,
                password = currentState.password.value.toCharArray(),
                repeatPassword = currentState.confirmedPassword.value.toCharArray(),
            )
            accountsRepository.signUp(data)
            navigationEvent.value = Event(NavigationIntent.Pop)
        }
        catch (e: StorageException) {
            toastEvent.value = Event(ToastIntent(R.string.such_a_user_already_exists_message))
        }
        catch (e: Exception) {
            toastEvent.value = Event(ToastIntent(R.string.an_error_occurred_while_performing_the_operation_try_again_later_message))
        } finally {
            _state.value = currentState.copy(signUpInProgress = false)
        }
    }
}