package com.box.view.viewmodel.dashboard

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.box.data.repository.BoxesRepository
import com.box.domain.entity.BoxEntity
import com.box.view.screens.base.BaseViewModel
import com.box.view.utils.share
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val boxesRepository: BoxesRepository
): BaseViewModel(), View.OnClickListener {
    private val _boxes = MutableLiveData<List<BoxEntity>>()
    val boxes = _boxes.share()

    init {
        getBoxes()
    }

    private fun getBoxes() = viewModelScope.launch {
        boxesRepository.getBoxesAndSettings(onlyActive = true).collect { list ->
            _boxes.value = list.map { it.box }
        }
    }

    override fun onClick(view: View) {
        val box = view.tag as BoxEntity
    }
}