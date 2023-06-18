package com.box.view.utils

import androidx.activity.result.ActivityResultCallback

class CallBackWrapper<T> : ActivityResultCallback<T> {
    var callback: ActivityResultCallback<T>? = null
    override fun onActivityResult(result: T) {
        callback?.onActivityResult(result)
        callback = null
    }
}