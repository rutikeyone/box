package com.box.view.screens.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.box.R
import com.box.databinding.ActivityMainBinding
import com.box.view.screens.auth.SignInFragmentDirections
import com.box.view.screens.splash.SplashFragmentDirections
import com.box.view.screens.tabs.TabsFragment
import com.box.view.screens.tabs.TabsFragmentDirections
import com.box.view.utils.HasScreenToolbar
import com.box.view.viewmodel.main.MainViewModel
import com.box.view.viewmodel.main.MainViewState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val viewModel by viewModels<MainViewModel>()

    private val rootNavController
        get() = ((supportFragmentManager.findFragmentById(R.id.fragmentContainer)) as NavHostFragment).navController
    private var currentNavController: NavController? = null

    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if(currentNavController != null && currentNavController?.popBackStack() == true) return
            isEnabled = false
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private val fragmentListener = object : FragmentManager.FragmentLifecycleCallbacks() {
        override fun onFragmentCreated(fragmentManager: FragmentManager, fragment: Fragment, savedInstanceState: Bundle?) {
            super.onFragmentCreated(fragmentManager, fragment, savedInstanceState)
            if(fragment is NavHostFragment) return
            onNavControllerActivated(fragment.findNavController())
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupNavigation()
        viewModel.state.observe(this, ::authenticationStateChanged)
    }

    private fun authenticationStateChanged(state: MainViewState) {
        when (state) {
            is MainViewState.Authenticated -> navigateByAuthenticatedState()
            MainViewState.NotAuthenticated -> navigateByNotAuthenticatedState()
            MainViewState.InPending -> setStartDestination()
        }
    }

    private fun setStartDestination() {
        val graph = rootNavController.navInflater.inflate(getMainNavigationGraphId())
        graph.setStartDestination(getSplashDestination())
        rootNavController.graph = graph
        currentNavController = rootNavController
    }

    private fun navigateByNotAuthenticatedState() {
        when(rootNavController.currentDestination?.id) {
            R.id.splashFragment -> rootNavController.navigate(SplashFragmentDirections.actionSplashFragmentToAuthGraph())
            R.id.homeFragment -> rootNavController.navigate(TabsFragmentDirections.actionGlobalAuthGraph())
        }
    }

    private fun navigateByAuthenticatedState() {
        when(rootNavController.currentDestination?.id) {
            R.id.splashFragment -> rootNavController.navigate(SplashFragmentDirections.actionSplashFragmentToHomeGraph())
            R.id.signInFragment -> rootNavController.navigate(SignInFragmentDirections.actionGlobalHomeGraph())
        }
    }

    override fun onDestroy() {
        supportFragmentManager.unregisterFragmentLifecycleCallbacks(fragmentListener)
        super.onDestroy()
    }

    private fun setupNavigation() {
        onNavControllerActivated(rootNavController)
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
        supportFragmentManager.registerFragmentLifecycleCallbacks(fragmentListener, true)
    }

    private fun onNavControllerActivated(navController: NavController) {
        if(this.currentNavController == navController) return
        this.currentNavController = navController
    }

    private fun getMainNavigationGraphId(): Int = R.navigation.main_graph

    private fun getSplashDestination(): Int = R.id.splashFragment
}