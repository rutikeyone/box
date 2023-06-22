package com.box.view.viewmodel.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.box.data.repository.AccountsRepository
import com.box.databinding.FragmentProfileBinding
import com.box.domain.entity.AccountEntity
import com.box.view.screens.base.BaseViewModel
import com.box.view.screens.profile.ProfileFragmentDirections
import com.box.view.utils.Event
import com.box.view.utils.NavigationIntent
import com.box.view.utils.share
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val accountsRepository: AccountsRepository,
): BaseViewModel() {
    private val _accountState = MutableLiveData<AccountEntity?>(null)
    val accountState = _accountState.share()

    init {
        getAccount()
    }

    fun navigateToEditProfile() {
        navigationEvent.value = Event(NavigationIntent.To(ProfileFragmentDirections.actionProfileFragmentToEditFragment()))
    }

    private fun getAccount() = viewModelScope.launch {
        accountsRepository.getAccount().collect {
            if(it != null) {
                _accountState.value = it
            }
        }
    }

    fun logout() = viewModelScope.launch {
        accountsRepository.logout()
    }
}