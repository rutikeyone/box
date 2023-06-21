package com.box.view.screens.tabs

import com.box.R
import com.box.view.screens.base.BaseFragment
import com.box.view.utils.HasScreenToolbar


class SettingsFragment : BaseFragment(R.layout.fragment_settings), HasScreenToolbar {
    override fun getScreenTitle(): String = getString(R.string.settings_screen)

}
