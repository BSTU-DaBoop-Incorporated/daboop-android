package com.example.lab5

import android.R
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.lab5.databinding.ActivityTodoDetailsBinding
import com.example.lab5.model.Todo
import com.example.lab5.viewModel.TodoDetailsViewModel
import java.util.*


class TodoDetailsActivity : AppCompatActivity() {
    private lateinit var todoDetailsViewModel: TodoDetailsViewModel
    private lateinit var binding: ActivityTodoDetailsBinding
    private var originalTodo: Todo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTodoDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val todoVm: TodoDetailsViewModel by viewModels()
        todoDetailsViewModel = todoVm
        binding.viewModel = todoVm



        originalTodo = intent.getSerializableExtra("todo") as Todo?
        todoDetailsViewModel.task.set(originalTodo?.task)
        todoDetailsViewModel.difficulty.set(originalTodo?.difficulty)
        todoDetailsViewModel.isDone.set(originalTodo?.isDone)
        todoDetailsViewModel.isEditMode.set(originalTodo == null)
        
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menu.add(Menu.NONE, 0, Menu.FIRST, "Edit").setOnMenuItemClickListener { 
            todoDetailsViewModel.isEditMode.set(true)
            true
        }
        return super.onCreateOptionsMenu(menu)
    }
    
    fun onSaveClick(view: View) {

        AlertDialog.Builder(this)
            .setTitle("Title")
            .setMessage("Do you really want to whatever?")
            .setIcon(R.drawable.ic_dialog_alert)
            .setPositiveButton(R.string.yes,
                DialogInterface.OnClickListener { dialog, whichButton ->
                    saveTodo()
                })
            .setNegativeButton(R.string.no, null).show()
        
    }
    
    fun saveTodo() {
        val intent = Intent(this, MainActivity::class.java)
        intent.action = "save todo"
        val todo = Todo()
        todo.task = todoDetailsViewModel.task.get()
        todo.difficulty = todoDetailsViewModel.difficulty.get()
        todo.isDone = todoDetailsViewModel.isDone.get()
        todo.id = originalTodo?.id ?: UUID.randomUUID().toString()

        intent.putExtra("todo", todo)

        startActivity(intent)
    }
}