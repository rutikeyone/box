package com.box.view.screens.auth

import android.os.Bundle
import android.text.Editable
import android.view.View
import android.view.View.OnClickListener
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import com.box.R
import com.box.databinding.FragmentSignInBinding
import com.box.view.screens.base.BaseFragment
import com.box.view.utils.validateEmail
import com.box.view.utils.validatePassword
import com.box.view.viewmodel.signIn.SignInViewModel
import com.box.view.viewmodel.signIn.SignInViewState

class SignInFragment : BaseFragment(R.layout.fragment_sign_in) {
    override val viewModel: SignInViewModel by viewModels()

    private lateinit var binding: FragmentSignInBinding

    private val signUpOnPressedListener = OnClickListener {
        viewModel.navigateToSignUp()
    }

    private val signInOnPressedListener = OnClickListener {
        viewModel.signIn()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSignInBinding.bind(view)
        setupUI()
        observeState()
    }

    private fun observeState() = viewModel.state.observe(viewLifecycleOwner, ::changeUIWhenStateChanged)

    private fun changeUIWhenStateChanged(state: SignInViewState) {
        binding.emailEditText.error = validateEmail(state.email)
        binding.passwordEditText.error = validatePassword(state.password)
        binding.signInButton.isEnabled = state.isCanSignIn
    }

    private fun setupUI() {
        binding.signUpButton.setOnClickListener(signUpOnPressedListener)
        binding.signInButton.setOnClickListener(signInOnPressedListener)
        binding.emailEditText.doAfterTextChanged(::doEmailTextChanged)
        binding.passwordEditText.doAfterTextChanged(::doPasswordTextChanged)
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
}