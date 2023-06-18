package com.box.view.screens.base

import androidx.lifecycle.ViewModel
import com.box.view.utils.CallBackWrapper
import com.box.view.utils.DialogIntent
import com.box.view.utils.MutableLiveEvent
import com.box.view.utils.NavigationIntent
import com.box.view.utils.PermissionIntent
import com.box.view.utils.SnackBarIntent
import com.box.view.utils.share

open class BaseViewModel: ViewModel() {
    private val _permissionCallbackWrapper = CallBackWrapper<Boolean>()
    val permissionCallBackWrapper
        get() = _permissionCallbackWrapper

    private val _showSnackBarEvent = MutableLiveEvent<SnackBarIntent>()
    val showSnackBarEvent = _showSnackBarEvent.share()

    private val _showPermissionEvent = MutableLiveEvent<PermissionIntent>()
    val showPermissionEvent = _showPermissionEvent.share()

    private val _showDialogEvent = MutableLiveEvent<DialogIntent>()
    val showDialogEvent = _showDialogEvent.share()

    protected val _navigationEvent = MutableLiveEvent<NavigationIntent>()
    val navigationEvent = _navigationEvent.share()
}