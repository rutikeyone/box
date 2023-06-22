package com.box.view.screens.tabs

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.box.R
import com.box.databinding.TabsFragmentBinding
import com.box.view.screens.base.BaseFragment
import com.box.view.screens.main.MainActivity
import com.box.view.viewmodel.tabs.TabsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TabsFragment : BaseFragment(R.layout.tabs_fragment) {
    override val viewModel by viewModels<TabsViewModel>()
    private lateinit var binding: TabsFragmentBinding
    private val navHost: NavHostFragment get() = childFragmentManager.findFragmentById(R.id.tabsContainer) as NavHostFragment
    private val navController: NavController get() = navHost.navController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = TabsFragmentBinding.bind(view)
        setupNavController()
        observeState()
    }

    private fun observeState() {
        viewModel.userEmail.observe(viewLifecycleOwner, ::changeUserEmailToolbar)
    }

    private fun changeUserEmailToolbar(email: String?) {
        binding.emailTextView.visibility = if(email != null) View.VISIBLE else View.GONE
        binding.emailTextView.text = email ?: ""
    }

    private fun setupNavController() {
        val appConfiguration = AppBarConfiguration(MainActivity.tabsTopLevelFragment)
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController)
        binding.tabsToolbar.setupWithNavController(navController, appConfiguration)
    }
}

