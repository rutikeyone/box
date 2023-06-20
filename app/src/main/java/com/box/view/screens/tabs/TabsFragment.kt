package com.box.view.screens.tabs

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.box.R
import com.box.databinding.TabsFragmentBinding
import com.box.view.screens.base.BaseFragment
class TabsFragment : BaseFragment(R.layout.tabs_fragment) {
    private lateinit var binding: TabsFragmentBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = TabsFragmentBinding.bind(view)
        setupNavController()
    }

    private fun setupNavController() {
        val navHost = childFragmentManager.findFragmentById(R.id.tabsContainer) as NavHostFragment
        val navController = navHost.navController
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController)
    }
}