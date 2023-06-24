package com.box.view.screens.dashboard

import android.os.Bundle
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.viewModels
import com.box.R
import com.box.databinding.FragmentDashboardBinding
import com.box.databinding.FragmentSettingsBinding
import com.box.domain.entity.BoxEntity
import com.box.view.screens.base.BaseFragment
import com.box.view.screens.base.BaseViewModel
import com.box.view.utils.viewBinding
import com.box.view.viewmodel.dashboard.DashboardViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardFragment : BaseFragment(R.layout.fragment_dashboard) {
    private val binding by viewBinding<FragmentDashboardBinding>()
    override val viewModel by viewModels<DashboardViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        clearBoxViews()
        viewModel.boxes.observe(viewLifecycleOwner) { renderBox(it) }
    }

    private fun renderBox(boxes: List<BoxEntity>) {
        clearBoxViews()
        if(boxes.isEmpty()) {
            binding.noBoxesTextView.visibility = View.VISIBLE
            binding.boxesContainer.visibility = View.GONE
        } else {
            binding.noBoxesTextView.visibility = View.GONE
            binding.boxesContainer.visibility = View.VISIBLE
            createBoxes(boxes)
        }
    }

    private fun createBoxes(boxes: List<BoxEntity>) {
        val with = resources.getDimensionPixelOffset(R.dimen.dp_140)
        val height = resources.getDimensionPixelOffset(R.dimen.dp_90)
        val generateIdentifiers = boxes.map { box ->
            val id = View.generateViewId()
            val dashboardTileView = DashboardTileView(requireContext())
            dashboardTileView.setBox(box)
            dashboardTileView.id = id
            dashboardTileView.tag = box
            dashboardTileView.setOnClickListener(viewModel)
            val params = ConstraintLayout.LayoutParams(with, height)
            binding.boxesContainer.addView(dashboardTileView, params)
            return@map id
        }.toIntArray()
        binding.flowView.referencedIds = generateIdentifiers
    }

    private fun clearBoxViews() {
        binding.boxesContainer.removeViews(1, binding.root.childCount - 1)
    }
}