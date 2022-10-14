package com.example.lab5.helper

import android.content.Context
import com.example.lab5.model.Todo
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

class FileHelpers {
    
    companion object {
        //save todos to json
        fun saveTodos(todos: List<Todo>, context: Context) {
            val jsonEncoder = Json.encodeToString(todos)
            val file = File(context.filesDir, "todos.json")
            if (!file.exists()) {
                file.createNewFile()

            }
            file.writeText(jsonEncoder);
        }

        //load todos from json
        fun loadTodos(context: Context): List<Todo> {
            val file = File(context.filesDir,"todos.json")
            if (!file.exists()) {
                return listOf()
            }
            val text = file.readText()
            return Json.decodeFromString(text)
        }
    }
}