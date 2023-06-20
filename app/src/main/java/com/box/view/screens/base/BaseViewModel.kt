package com.box.view.screens.base

import androidx.lifecycle.ViewModel
import com.box.view.utils.CallBackWrapper
import com.box.view.utils.DialogIntent
import com.box.view.utils.MutableLiveEvent
import com.box.view.utils.NavigationIntent
import com.box.view.utils.PermissionIntent
import com.box.view.utils.SnackBarIntent
import com.box.view.utils.ToastIntent
import com.box.view.utils.share

open class BaseViewModel: ViewModel() {
    private val permissionCallbackWrapper = CallBackWrapper<Boolean>()
    val permissionCallBackWrapperShare = permissionCallbackWrapper

    private val _showSnackBarEvent = MutableLiveEvent<SnackBarIntent>()
    val showSnackBarShareEvent = _showSnackBarEvent.share()

    private val _showPermissionEvent = MutableLiveEvent<PermissionIntent>()
    val showPermissionShareEvent = _showPermissionEvent.share()

    private val _showDialogEvent = MutableLiveEvent<DialogIntent>()
    val showDialogShareEvent = _showDialogEvent.share()

    protected val navigationEvent = MutableLiveEvent<NavigationIntent>()
    val navigationShareEvent = navigationEvent.share()

    protected val toastEvent = MutableLiveEvent<ToastIntent>()
    val toastsShareEvent = toastEvent.share()
}