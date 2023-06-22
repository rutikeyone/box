package com.box.view.screens.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.box.R
import com.box.databinding.FragmentEditProfileBinding
import com.box.view.screens.base.BaseFragment
import com.box.view.screens.base.BaseViewModel
import com.box.view.viewmodel.editProfile.EditProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditProfileFragment : BaseFragment(R.layout.fragment_edit_profile) {
    private lateinit var binding: FragmentEditProfileBinding

    override val viewModel by viewModels<EditProfileViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentEditProfileBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }

    private fun setupUI() {
        binding.cancelButton.setOnClickListener { viewModel.navigatePop() }
    }
}
