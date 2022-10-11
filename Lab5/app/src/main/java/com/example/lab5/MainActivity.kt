package com.example.lab5

import android.app.AlertDialog
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.os.PersistableBundle
import android.view.ContextMenu
import android.view.ContextMenu.ContextMenuInfo
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.ui.AppBarConfiguration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lab5.databinding.ActivityMainBinding
import com.example.lab5.fragment.TodoDetailsFragment
import com.example.lab5.model.Todo
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
//
////    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
////        super.onSaveInstanceState(outState, outPersistentState)
////    }
//

}