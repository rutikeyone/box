package com.box.view.screens.base

import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.box.view.utils.DialogIntent
import com.box.view.utils.NavigationIntent
import com.box.view.utils.PermissionIntent
import com.box.view.utils.SnackBarIntent
import com.box.view.utils.ToastIntent
import com.box.view.utils.observeEvent
import com.google.android.material.snackbar.Snackbar

open class BaseFragment(@LayoutRes contentLayoutId: Int): Fragment(contentLayoutId) {
    protected open val viewModel by viewModels<BaseViewModel>()
    private val requestPermissionListener : ActivityResultLauncher<String> by lazy(LazyThreadSafetyMode.SYNCHRONIZED, ::createRequestPermission)

    private fun createRequestPermission(): ActivityResultLauncher<String>
        = registerForActivityResult(ActivityResultContracts.RequestPermission(), viewModel.permissionCallBackWrapperShare)

    override fun onCreate(savedInstanceState: Bundle?) {
        observeSideEffectIntents()
        super.onCreate(savedInstanceState)
    }

    private fun observeSideEffectIntents() {
        viewModel.showSnackBarShareEvent.observeEvent(this, ::createShowSnackBar)
        viewModel.showPermissionShareEvent.observeEvent(this, ::createPermission)
        viewModel.showDialogShareEvent.observeEvent(this, ::createDialog)
        viewModel.navigationShareEvent.observeEvent(this, ::executeNavigationEvent)
        viewModel.toastsShareEvent.observeEvent(this, ::createToast)
    }

    private fun createToast(toastIntent: ToastIntent) = Toast.makeText(requireContext(), toastIntent.message, toastIntent.duration).show()

    private fun executeNavigationEvent(navigationIntent: NavigationIntent) = findNavController().navigate(navigationIntent.direction)

    private fun createDialog(dialogIntent: DialogIntent) {
        AlertDialog.Builder(requireContext())
            .setTitle(dialogIntent.title)
            .setMessage(dialogIntent.message)
            .setPositiveButton(dialogIntent.setPositiveButton.first) {  _, _ ->
                dialogIntent.setPositiveButton.second()
            }
            .create()
            .show()
    }

    private fun createPermission(permissionIntent: PermissionIntent) = requestPermissionListener.launch(permissionIntent.permission)

    private fun createShowSnackBar(snackBarIntent: SnackBarIntent) {
        val message = getString(snackBarIntent.message)
        Snackbar.make(requireView(), message,  Snackbar.LENGTH_LONG).show()
    }
}