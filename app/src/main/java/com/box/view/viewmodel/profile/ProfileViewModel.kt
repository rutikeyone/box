package com.box.view.viewmodel.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.box.data.repository.AccountsRepository
import com.box.view.screens.base.BaseViewModel
import com.box.view.screens.profile.ProfileFragmentDirections
import com.box.view.utils.Event
import com.box.view.utils.NavigationIntent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val accountsRepository: AccountsRepository,
): BaseViewModel() {

    fun navigateToEditProfile() {
        navigationEvent.value = Event(NavigationIntent.To(ProfileFragmentDirections.actionProfileFragmentToEditFragment()))
    }

    fun logout() = viewModelScope.launch {
        accountsRepository.logout()
    }
}