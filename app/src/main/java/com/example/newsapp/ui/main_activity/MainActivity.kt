package com.example.newsapp.ui.main_activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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
        setNavItemListener()
    }

    private fun setNavItemListener() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController

        binding.navigationView.setOnItemSelectedListener {
            navController.navigate(it.itemId)
            return@setOnItemSelectedListener true
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
