package com.box.view.screens.tabs

import com.box.R
import com.box.view.screens.base.BaseFragment
import com.box.view.utils.HasScreenToolbar

class DashboardFragment : BaseFragment(R.layout.fragment_dashboard), HasScreenToolbar {
    override fun getScreenTitle(): String = getString(R.string.dashboard_screen)

}