package com.example.newsapp.ui.main_activity

import android.os.Build
import android.view.View
import androidx.core.view.isGone
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.newsapp.R
import com.example.newsapp.databinding.ActivityMainBinding
import com.example.newsapp.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity() : BaseActivity<MainActionBus, MainActivityViewModel, ActivityMainBinding>(
    ActivityMainBinding::inflate, MainActivityViewModel::class.java
) {

    override fun init() {
        setNotificationBarTransparent()
        setNavListeners()
    }

    override suspend fun onAction(action: MainActionBus) {
    }

    private fun setNavListeners() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController

        navController.addOnDestinationChangedListener { _, navDestination, _ ->
            binding.navigationView.isGone = navDestination.id == R.id.newFragment
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
    }

    fun setNavigationItemId(id: Int) {
        binding.navigationView.selectedItemId = id
    }
}