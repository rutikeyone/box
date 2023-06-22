package com.box.view.viewmodel.editProfile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.box.R
import com.box.data.repository.AccountsRepository
import com.box.domain.entity.field.UsernameField
import com.box.view.screens.base.BaseViewModel
import com.box.view.utils.Event
import com.box.view.utils.MutableLiveEvent
import com.box.view.utils.NavigationIntent
import com.box.view.utils.ToastIntent
import com.box.view.utils.publishEvent
import com.box.view.utils.share
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val accountsRepository: AccountsRepository,
) : BaseViewModel() {
    private val _state = MutableLiveData<EditProfileViewState>()
    val state = _state.share()
    private val currentState get() = _state.value

    private val _initialUsernameEvent = MutableLiveEvent<String>()
    val initialUsernameEvent = _initialUsernameEvent.share()

    init {
        getAccount()
    }

    fun changeUsername(value: String) {
        val state = currentState ?: return
        if(state.username.isPure && value.isEmpty()) return
        val status = UsernameField.validate(value)
        val username = UsernameField(value, status)
        _state.value = state.copy(username = username)
    }

    private fun getAccount() = viewModelScope.launch {
        val account = accountsRepository.getAccount()
            .filterNotNull()
            .first()
        val validateStatus = UsernameField.validate(account.username)
        val username = UsernameField(account.username, validateStatus)
        _initialUsernameEvent.publishEvent(account.username)
        _state.value = EditProfileViewState(account.username, username)

    }

    fun navigatePop() {
        navigationEvent.value = Event(NavigationIntent.Pop)
    }

    fun saveChanges() = viewModelScope.launch {
        val state = currentState ?: return@launch
        if(!state.isCanApplyChanges) return@launch
        _state.value = state.copy(saveChangesInProgress = true)
        try {
            accountsRepository.updateAccountUsername(state.username.value)
            navigationEvent.publishEvent(NavigationIntent.Pop)
        } catch (_: Exception) {
            toastEvent.value = Event(ToastIntent(R.string.an_error_occurred_while_performing_the_operation_try_again_later_message))
        } finally {
            _state.value = state.copy(saveChangesInProgress = false)
        }
    }
}