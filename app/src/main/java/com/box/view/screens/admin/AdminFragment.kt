package com.box.view.screens.admin

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.box.R
import com.box.databinding.FragmentAdminBinding
import com.box.databinding.FragmentDashboardBinding
import com.box.view.screens.base.BaseFragment
import com.box.view.screens.base.BaseViewModel
import com.box.view.utils.ContextResources
import com.box.view.utils.viewBinding
import com.box.view.utils.viewModelCreator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AdminFragment : BaseFragment(R.layout.fragment_admin) {
    @Inject
    lateinit var factory: AdminViewModel.Factory
    override val viewModel: AdminViewModel by viewModelCreator { factory.create(ContextResources(requireContext())) }

    private val binding by viewBinding<FragmentAdminBinding>()
    private lateinit var adapter: AdminTileAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}