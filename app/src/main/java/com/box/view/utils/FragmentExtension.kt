package com.box.view.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.box.R
import com.box.domain.entity.field.ConfirmedPassword
import com.box.domain.entity.field.ConfirmedPasswordFieldValidationStatus
import com.box.domain.entity.field.EmailField
import com.box.domain.entity.field.EmailValidationStatus
import com.box.domain.entity.field.PasswordField
import com.box.domain.entity.field.PasswordValidationStatus
import com.box.domain.entity.field.UsernameField
import com.box.domain.entity.field.UsernameValidationStatus
import com.box.view.screens.base.BaseFragment

fun BaseFragment.validateEmail(value: EmailField): String? = when(value.status) {
    EmailValidationStatus.EMPTY -> getString(R.string.empty_text_field)
    EmailValidationStatus.INVALID_EMAIL -> getString(R.string.incorrect_email)
    else -> null
}

fun BaseFragment.validatePassword(value: PasswordField): String? = when(value.status) {
    PasswordValidationStatus.EMPTY -> getString(R.string.empty_text_field)
    PasswordValidationStatus.INVALID -> getString(R.string.the_password_must_contain_more_than_eight_characters)
    else -> null
}

fun BaseFragment.validateUsername(value: UsernameField): String? = when(value.status){
    UsernameValidationStatus.INVALID -> getString(R.string.invalid_user_name)
    else -> null
}

fun BaseFragment.validateConfirmedPassword(value: ConfirmedPassword): String? = when(value.status) {
    ConfirmedPasswordFieldValidationStatus.NOT_EQUAL_PASSWORD -> getString(R.string.passwords_must_match)
    else -> null
}
fun Fragment.hideKeyboard() {
    val inputMethodService = view?.context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodService.hideSoftInputFromWindow(requireView().windowToken, 0)
}
