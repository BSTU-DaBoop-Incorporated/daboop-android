package com.example.lab5

import android.content.Context
import android.database.Cursor
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.FrameLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lab5.datasource.TodoDatabaseHelper
import com.example.lab5.model.Todo
enum class SortOrder(val value: String, val label: String) {
    ASC("ASC", "Ascending"),
    DESC("DESC", "Descending")
}
class TodoCursorAdapter(val context: Context, private val todoInterface: TodoInterface) :
    RecyclerView.Adapter<TodoCursorAdapter.TodoViewHolder>() {
    var searchField = TodoDatabaseHelper.KEY_TASK
    var sortOrder = SortOrder.ASC
    var taskFilter: String? = null
    private val helper: TodoDatabaseHelper = TodoDatabaseHelper(context)
    private var _cursor: Cursor? = null
    private var cursor: Cursor 
        get() = _cursor ?: throw IllegalStateException("Cursor is null")
        set(value) {
            _cursor = value
        }

    init {
        setHasStableIds(true)
        cursor = helper.getCursor()
        
    }
    
    fun updateCursor() {
        cursor.close()
        cursor = helper.getCursor(searchField, sortOrder, taskFilter)
        
        notifyDataSetChanged()
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        return TodoViewHolder.create(parent, todoInterface)
    }
    override fun getItemCount(): Int {
        return cursor.count
    }

    override fun getItemId(position: Int): Long {
        cursor.moveToPosition(position)
        return cursor.getLong(cursor.getColumnIndex(TodoDatabaseHelper.KEY_ID))
    }

    private fun getItem(position: Int) : Todo {
        cursor.moveToPosition(position)
        val id = cursor.getLong(cursor.getColumnIndex(TodoDatabaseHelper.KEY_ID))
        val task = cursor.getString(cursor.getColumnIndex(TodoDatabaseHelper.KEY_TASK))
        val difficulty = cursor.getInt(cursor.getColumnIndex(TodoDatabaseHelper.KEY_DIFFICULTY))
        val isDone = cursor.getInt(cursor.getColumnIndex(TodoDatabaseHelper.KEY_IS_DONE))
        val todo = Todo()
        todo.id = id
        todo.task = task
        todo.difficulty = difficulty
        todo.isDone = isDone == 1
        return todo
    }
    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
    }

    
    
    class TodoViewHolder(
        itemView: View,
        private val todoInterface: TodoInterface,
    ) : RecyclerView.ViewHolder(itemView), View.OnCreateContextMenuListener {
        var data: Todo? = null
        val todoTask: TextView = itemView.findViewById(R.id.todo_task)
        val todoDifficulty: TextView = itemView.findViewById(R.id.todo_difficulty)
        val todoIsDoneCheckbox: CheckBox = itemView.findViewById(R.id.todo_is_done_checkbox)
        
        init {
            itemView.setOnCreateContextMenuListener(this)
        }

        fun bind(item: Todo) {
            data = item
            todoTask.text = item.task
            todoDifficulty.text = item.difficulty.toString()
            todoIsDoneCheckbox.isChecked = item.isDone ?: false
            
        }

        companion object {
            fun create(parent: ViewGroup, todoInterface: TodoInterface): TodoViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.todos_recycler_view_item, parent, false)
                return TodoViewHolder(view, todoInterface)
            }
        }

        override fun onCreateContextMenu(
            contextMenu: ContextMenu,
            view: View,
            contextMenuInfo: ContextMenu.ContextMenuInfo?,
        ) {
            if(data != null) todoInterface.createContextMenu(data!!, contextMenu, view, contextMenuInfo)
        }
    }


}
