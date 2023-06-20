package com.box.view.viewmodel.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.box.data.repository.AccountsRepository
import com.box.view.utils.share
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val accountsRepository: AccountsRepository,
): ViewModel() {
    private val _state = MutableLiveData<MainViewState>(MainViewState.InPending)
    val state = _state.share()

    init {
        getAccount()
    }

    private fun getAccount() = viewModelScope.launch {
        delay(1000)
        accountsRepository.getAccount().collect {
            if(it != null) {
                _state.value = MainViewState.Authenticated(it)
            } else {
                _state.value = MainViewState.NotAuthenticated
            }
        }
    }
}