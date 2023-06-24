package com.box.view.screens.admin

import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.box.R
import com.box.databinding.TileTreeElementBinding

class AdminTileAdapter(
    private val listener: Listener,
) : RecyclerView.Adapter<AdminTileAdapter.Holder>(), View.OnClickListener {
    private var items: List<AdminTreeTile> = emptyList()

    @DrawableRes
    fun getExpansionIcon(tile: AdminTreeTile) = when(tile.expansionStatus) {
        ExpansionStatus.EXPANDED -> R.drawable.ic_minus
        ExpansionStatus.COLLAPSED -> R.drawable.ic_plus
        else -> R.drawable.ic_dot
    }

    override fun onClick(view: View) {
        val tile = view.tag as AdminTreeTile
        listener.onItemToggled(tile)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = TileTreeElementBinding.inflate(inflater, parent, false)
        binding.root.setOnClickListener(this)
        return Holder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val tile = items[position]
        holder.itemView.tag = tile
        with(holder.binding) {
            attributesTextView.text = prepareAttributesText(tile)
            expandCollapseIndicatorImageView.setImageResource(getExpansionIcon(tile))
            adjustStartOffset(tile, attributesTextView)
            holder.binding.root.isClickable = tile.expansionStatus != ExpansionStatus.NOT_EXPANDABLE
        }
    }

    private fun adjustStartOffset(tile: AdminTreeTile, attributesTextView: TextView) {
        val context = attributesTextView.context
        val spacePerLevel = context.resources.getDimensionPixelSize(R.dimen.dp_32)
        val totalSpace = (tile.level + 1) * spacePerLevel
        val layoutParams = attributesTextView.layoutParams as ViewGroup.MarginLayoutParams
        layoutParams.marginStart = totalSpace
        attributesTextView.layoutParams = layoutParams
    }

    private fun prepareAttributesText(tile: AdminTreeTile): CharSequence {
        val attributesText = tile.attributes.entries.joinToString("<br>") {
            if(it.value.isNotBlank()) {
                "<b>${it.key}</b>: ${it.value}"
            } else {
                "<b>${it.key}</b>"
            }
        }
        return Html.fromHtml(attributesText)
    }

    fun renderItems(items: List<AdminTreeTile>) {
        val diffCallback = AdminTreeTileDiffCallback(this.items, items)
        val result = DiffUtil.calculateDiff(diffCallback)
        this.items = items
        result.dispatchUpdatesTo(this)
    }

    class Holder(val binding: TileTreeElementBinding): RecyclerView.ViewHolder(binding.root)

    interface Listener {
        fun onItemToggled(item: AdminTreeTile)
    }


}