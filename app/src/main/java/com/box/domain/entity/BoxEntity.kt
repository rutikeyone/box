package com.box.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BoxEntity(
    val id: Long,
    val colorName: String,
    val colorValue: Int,
): Parcelable