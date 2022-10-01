package com.example.lab5

import android.app.AlertDialog
import android.content.Intent
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
import com.example.lab5.model.Todo
import com.example.lab5.viewModel.TodosViewModel


class MainActivity : AppCompatActivity(), TodoInterface {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private val todosViewModel: TodosViewModel by lazy { ViewModelProvider(this).get(TodosViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val todosList = FileHelpers.loadTodos(this)

//        val todo1 = Todo()
//        todo1.id = "1"
//        todo1.task = "Task 1"
//        todo1.difficulty = 1
//
//        val todo2 = Todo()
//        todo2.id = "2"
//        todo2.task = "Task 2"
//        todo2.difficulty = 2
//        todo2.isDone = true


        todosViewModel.allTodos.value = todosList
//        todosViewModel.allTodos.value = mutableListOf(todo1, todo2)

        if (intent.action == "save todo") {
            val todo = intent.getSerializableExtra("todo") as Todo

            todosViewModel.upsertTodo(todo)
        }

        val recyclerView = findViewById<RecyclerView>(R.id.todos_recycler_view)
        val adapter = TodoListAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        registerForContextMenu(recyclerView)

        todosViewModel.allTodos.observe(this) { todos ->
            adapter.submitList(todos)
            FileHelpers.saveTodos(todosViewModel.allTodos.value!!, this)

        }


    }

//    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
//        super.onSaveInstanceState(outState, outPersistentState)
//    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add_action -> {
                createNew()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    val MENU_VIEW_DETAILS = 1
    val MENU_FINISH_TODO = 2
    val MENU_DELETE_TODO = 3
    override fun createContextMenu(
        todo: Todo,
        menu: ContextMenu,
        view: View,
        contextMenuInfo: ContextMenuInfo?,
    ) {
        menu.add(0, MENU_VIEW_DETAILS, 0, "View details").setOnMenuItemClickListener {
            viewDetails(todo)
            true
        }
        if (todo.isDone == true) {

            menu.add(0, MENU_FINISH_TODO, 0, "Restore task").setOnMenuItemClickListener {

                val copy = todosViewModel.copyTodo(todo)
                copy.isDone = false
                todosViewModel.upsertTodo(copy)
                true
            }
        } else {
            menu.add(0, MENU_FINISH_TODO, 0, "Finish task").setOnMenuItemClickListener {
                val copy = todosViewModel.copyTodo(todo)
                copy.isDone = true
                todosViewModel.upsertTodo(copy)
                true
            }
        }

        menu.add(0, MENU_DELETE_TODO, 0, "Delete task").setOnMenuItemClickListener {

            AlertDialog.Builder(this)
                .setTitle("Confirm delete")
                .setMessage("Do you really want to delete this task?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes
                ) { _, _ ->
                    todosViewModel.deleteTodo(todo)
                }
                .setNegativeButton(android.R.string.no, null).show()
            true
        }


    }


    fun createNew() {
        val intent = Intent(this, TodoDetailsActivity::class.java)
        startActivity(intent)
    }


    private fun viewDetails(todo: Todo) {
        Toast.makeText(this, todo.task, Toast.LENGTH_SHORT).show()
        val intent = Intent(this, TodoDetailsActivity::class.java)
        intent.putExtra("todo", todo)
        startActivity(intent)
    }


}