package com.example.lab5.fragment

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.example.lab5.MainActivity
import com.example.lab5.R
import com.example.lab5.databinding.FragmentTodoDetailsBinding
import com.example.lab5.datasource.TodoDatabaseHelper
import com.example.lab5.model.Todo
import com.example.lab5.viewModel.TodoDetailsViewModel
import com.example.lab5.viewModel.TodosViewModel


class TodoDetailsFragment : Fragment() {
    private lateinit var todoDetailsViewModel: TodoDetailsViewModel
    private val todosViewModel: TodosViewModel by activityViewModels()
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
            if (originalTodo == null) /* is not already set */ {
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
        val todo = Todo()
        todo.task = todoDetailsViewModel.task.get()
        todo.difficulty = todoDetailsViewModel.difficulty.get()
        todo.isDone = todoDetailsViewModel.isDone.get()
        todo.id = originalTodo?.id

        val todoDatabaseHelper = TodoDatabaseHelper(requireContext())
        todoDatabaseHelper.upsert(todo)
        val intent = Intent(requireContext(), MainActivity::class.java)
        startActivity(intent)

//        
//        activity?.let {
//            if(it.isHorizontalOrientation()) { 
//                todosViewModel.upsertTodo(todo)
////                todoDetailsViewModel.isEditMode.set(false)
//                ActivityHelpers.createDetailsTodoFragment(it, todo)
//                return
//            }
//            val intent = Intent(it, MainActivity::class.java)
//            intent.action = "save todo"
//            
//            intent.putExtra("todo", todo)
//
//            startActivity(intent)
//        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_todo_details, menu)
        this.menu = menu

        if (originalTodo == null) {
            menu.findItem(R.id.edit_action).isVisible = false
        }
        return super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.edit_action -> {
                if (todoDetailsViewModel.isEditMode.get() == true) {
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