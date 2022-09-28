package com.example.lab3

import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TableRow
import android.widget.TextView
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.lab3.databinding.ActivityListBinding
import java.util.*

class ListActivity : BaseFormActivity() {
    private lateinit var binding: ActivityListBinding
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val tableLayout =
            binding.gamesTableLayout


        readStateFromFile()


        viewModel.games.observe(this) { games ->
            tableLayout.removeAllViews()
            for (game in games) {
                val row = TableRow(this)
                val textView = TextView(this)
                textView.text = game.title.get()
                textView.setTextAppearance(this,
                    androidx.appcompat.R.style.TextAppearance_AppCompat_Medium)
                row.addView(textView)

                val editButton = Button(this)
                editButton.text = "Edit"
                editButton.setOnClickListener { 
                    sendGameIntent(game, super.games, MainActivity::class.java)
                }
                row.addView(editButton)
                tableLayout.addView(row)
            }
        }


    }

}