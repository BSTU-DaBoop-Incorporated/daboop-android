package com.example.lab5

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.ui.AppBarConfiguration
import com.example.lab5.databinding.ActivityMainBinding
import com.example.lab5.event.NavigationOptionItemSelectedEvent
import com.example.lab5.fragment.TodoDetailsFragment
import com.example.lab5.viewModel.TodosViewModel
import org.greenrobot.eventbus.EventBus


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var drawerLayout: DrawerLayout
    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val orientation = resources.configuration.orientation
        if(orientation == Configuration.ORIENTATION_LANDSCAPE) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.todo_details_fragment_container,
                    TodoDetailsFragment.newInstance(null))
                .commit()
        }



        drawerLayout = binding.drawerLayout
        actionBarDrawerToggle = ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R
            .string.nav_close)

        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
        setSupportActionBar(binding.toolbar)

        
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        
        binding.navView.setNavigationItemSelectedListener {
            EventBus.getDefault().post(NavigationOptionItemSelectedEvent(it))
            drawerLayout.closeDrawer(binding.navView)
            true
        }
    }
    override fun onBackPressed() {
        val fm = supportFragmentManager
        if (fm.backStackEntryCount > 0) {
            Log.i("MainActivity", "popping backstack");
            fm.popBackStack();
            return
        } else {
            Log.i("MainActivity", "nothing on backstack, calling super");
        }
        super.onBackPressed()
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (actionBarDrawerToggle.onOptionsItemSelected(item)) true else super.onOptionsItemSelected(item)
    }

}