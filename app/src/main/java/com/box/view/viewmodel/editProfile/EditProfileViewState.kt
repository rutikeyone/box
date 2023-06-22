package com.box.view.viewmodel.editProfile

import com.box.domain.entity.field.UsernameField

data class EditProfileViewState(
    val defaultUsername: String,
    val username: UsernameField = UsernameField(),
    val saveChangesInProgress: Boolean = false,
) {
    val isCanApplyChanges = username.isValid && username.value != defaultUsername && !saveChangesInProgress
}