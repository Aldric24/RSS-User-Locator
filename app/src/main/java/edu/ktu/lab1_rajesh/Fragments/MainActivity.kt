package edu.ktu.lab1_rajesh.Fragments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import edu.ktu.lab1_rajesh.R
import edu.ktu.lab1_rajesh.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostController= supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostController.navController

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val navInflater = navController.navInflater
        val graph = navInflater.inflate(R.navigation.navgraph)
        navController.graph = graph

        bottomNavigationView.menu.clear()
        bottomNavigationView.inflateMenu(R.menu.bottom_nav)
        setupWithNavController(bottomNavigationView,navController)
    }
}