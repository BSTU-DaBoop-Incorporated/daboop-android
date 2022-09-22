package com.example.lab3

import android.os.Bundle
import android.widget.Button
import android.widget.TableRow
import android.widget.TextView
import androidx.activity.viewModels
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.lab3.databinding.ActivityListBinding

class ListActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityListBinding
    private lateinit var listViewModel: GameListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val vm: GameListViewModel by viewModels()
        listViewModel = vm

//        val navController = findNavController(R.id.nav_host_fragment_content_list)
//        appBarConfiguration = AppBarConfiguration(navController.graph)
//        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        val tableLayout =
            binding.gamesTableLayout

        val game1 = Game()
        game1.title.set("test1")
        val game2 = Game()
        game2.title.set("test2")
        
        listViewModel.games.value = arrayListOf(game1, game2)
        listViewModel.games.observe(this) { games ->
            tableLayout.removeAllViews()
            for (game in games) {
                val row = TableRow(this)
                val testView = TextView(this)
                testView.text = game.title.get()
                
                val editButton = Button(this)
                editButton.text = "Edit"
                row.addView(editButton)
                tableLayout.addView(row)
            }
        }
        
        
    }

//    override fun onSupportNavigateUp(): Boolean {
//        val navController = findNavController(R.id.nav_host_fragment_content_list)
//        return navController.navigateUp(appBarConfiguration)
//                || super.onSupportNavigateUp()
//    }
}