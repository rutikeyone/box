package com.box.view.screens.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.box.R
import com.box.databinding.FragmentProfileBinding
import com.box.view.screens.base.BaseFragment
import com.box.view.screens.base.BaseViewModel
import com.box.view.viewmodel.profile.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment() : BaseFragment(R.layout.fragment_profile) {
    private lateinit var binding: FragmentProfileBinding
    override val viewModel by viewModels<ProfileViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentProfileBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }

    private fun setupUI() {
        binding.editProfileButton.setOnClickListener { viewModel.navigateToEditProfile() }
        binding.logoutProfileButton.setOnClickListener { viewModel.logout() }
    }
}