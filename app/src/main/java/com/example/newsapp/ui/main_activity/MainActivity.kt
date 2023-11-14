package com.example.newsapp.ui.main_activity

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.example.newsapp.R
import com.example.newsapp.databinding.ActivityMainBinding
import com.example.newsapp.domain.model.enums.FragmentAndIds
import dagger.hilt.android.AndroidEntryPoint


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
            binding.navigationView.isGone = navDestination.id == R.id.newFragment
        }

        binding.navigationView.setOnItemSelectedListener {
            navController.navigate(it.itemId)
            return@setOnItemSelectedListener true
        }
    }

    private fun setNotificationBarTransparent() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false)
        } else {
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
    }

    fun setSelectedFragment(fragment: Fragment) {
        val myFragmentId = fragment.toFragmentId()
        myFragmentId?.let {
            if (binding.navigationView.selectedItemId == it) return
            binding.navigationView.selectedItemId = it
        }
    }

    private fun Fragment.toFragmentId(): Int? {
        return FragmentAndIds.values().find {
            it.fragment == this::class.java
        }.let {
            it?.id
        }
    }
}