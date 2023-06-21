package com.box.view.screens.tabs

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.box.R
import com.box.databinding.TabsFragmentBinding
import com.box.view.screens.base.BaseFragment

class TabsFragment : BaseFragment(R.layout.tabs_fragment) {
    private lateinit var binding: TabsFragmentBinding

    private val topLevelDestinations = setOf(R.id.dashboard, R.id.settings, R.id.profile)
    private val topLevelFragmentIds = setOf(R.id.dashboardFragment, R.id.settingsFragment, R.id.profileFragment)
    private val isTheMainCurrentDestination: Boolean get() = topLevelFragmentIds.any {it == navController.currentDestination?.id }
    private val navHost: NavHostFragment get() = childFragmentManager.findFragmentById(R.id.tabsContainer) as NavHostFragment
    private val navController: NavController get() = navHost.navController

    private val onBackCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            isEnabled = false
            when (isTheMainCurrentDestination) {
                true -> requireActivity().finish()
                false -> requireActivity().onBackPressedDispatcher.onBackPressed()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        requireActivity().onBackPressedDispatcher.addCallback(onBackCallback)
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = TabsFragmentBinding.bind(view)
        setupNavController()
    }

    private fun setupNavController() {
        val appConfiguration = AppBarConfiguration(topLevelDestinations)
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController)
        binding.tabsToolbar.setupWithNavController(navController, appConfiguration)
    }
}

