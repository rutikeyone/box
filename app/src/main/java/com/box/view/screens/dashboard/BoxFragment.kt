package com.box.view.screens.dashboard

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.box.R
import com.box.databinding.FragmentBoxBinding
import com.box.view.screens.base.BaseFragment
import com.box.view.screens.base.BaseViewModel
import com.box.view.utils.viewBinding
import com.box.view.viewmodel.box.BoxViewModel
import com.box.view.viewmodel.dashboard.DashboardViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BoxFragment : BaseFragment(R.layout.fragment_box) {
    private val args by navArgs<BoxFragmentArgs>()
    private val binding by viewBinding<FragmentBoxBinding>()
    override val viewModel by viewModels<BoxViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.root.setBackgroundColor(DashboardTileView.getBackgroundColor(getColorValue()))
        binding.boxTextView.text = getString(R.string.this_is_box, getColorName())
        binding.goBackButton.setOnClickListener { viewModel.navigateBack() }
    }

    private fun getColorValue() = args.box.colorValue

    private fun getColorName() = args.box.colorName
}
