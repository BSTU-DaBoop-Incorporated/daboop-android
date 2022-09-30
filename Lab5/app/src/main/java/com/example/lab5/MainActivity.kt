package com.example.lab5

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.ContextMenu
import android.view.ContextMenu.ContextMenuInfo
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lab5.model.Todo
import com.example.lab5.viewModel.TodosViewModel
import com.example.lab5.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(), TodoInterface {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var todosViewModel: TodosViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val todosVm by viewModels<TodosViewModel>()
        val todosViewModel = todosVm
        
        val todosList = FileHelpers.loadTodos(this)

        val todo1 = Todo()
        todo1.id = "1"
        todo1.task = "Task 1"
        todo1.difficulty = 1

        val todo2 = Todo()
        todo2.id = "2"
        todo2.task = "Task 2"
        todo2.difficulty = 2
        todo2.isDone = true
        
       
        todosViewModel.allTodos.value = mutableListOf(todo1, todo2).plus(todosList)
        
        if(intent.action == "save todo") {
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
        }
        

    }

//    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
//        FileHelpers.saveTodos(todosViewModel.allTodos.value!!, this)
//        super.onSaveInstanceState(outState, outPersistentState)
//    }
    
    val MENU_VIEW_DETAILS = 1
    val MENU_FINISH_TODO = 2
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
        if(todo.isDone == true) {

            menu.add(0, MENU_FINISH_TODO, 0, "Restore task").setOnMenuItemClickListener {
                val copy = todosViewModel.copyTodo(todo)
                copy.isDone = false
                todosViewModel.upsertTodo(copy)
                true
            }
        }
        else {
            menu.add(0, MENU_FINISH_TODO, 0, "Finish task").setOnMenuItemClickListener {
                val copy = todosViewModel.copyTodo(todo)
                copy.isDone = true
                todosViewModel.upsertTodo(copy)
                true
            }
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