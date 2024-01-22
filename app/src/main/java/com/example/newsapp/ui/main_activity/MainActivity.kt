package com.example.newsapp.ui.main_activity

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isGone
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.newsapp.R
import com.example.newsapp.data.local.worker.SaveCountryWorker
import com.example.newsapp.databinding.ActivityMainBinding
import com.example.newsapp.utils.CountryUtils
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity() : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setNotificationBarTransparent()
        setNavListeners()
    }

    private fun setNavListeners() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController

        navController.addOnDestinationChangedListener { _, navDestination, _ ->
            binding.navigationView.isGone =
                (navDestination.id == R.id.newFragment) || navDestination.id == R.id.splashScreenFragment
        }
        binding.navigationView.setupWithNavController(navController)
    }

    private fun setNotificationBarTransparent() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false)
        } else {
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
        binding.root.setNavBarHeight(this.window)
    }

    private fun View.setNavBarHeight(window: Window) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        ViewCompat.setOnApplyWindowInsetsListener(this) { _, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
            val param = this.layoutParams as ViewGroup.MarginLayoutParams
            param.setMargins(0, 0, 0, insets.bottom)
            this.layoutParams = param

            WindowInsetsCompat.CONSUMED
        }
    }

    fun setNavigationItemId(id: Int) {
        binding.navigationView.selectedItemId = id
    }
}