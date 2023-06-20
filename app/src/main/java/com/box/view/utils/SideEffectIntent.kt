package com.box.view.utils

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.navigation.NavDirections

data class SnackBarIntent(
    @StringRes val message: Int
)

data class DialogIntent(
    @StringRes val title: Int,
    @StringRes val message: Int,
    val setPositiveButton: Pair<Int, () -> Unit>,
)

data class PermissionIntent(
    val permission: String,
    val access: () -> Unit,
    val notAccess: () -> Unit,
    val notAccessForever: () -> Unit,
)
sealed class NavigationIntent {
    data class To(val direction: NavDirections): NavigationIntent()
    object Pop : NavigationIntent()
}

data class ToastIntent(
    @StringRes val message: Int,
    val duration: Int = Toast.LENGTH_LONG,
)