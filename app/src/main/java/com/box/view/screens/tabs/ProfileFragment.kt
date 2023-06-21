package com.box.view.screens.tabs

import androidx.fragment.app.Fragment
import com.box.R
import com.box.view.utils.HasScreenToolbar

class ProfileFragment() : Fragment(R.layout.fragment_profile), HasScreenToolbar {
    override fun getScreenTitle(): String = getString(R.string.profile)
}