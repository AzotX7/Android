package com.example.hwork4.data

import com.example.hwork4.R
import com.example.hwork4.domain.Theme

object ThemesRepository {
    val themes = listOf(
        Theme(R.color.red, R.style.Base_Theme_hWork4_Red),
        Theme(R.color.green, R.style.Base_Theme_hWork4_Green),
        Theme(R.color.blue, R.style.Base_Theme_hWork4_Blue)
    )
}