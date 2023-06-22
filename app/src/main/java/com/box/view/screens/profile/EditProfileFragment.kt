package com.box.view.screens.profile

import android.os.Bundle
import android.text.Editable
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import com.box.R
import com.box.databinding.FragmentEditProfileBinding
import com.box.domain.entity.field.UsernameField
import com.box.view.screens.base.BaseFragment
import com.box.view.utils.hideKeyboard
import com.box.view.utils.observeEvent
import com.box.view.utils.validateUsername
import com.box.view.viewmodel.editProfile.EditProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditProfileFragment : BaseFragment(R.layout.fragment_edit_profile) {
    private lateinit var binding: FragmentEditProfileBinding
    private val saveButtonOnPressed = View.OnClickListener {
        hideKeyboard()
        viewModel.saveChanges()
    }

    override val viewModel by viewModels<EditProfileViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentEditProfileBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }

    private fun setupUI() {
        binding.cancelButton.setOnClickListener { viewModel.navigatePop() }
        binding.saveButton.setOnClickListener(saveButtonOnPressed)
        binding.usernameEditText.doAfterTextChanged(::doUsernameTextChanged)
        observeInitialUsernameEvent()
        observeState()
    }

    private fun doUsernameTextChanged(text: Editable?) {
        if(text == null) return
        val value = text.toString()
        viewModel.changeUsername(value)
    }

    private fun observeState() = viewModel.state.observe(viewLifecycleOwner) {
        binding.usernameEditText.error = validateUsername(it.username)
        binding.progressBar.visibility = if(it.saveChangesInProgress) View.VISIBLE else View.GONE
        binding.usernameTextInput.isEnabled = !it.saveChangesInProgress
        binding.saveButton.isEnabled = it.isCanApplyChanges
    }

    private fun observeInitialUsernameEvent() = viewModel.initialUsernameEvent.observeEvent(viewLifecycleOwner) {
        binding.usernameEditText.setText(it)
    }
}
