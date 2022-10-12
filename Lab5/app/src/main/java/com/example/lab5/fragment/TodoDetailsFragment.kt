package com.example.lab5.fragment

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.*
import androidx.fragment.app.Fragment
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.lab5.MainActivity
import com.example.lab5.R
import com.example.lab5.databinding.ActivityTodoDetailsBinding
import com.example.lab5.databinding.FragmentTodoDetailsBinding
import com.example.lab5.isHorizontalOrientation
import com.example.lab5.model.Todo
import com.example.lab5.viewModel.TodoDetailsViewModel
import com.example.lab5.viewModel.TodosViewModel
import java.util.*


class TodoDetailsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var todoDetailsViewModel: TodoDetailsViewModel
    private lateinit var binding: FragmentTodoDetailsBinding
    private var originalTodo: Todo? = null
    private var menu: Menu? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        todoDetailsViewModel = ViewModelProvider(this)[TodoDetailsViewModel::class.java]
        
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentTodoDetailsBinding.inflate(layoutInflater, container, false)
        binding.fragment = this

        arguments?.let {
            (it.getSerializable("todo") as Todo?).let {
                originalTodo = it
            }
        }
        activity?.let {
            binding.viewModel = todoDetailsViewModel
            if(originalTodo == null) /* is not already set */ {
                originalTodo = it.intent.getSerializableExtra("todo") as Todo?
            }
            todoDetailsViewModel.task.set(originalTodo?.task)
            todoDetailsViewModel.difficulty.set(originalTodo?.difficulty ?: 1)
            todoDetailsViewModel.isDone.set(originalTodo?.isDone ?: false)
            todoDetailsViewModel.isEditMode.set(originalTodo == null)
        }
        return binding.root
    }

    fun onSaveClick() {

        activity.let {
            AlertDialog.Builder(it)
                .setTitle("Confirm changes")
                .setMessage("Do you really want to save changes?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes
                ) { _, _ ->
                    saveTodo()
                }
                .setNegativeButton(android.R.string.no, null).show()
        }

    }

    private fun saveTodo() {
        activity?.let {
            val intent = Intent(it, MainActivity::class.java)
            intent.action = "save todo"
            if(it.isHorizontalOrientation()) { // TODO: Still not working
                intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT 
            }
            val todo = Todo()
            todo.task = todoDetailsViewModel.task.get()
            todo.difficulty = todoDetailsViewModel.difficulty.get()
            todo.isDone = todoDetailsViewModel.isDone.get()
            todo.id = originalTodo?.id ?: UUID.randomUUID().toString()

            intent.putExtra("todo", todo)

            startActivity(intent)
        }
    }
    
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_todo_details, menu)
        this.menu = menu

        if(originalTodo == null) {
            menu.findItem(R.id.edit_action).isVisible = false
        }
        return super.onCreateOptionsMenu(menu, inflater)
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

    companion object {

        @JvmStatic
        fun newInstance(todo: Todo?) =
            TodoDetailsFragment().apply {
                todo.let {
                    arguments = Bundle().apply {
                        putSerializable("todo", todo)
                    }
                }
            }
    }
}