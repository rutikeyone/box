package com.box.view.screens.profile

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.box.R
import com.box.databinding.FragmentProfileBinding
import com.box.domain.entity.AccountEntity
import com.box.view.screens.base.BaseFragment
import com.box.view.screens.base.BaseViewModel
import com.box.view.viewmodel.profile.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Date

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
        observeState()
    }

    @SuppressLint("SimpleDateFormat")
    private fun observeState() {
        viewModel.accountState.observe(viewLifecycleOwner) { account ->
            binding.dataContainer.children.forEach { it.visibility = View.GONE }
            if (account?.email != null) {
                binding.emailContainer.visibility = View.VISIBLE
                binding.emailTextView.text = account.email
            }

            if(account?.username != null) {
                binding.usernameContainer.visibility = View.VISIBLE
                binding.usernameTextView.text = account.username
            }

            if(account != null && account.createdAt != AccountEntity.UNKNOWN_CREATED_AT) {
                val date = Date(account.createdAt)
                val dateFormat = SimpleDateFormat("dd/MM/yyyy")
                binding.createdAtContainer.visibility = View.VISIBLE
                binding.createdAtTextView.text = dateFormat.format(date)
            }
        }
    }

}