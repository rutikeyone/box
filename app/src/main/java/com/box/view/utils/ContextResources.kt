package com.box.view.utils

import android.content.Context
import androidx.annotation.StringRes


interface Resources {
    fun getString(@StringRes stringRes: Int): String
}

class ContextResources(
    private val context: Context
) : Resources {

    override fun getString(stringRes: Int): String {
        return context.getString(stringRes)
    }

}