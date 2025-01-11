package com.example.hwork4.presentation.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import com.example.hwork4.R
import com.example.hwork4.app
import com.example.hwork4.databinding.ActivityMainBinding
import com.example.hwork4.presentation.fragments.NotificationFragment
import com.example.hwork4.util.NavigationAction
import com.example.hwork4.util.ScreenTags
import com.example.hwork4.util.showToast


class MainActivity : BaseActivity() {
    private var binding: ActivityMainBinding? = null
    override val mainContainerId: Int = R.id.fragment_container_view

    companion object {
        const val FROM_NOTIFICATION_KEY = "FROM_NOTIFICATION"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app.theme?.let { theme ->
            setTheme(theme.themeResource)
        }
        if (intent.getBooleanExtra(FROM_NOTIFICATION_KEY, false)) {
            showToast(getString(R.string.launched_from_notification))
        }
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater).apply {
            setContentView(root)
        }
        if (savedInstanceState == null) {
            navigate(
                destination = NotificationFragment(),
                destinationTag = ScreenTags.NOTIFICATION_TAG,
                action = NavigationAction.ADD,
                isAddToBackStack = false
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}