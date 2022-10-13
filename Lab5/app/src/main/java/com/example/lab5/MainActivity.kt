package com.example.lab5

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.ui.AppBarConfiguration
import com.example.lab5.databinding.ActivityMainBinding
import com.example.lab5.fragment.TodoDetailsFragment
import com.example.lab5.viewModel.TodosViewModel


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private val todosViewModel: TodosViewModel by lazy { ViewModelProvider(this).get(TodosViewModel::class.java) }

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

        setSupportActionBar(binding.toolbar)
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

}