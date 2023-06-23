package com.box.view.screens.settings

import androidx.recyclerview.widget.DiffUtil
import com.box.domain.entity.BoxAndSettingsEntity

class BoxSettingsDiffCallback(
    private val oldList: List<BoxAndSettingsEntity>,
    private val newList: List<BoxAndSettingsEntity>,
): DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].box.id == newList[newItemPosition].box.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

}