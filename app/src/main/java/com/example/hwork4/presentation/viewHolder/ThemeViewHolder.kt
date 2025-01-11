package com.example.hwork4.presentation.viewHolder

import androidx.recyclerview.widget.RecyclerView
import com.example.hwork4.domain.Theme
import com.example.hwork4.databinding.ItemThemeBinding


class ThemeViewHolder(
    private val binding: ItemThemeBinding,
    private val onClick: (position: Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.colorView.setOnClickListener {
            onClick(adapterPosition)
        }
    }
    fun bind(theme: Theme) {
        binding.colorView.setBackgroundResource(theme.color)
    }
}