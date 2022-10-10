package com.example.lab5.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import com.example.lab5.R
import com.example.lab5.databinding.ActivityTodoDetailsBinding
import com.example.lab5.databinding.FragmentTodoDetailsBinding
import com.example.lab5.model.Todo
import com.example.lab5.viewModel.TodoDetailsViewModel


class TodoDetailsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var todoDetailsViewModel: TodoDetailsViewModel
    private lateinit var binding: FragmentTodoDetailsBinding
    private var originalTodo: Todo? = null
    private var menu: Menu? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        arguments?.let {
//            param1 = it.getString(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)
//        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        val view: View? = inflater.inflate(R.layout.fragment_todo_details, container, false)

//        binding = ActivityTodoDetailsBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        val todoVm: TodoDetailsViewModel by viewModels()
//        todoDetailsViewModel = todoVm
//        binding.viewModel = todoVm
//
//
//
//        originalTodo = intent.getSerializableExtra("todo") as Todo?
//        todoDetailsViewModel.task.set(originalTodo?.task)
//        todoDetailsViewModel.difficulty.set(originalTodo?.difficulty ?: 1)
//        todoDetailsViewModel.isDone.set(originalTodo?.isDone ?: false)
//        todoDetailsViewModel.isEditMode.set(originalTodo == null)
        return view
    }

    companion object {
   
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TodoDetailsFragment().apply {
//                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
//                }
            }
    }
}