package com.box.view.viewmodel.settings

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.box.data.repository.BoxesRepository
import com.box.domain.entity.BoxAndSettingsEntity
import com.box.domain.entity.BoxEntity
import com.box.view.screens.base.BaseViewModel
import com.box.view.screens.settings.SettingsAdapter
import com.box.view.utils.share
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val boxesRepository: BoxesRepository,
): BaseViewModel(), SettingsAdapter.Listener {
    private val _boxSettings = MutableLiveData<List<BoxAndSettingsEntity>>()
    val boxSettings = _boxSettings.share()

    init {
        getBoxSettings()
    }

    private fun getBoxSettings() = viewModelScope.launch {
        boxesRepository.getBoxesAndSettings().collect {
            _boxSettings.value = it
        }
    }

    override fun enableBox(box: BoxEntity) {
        TODO("Not yet implemented")
    }

    override fun disableBox(box: BoxEntity) {
        TODO("Not yet implemented")
    }
}