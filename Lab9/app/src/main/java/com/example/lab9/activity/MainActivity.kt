package com.example.lab9.activity

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.navigation.fragment.NavHostFragment
import com.example.lab9.R
import com.example.lab9.UserContactApplication
import com.example.lab9.databinding.ActivityMainBinding
import com.example.lab9.viewModel.UserContactDetailsViewModel
import com.example.lab9.viewModel.UserContactListViewModel
import com.example.lab9.viewModel.UserContactViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private val userContactListViewModel: UserContactListViewModel by viewModels {
        UserContactViewModelFactory((application as UserContactApplication).database.userContactDao())
    }

    private val userContactDetailsViewModel: UserContactDetailsViewModel by viewModels {
        UserContactViewModelFactory((application as UserContactApplication).database.userContactDao())
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = (supportFragmentManager.findFragmentById(R.id.fragment_container_view)
                as NavHostFragment).navController
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)
        
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = (supportFragmentManager.findFragmentById(R.id.fragment_container_view)
                as NavHostFragment).navController
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}