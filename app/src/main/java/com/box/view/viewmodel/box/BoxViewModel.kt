package com.box.view.viewmodel.box

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.navArgument
import com.box.domain.entity.BoxEntity
import com.box.view.screens.base.BaseViewModel
import com.box.view.utils.NavigationIntent
import com.box.view.utils.publishEvent
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BoxViewModel @Inject constructor(): BaseViewModel() {

  fun navigateBack() = navigationEvent.publishEvent(NavigationIntent.Pop)

}