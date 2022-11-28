package com.example.test

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.test.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navController = this.findNavController(R.id.fragmentContainerView)
        setSupportActionBar(findViewById(R.id.my_toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Add menu items here
                menuInflater.inflate(R.menu.toolbar_menu, menu)
            }


            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Handle the menu selection

                return false
            }
        })
        NavigationUI.setupWithNavController(binding.bottomBar, navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if(destination.id == R.id.fragment_training || destination.id == R.id.fragment_rating || destination.id == R.id.fragment_network || destination.id == R.id.fragment_multiplayer) {
           binding.myToolbar.visibility = View.GONE
                binding.bottomBar.visibility = View.VISIBLE
            } else {
                binding.myToolbar.visibility = View.VISIBLE
                binding.bottomBar.visibility = View.GONE
            }
        }

    }
}





