package com.example.lab5.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.example.lab5.model.Todo

class TodosViewModel(application: Application) :AndroidViewModel(application){
    var allTodos: MutableLiveData<List<Todo>> = MutableLiveData<List<Todo>>()
    val taskFilter = MutableLiveData<String?>(null)
    val sortOrderText = MutableLiveData<String>("ASC")
    val sortOrderSelectedItemPosition = MutableLiveData(0)
    val layoutManager = MutableLiveData<RecyclerView.LayoutManager?>()
    
    
    fun addTodo (todo: Todo) {
        allTodos.value = allTodos.value?.plus(todo)
    }
    fun copyTodo(todo: Todo) : Todo {
        val todoCopy = Todo()
        todoCopy.task = todo.task
        todoCopy.difficulty = todo.difficulty
        todoCopy.isDone = todo.isDone
        todoCopy.id = todo.id
        return todoCopy
    }
    fun upsertTodo(todo: Todo) {
        val todos = allTodos.value?.toMutableList() ?: mutableListOf()
        val index = todos.indexOfFirst { it.id == todo.id }
        if(index != -1) {
            todos[index] = todo
            allTodos.value = todos
            return
        }
        
        allTodos.value = todos.plus(todo)


    }

    fun deleteTodo (todo: Todo) {
        val todos = allTodos.value?.toMutableList() ?: mutableListOf()
        val index = todos.indexOfFirst { it.id == todo.id }
        if(index != -1) {
            todos.removeAt(index)
            allTodos.value = todos
        }
    }
    
    fun setGridLayout() {
        val application = getApplication<Application>()
        layoutManager.value = androidx.recyclerview.widget.GridLayoutManager(application.applicationContext, 2)
    }
    
    fun setLinearLayout() {
        val application = getApplication<Application>()
        layoutManager.value = androidx.recyclerview.widget.LinearLayoutManager(application.applicationContext)
    }
}