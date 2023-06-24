package com.box.view.viewmodel.tabs

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.box.data.repository.AccountsRepository
import com.box.view.screens.base.BaseViewModel
import com.box.view.utils.share
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TabsViewModel @Inject constructor(
    private val accountsRepository: AccountsRepository,
): BaseViewModel() {
    private val _userEmail = MutableLiveData<String?>(null)
    val userEmail = _userEmail.share()

    private val _showAdminTab = MutableLiveData<Boolean>()
    val showAdminTab = _showAdminTab.share()

    init {
        getAccount()
    }

    private fun getAccount() = viewModelScope.launch {
        accountsRepository.getAccount().collect {
            _showAdminTab.value = it?.isAdmin() == true 
            if(it != null) {
                _userEmail.value = it.email
            } else {
                _userEmail.value = null
            }
        }
    }
}