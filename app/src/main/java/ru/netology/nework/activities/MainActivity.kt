package ru.netology.nework.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import ru.netology.nework.R
import ru.netology.nework.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bottomNavigationView = binding.bottomNavigation
        val navController = findNavController(R.id.nav_host_fragment_activity_app)
        bottomNavigationView.setupWithNavController(navController)


        var autorised = false

        if (!autorised){
            navController.navigate(R.id.singInFragment)
            autorised = !autorised
        }





    }
}