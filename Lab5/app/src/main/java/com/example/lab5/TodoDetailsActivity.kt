package com.example.lab5

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.lab5.databinding.ActivityTodoDetailsBinding
import com.example.lab5.model.Todo
import com.example.lab5.viewModel.TodoDetailsViewModel
import com.example.lab5.viewModel.TodosViewModel
import java.util.*


class TodoDetailsActivity : AppCompatActivity() {
    private lateinit var todoDetailsViewModel: TodoDetailsViewModel
    private lateinit var binding: ActivityTodoDetailsBinding
    private var originalTodo: Todo? = null
    private var menu: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTodoDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

}