package com.example.hwork4.domain

import androidx.annotation.ColorRes
import androidx.annotation.StyleRes

data class Theme(
    @ColorRes val color: Int,
    @StyleRes val themeResource: Int
)