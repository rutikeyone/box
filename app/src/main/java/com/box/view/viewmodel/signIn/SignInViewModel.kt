package com.box.view.viewmodel.signIn

 import android.util.Log
 import androidx.lifecycle.MutableLiveData
 import androidx.lifecycle.viewModelScope
 import com.box.R
 import com.box.data.repository.AccountsRepository
 import com.box.domain.entity.field.EmailField
 import com.box.domain.entity.field.PasswordField
 import com.box.domain.entity.room.AuthException
 import com.box.view.screens.auth.SignInFragmentDirections
import com.box.view.screens.base.BaseViewModel
 import com.box.view.utils.Event
 import com.box.view.utils.NavigationIntent
 import com.box.view.utils.ToastIntent
 import com.box.view.utils.publishEvent
 import com.box.view.utils.share
 import dagger.hilt.android.lifecycle.HiltViewModel
 import kotlinx.coroutines.launch
 import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val accountsRepository: AccountsRepository,
) : BaseViewModel() {
    private val _state = MutableLiveData(SignInViewState())
    val state = _state.share()
    private val currentState
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
        _state.value = prevState.copy(password = password)
    }

    fun navigateToSignUp() {
        val navigationIntent = NavigationIntent.To(SignInFragmentDirections.actionSignInFragmentToSignUpFragment2())
        navigationEvent.publishEvent(navigationIntent)
    }

     fun signIn() = viewModelScope.launch {
         if(!currentState.isCanSignIn) return@launch;
        _state.value = currentState.copy(signInInProgress = true)
        try {
            accountsRepository.signIn(currentState.email.value, currentState.password.value.toCharArray())
        } catch (e: AuthException) {
          toastEvent.value = Event(ToastIntent(R.string.invalid_email_or_password_message))
        } catch (e: Exception) {
            toastEvent.value = Event(ToastIntent(R.string.an_error_occurred_while_performing_the_operation_try_again_later_message))
        }
        finally {
            _state.value = currentState.copy(signInInProgress = false)
        }
     }

}