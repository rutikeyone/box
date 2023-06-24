package com.box.view.screens.settings

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.box.R
import com.box.domain.entity.BoxAndSettingsEntity
import com.box.domain.entity.BoxEntity

class SettingsAdapter(
    private val listener: Listener,
): RecyclerView.Adapter<SettingsAdapter.Holder>(), View.OnClickListener {
    private var settings: List<BoxAndSettingsEntity> = emptyList()

    override fun onClick(view: View?) {
        val checkBox = view as CheckBox
        val box = view.tag as BoxEntity
        if(checkBox.isChecked) {
            listener.enableBox(box)
        } else {
            listener.disableBox(box)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater = LayoutInflater.from(parent.context)
        val checkbox = inflater.inflate(R.layout.settings_tile, parent, false) as CheckBox
        checkbox.setOnClickListener(this)
        return Holder(checkbox)
    }

    override fun getItemCount(): Int = settings.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val setting = settings[position]
        val context = holder.itemView.context
        holder.checkbox.tag = setting.box

        if(holder.checkbox.isChecked != setting.isActive) {
            holder.checkbox.isChecked = setting.isActive
        }
        val colorName = setting.box.colorName
        holder.checkbox.text = context.getString(R.string.enable_checkbox, colorName)
    }

    fun renderSettings(settings: List<BoxAndSettingsEntity>) {
        val diffResult = DiffUtil.calculateDiff(BoxSettingsDiffCallback(this.settings, settings))
        this.settings = settings
        diffResult.dispatchUpdatesTo(this)
    }

    class Holder(val checkbox: CheckBox): RecyclerView.ViewHolder(checkbox)

    interface Listener {
        fun enableBox(box: BoxEntity)
        fun disableBox(box: BoxEntity)
    }

}

