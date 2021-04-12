package com.darioArevalo.biblioisais.ui

import android.os.Bundle
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.darioArevalo.biblioisais.R
import com.darioArevalo.biblioisais.core.hide
import com.darioArevalo.biblioisais.core.show
import com.darioArevalo.biblioisais.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        binding.navView.setupWithNavController(navController)
        observeDestinationChange()


        /*setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(
                R.id.navigation_bibliomundo, R.id.navigation_biblioisais, R.id.navigation_biblioteca,R.id.navigation_lecturaHuerta))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)*/

    }

    private fun observeDestinationChange(){
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when(destination.id){
                R.id.loginFragment -> {
                    binding.navView.hide()
                }
                R.id.registerFragment ->{
                    binding.navView.hide()
                }
                else -> {
                    binding.navView.show()
                }
            }
        }
    }
}