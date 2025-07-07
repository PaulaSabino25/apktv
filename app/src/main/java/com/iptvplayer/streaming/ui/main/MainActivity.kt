package com.iptvplayer.streaming.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.iptvplayer.streaming.databinding.ActivityMainBinding
import com.iptvplayer.streaming.ui.home.HomeFragment
import com.iptvplayer.streaming.ui.channels.ChannelsFragment
import com.iptvplayer.streaming.ui.movies.MoviesFragment
import com.iptvplayer.streaming.ui.series.SeriesFragment
import com.iptvplayer.streaming.ui.profile.ProfileFragment
import com.iptvplayer.streaming.R

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializa com HomeFragment
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(binding.navHostFragment.id, HomeFragment())
                .commit()
        }

        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            val fragment: Fragment = when (item.itemId) {
                R.id.nav_home -> HomeFragment()
                R.id.nav_tv -> ChannelsFragment.newInstance()
                R.id.nav_movies -> MoviesFragment()
                R.id.nav_series -> SeriesFragment()
                R.id.nav_profile -> ProfileFragment()
                else -> HomeFragment()
            }
            supportFragmentManager.beginTransaction()
                .replace(binding.navHostFragment.id, fragment)
                .commit()
            true
        }
    }
}
