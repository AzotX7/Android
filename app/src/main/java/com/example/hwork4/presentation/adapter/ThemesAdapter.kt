package com.example.hwork4.presentation.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hwork4.databinding.ItemThemeBinding
import com.example.hwork4.domain.Theme
import com.example.hwork4.presentation.viewHolder.ThemeViewHolder
import com.example.hwork4.util.attachBinding

class ThemesAdapter(
    private val themes: List<Theme>,
    private val onClick: (position: Int) -> Unit,
) : RecyclerView.Adapter<ThemeViewHolder>() {
    override fun getItemCount(): Int = themes.size
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ThemeViewHolder {
        return ThemeViewHolder(
            parent.attachBinding(ItemThemeBinding::inflate),
            onClick
        )
    }

    override fun onBindViewHolder(holder: ThemeViewHolder, position: Int) {
        holder.bind(themes[position])
    }
}