package com.example.lab5

import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.FrameLayout
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.lab5.model.Todo

class TodoListAdapter(private val todoInterface: TodoInterface) :
    ListAdapter<Todo, TodoListAdapter.TodoViewHolder>(TodoDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        return TodoViewHolder.create(parent, todoInterface)
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
        val frameLayout: FrameLayout = itemView.findViewById(R.id.frame_layout)

        //getContextMenuInfo 
//        override fun getContextMenuInfo(): ContextMenu.ContextMenuInfo? {
//            return null
//        }
        init {
            itemView.setOnCreateContextMenuListener(this)
        }

        fun bind(item: Todo) {
            data = item
            todoTask.text = item.task
            todoDifficulty.text = item.difficulty.toString()
            todoIsDoneCheckbox.isChecked = item.isDone ?: false

//            frameLayout.setOnClickListener {
//                todoInterface.onRowClick(item)
//            }
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

    class TodoDiffCallback : DiffUtil.ItemCallback<Todo>() {
        override fun areItemsTheSame(oldItem: Todo, newItem: Todo): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Todo, newItem: Todo): Boolean {
            return oldItem.task == newItem.task && oldItem.difficulty == newItem.difficulty && oldItem.isDone == newItem.isDone
        }
    }
}
