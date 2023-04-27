package com.lucassimao.maptrack.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.lucassimao.maptrack.R
import com.lucassimao.maptrack.databinding.ActivityMainBinding
import com.lucassimao.maptrack.util.Constants.TRACKING_FRAGMENT_ACTION
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navigateToMapsFragment(intent)

    }

    private fun navigateToMapsFragment(intent: Intent?) {
        if (intent?.action == TRACKING_FRAGMENT_ACTION) {
            val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
            navHostFragment.findNavController().navigate(R.id.action_global_mapsFragment)
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        navigateToMapsFragment(intent)
    }

}