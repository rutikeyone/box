package com.box.view.viewmodel.main

import com.box.domain.entity.AccountEntity

sealed class MainViewState {
    object InPending: MainViewState()
    data class Authenticated(val account: AccountEntity): MainViewState()
    object NotAuthenticated : MainViewState()
}