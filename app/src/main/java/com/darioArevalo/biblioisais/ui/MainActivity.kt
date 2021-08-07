package com.darioArevalo.biblioisais.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
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
                R.id.FormFragment -> {
                    binding.navView.hide()
                }
                R.id.navigation_biblioteca -> {
                    binding.navView.hide()
                }
                R.id.splashFragment -> {
                    //binding.navView.hide()
                }
                R.id.imageviewFragment ->{
                    binding.navView.hide()
                }
                R.id.detallesPostFragment->{
                    binding.navView.hide()
                }
                R.id.agregarTemaFragment ->{
                    binding.navView.hide()
                }
                else -> {
                    binding.navView.show()
                }
            }
        }
    }
}