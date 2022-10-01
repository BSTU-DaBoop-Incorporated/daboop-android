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

        val todoVm: TodoDetailsViewModel by viewModels()
        todoDetailsViewModel = todoVm
        binding.viewModel = todoVm



        originalTodo = intent.getSerializableExtra("todo") as Todo?
        todoDetailsViewModel.task.set(originalTodo?.task)
        todoDetailsViewModel.difficulty.set(originalTodo?.difficulty ?: 1)
        todoDetailsViewModel.isDone.set(originalTodo?.isDone ?: false)
        todoDetailsViewModel.isEditMode.set(originalTodo == null)
        
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_todo_details, menu)
        this.menu = menu
        
        if(originalTodo == null) {
            menu.findItem(R.id.edit_action).isVisible = false
        }
        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.edit_action -> {
                if(todoDetailsViewModel.isEditMode.get() == true) {
                    item.title = "Edit"
                    todoDetailsViewModel.isEditMode.set(false)
                    todoDetailsViewModel.task.set(originalTodo?.task)
                    todoDetailsViewModel.difficulty.set(originalTodo?.difficulty)
                    todoDetailsViewModel.isDone.set(originalTodo?.isDone)
                } else {
                    item.title = "Cancel edit"
                    todoDetailsViewModel.isEditMode.set(true)
                }
                
            }
        }
        return super.onOptionsItemSelected(item)
    }
    fun onSaveClick(view: View) {

        AlertDialog.Builder(this)
            .setTitle("Confirm changes")
            .setMessage("Do you really want to save changes?")
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setPositiveButton(android.R.string.yes
            ) { _, _ ->
                saveTodo()
            }
            .setNegativeButton(android.R.string.no, null).show()
        
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