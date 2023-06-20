package com.box.domain.entity.room

import android.graphics.Color
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.box.domain.entity.BoxEntity

@Entity(
    tableName = "boxes"
)
data class BoxDbEntity(
    @PrimaryKey @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo(name = "color_name") val colorName: String,
    @ColumnInfo(name = "color_value") val colorValue: String,
) {
    fun toBox(): BoxEntity = BoxEntity(
        id = id,
        colorName = colorName,
        colorValue = Color.parseColor(colorValue),
    )
}