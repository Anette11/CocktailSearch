package com.example.cocktailsearch.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.cocktailsearch.R
import com.example.cocktailsearch.adapters.CocktailStateAdapter
import com.example.cocktailsearch.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        attachTabLayout()
    }

    private fun attachTabLayout() {
        with(binding) {
            val cocktailStateAdapter = CocktailStateAdapter(supportFragmentManager, lifecycle)
            viewPager.adapter = cocktailStateAdapter

            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                when (position) {
                    0 -> {
                        tab.text = getString(R.string.home)
                    }
                    else -> {
                        tab.text = getString(R.string.search)
                    }
                }
            }.attach()
        }
    }
}