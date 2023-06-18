package com.box.view.viewmodel.signIn

 import androidx.lifecycle.MutableLiveData
 import com.box.domain.entity.field.EmailField
 import com.box.domain.entity.field.PasswordField
 import com.box.view.screens.auth.SignInFragmentDirections
import com.box.view.screens.base.BaseViewModel
 import com.box.view.utils.Event
 import com.box.view.utils.MutableLiveEvent
 import com.box.view.utils.NavigationIntent
 import com.box.view.utils.publishEvent
 import com.box.view.utils.share

class SignInViewModel : BaseViewModel() {
    private val _state = MutableLiveData(SignInViewState())
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
        val email = PasswordField(value, status)
        _state.value = prevState.copy(password = email)
    }

    fun navigateToSignUp() {
        val navigationIntent = NavigationIntent(SignInFragmentDirections.actionSignInFragmentToSignUpFragment2())
        _navigationEvent.publishEvent(navigationIntent)
    }

    fun signIn() {}

}