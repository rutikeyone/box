package com.box.view.viewmodel.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.box.data.repository.AccountsRepository
import com.box.domain.repository.RoomAccountsRepository
import com.box.view.utils.share
import kotlinx.coroutines.launch

class MainViewModel(
    private val accountsRepository: AccountsRepository = RoomAccountsRepository()
): ViewModel() {
    private val _state = MutableLiveData<MainViewState>(MainViewState.InPending)
    val state = _state.share()


    init {
        getAccount()
    }

    private fun getAccount() = viewModelScope.launch {
        val account = accountsRepository.getAccount()
        if(account != null) {
            _state.value = MainViewState.Authenticated(account)
        } else {
            _state.value = MainViewState.NotAuthenticated
        }
    }
}