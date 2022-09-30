package com.example.lab5.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lab5.model.Todo

class TodosViewModel :ViewModel(){
    var allTodos: MutableLiveData<List<Todo>> = MutableLiveData<List<Todo>>()
    
    
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
        } else {
            todos.plus(todo)
        }
        allTodos.value = todos

    }
    
}