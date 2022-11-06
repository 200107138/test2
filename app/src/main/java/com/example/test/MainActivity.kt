package com.example.test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.test.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navController = this.findNavController(R.id.fragmentContainerView)

        NavigationUI.setupWithNavController(binding.bottomBar, navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if(destination.id == R.id.fragment_first_game || destination.id == R.id.fragment_second_game) {

                binding.bottomBar.visibility = View.GONE
            } else {

                binding.bottomBar.visibility = View.VISIBLE
            }
        }

    }
}





