package com.box.view.screens.auth

import android.os.Bundle
import android.text.Editable
import android.view.View
import android.view.View.OnClickListener
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import com.box.R
import com.box.databinding.FragmentSignUpBinding
import com.box.view.screens.base.BaseFragment
import com.box.view.utils.validateConfirmedPassword
import com.box.view.utils.validateEmail
import com.box.view.utils.validatePassword
import com.box.view.utils.validateUsername
import com.box.view.viewmodel.signup.SignUpViewModel
import com.box.view.viewmodel.signup.SignUpViewState


class SignUpFragment : BaseFragment(R.layout.fragment_sign_up) {
    override val viewModel by viewModels<SignUpViewModel>()

    private lateinit var binding: FragmentSignUpBinding

    private val createAccountOnPressedListener = OnClickListener { viewModel.createAccount() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSignUpBinding.bind(view)
        setupUI()
        observeState()
    }

    private fun observeState() = viewModel.state.observe(viewLifecycleOwner, ::changeUIWhenStateChanged)

    private fun changeUIWhenStateChanged(state: SignUpViewState) {
        binding.emailEditText.error = validateEmail(state.email)
        binding.passwordEditText.error = validatePassword(state.password)
        binding.usernameEditText.error = validateUsername(state.username)
        binding.repeatPasswordEditText.error = validateConfirmedPassword(state.confirmedPassword)
        binding.createAccountButton.isEnabled = state.isCanSignUp
    }

    private fun setupUI() {
        binding.createAccountButton.setOnClickListener(createAccountOnPressedListener)
        binding.emailEditText.doAfterTextChanged(::doEmailTextChanged)
        binding.passwordEditText.doAfterTextChanged(::doPasswordTextChanged)
        binding.usernameEditText.doAfterTextChanged(::doUsernameTextChanged)
        binding.repeatPasswordEditText.doAfterTextChanged(::doRepeatTextChanged)
    }

    private fun doRepeatTextChanged(text: Editable?) {
        if(text == null) return
        val value = text.toString()
        viewModel.changeConfirmedPassword(value)
    }

    private fun doPasswordTextChanged(text: Editable?) {
        if(text == null) return
        val value = text.toString()
        viewModel.changePassword(value)
    }

    private fun doEmailTextChanged(text: Editable?) {
        if(text == null) return
        val value = text.toString()
        viewModel.changeEmail(value)
    }

    private fun doUsernameTextChanged(text: Editable?) {
        if(text == null) return
        val value = text.toString()
        viewModel.changeUsername(value)
    }
}