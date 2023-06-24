package com.box.view.viewmodel.settings

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.box.R
import com.box.data.repository.BoxesRepository
import com.box.domain.entity.BoxAndSettingsEntity
import com.box.domain.entity.BoxEntity
import com.box.view.screens.base.BaseViewModel
import com.box.view.screens.settings.SettingsAdapter
import com.box.view.utils.ToastIntent
import com.box.view.utils.publishEvent
import com.box.view.utils.share
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
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
        viewModelScope.launch {
            try {
                boxesRepository.activateBox(box)
            } catch (e: Exception) {
                toastEvent.publishEvent(ToastIntent(R.string.an_error_occurred_while_performing_the_operation_try_again_later_message))
            }
        }
    }

    override fun disableBox(box: BoxEntity) {
        viewModelScope.launch {
            try {
                boxesRepository.deactivateBox(box)
            } catch (e: Exception) {
                toastEvent.publishEvent(ToastIntent(R.string.an_error_occurred_while_performing_the_operation_try_again_later_message))
            }
        }
    }
}