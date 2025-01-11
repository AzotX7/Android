package com.example.hwork4.domain

import android.app.NotificationManager
import androidx.annotation.StringRes
import com.example.hwork4.R

enum class NotificationPriority(@StringRes val title: Int, val androidPriority: Int) {
    MAX(R.string.max_importance, NotificationManager.IMPORTANCE_MAX),
    HIGH(R.string.high_importance, NotificationManager.IMPORTANCE_HIGH),
    DEFAULT(R.string.default_importance, NotificationManager.IMPORTANCE_DEFAULT),
    LOW(R.string.low_importance, NotificationManager.IMPORTANCE_LOW),
    MIN(R.string.min_importance, NotificationManager.IMPORTANCE_MIN)
}