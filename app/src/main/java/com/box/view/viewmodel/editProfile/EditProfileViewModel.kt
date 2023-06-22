package com.box.view.viewmodel.editProfile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.box.data.repository.AccountsRepository
import com.box.view.screens.base.BaseViewModel
import com.box.view.utils.Event
import com.box.view.utils.NavigationIntent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val accountsRepository: AccountsRepository,
) : BaseViewModel() {
    private val _state = MutableLiveData<EditProfileViewState>()

    init {
        getAccount()
    }

    private fun getAccount() {
        TODO("Not yet implemented")
    }

    fun navigatePop() {
        navigationEvent.value = Event(NavigationIntent.Pop)
    }

    fun saveChanges() = viewModelScope.launch {

    }
}