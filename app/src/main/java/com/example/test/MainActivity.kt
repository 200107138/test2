package com.example.test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.core.content.ContextCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.test.databinding.ActivityMainBinding
import com.example.test.databinding.HomeLayoutBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var switchOnOff: SwitchCompat
    private lateinit var tvSwitchYes: TextView
    private lateinit var tvSwitchNo: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        drawerLayout = binding.drawerLayout

        val navController = this.findNavController(R.id.fragment)

        NavigationUI.setupActionBarWithNavController(this,navController, drawerLayout)

        NavigationUI.setupWithNavController(binding.navView, navController)

        switchOnOff = findViewById<SwitchCompat>(R.id.switchOnOff)
        tvSwitchYes = findViewById<TextView>(R.id.tvSwitchYes)
        tvSwitchNo = findViewById<TextView>(R.id.tvSwitchNo)
        switchOnOff.setOnCheckedChangeListener { _, checked ->
            when {
                checked -> {
                    tvSwitchYes.setTextColor(ContextCompat.getColor(this,R.color.blue))
                    tvSwitchNo.setTextColor(ContextCompat.getColor(this,R.color.white))
                }
                else -> {
                    tvSwitchYes.setTextColor(ContextCompat.getColor(this,R.color.white))
                    tvSwitchNo.setTextColor(ContextCompat.getColor(this,R.color.blue))
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.fragment)
        return NavigationUI.navigateUp(navController, drawerLayout)
    }

}