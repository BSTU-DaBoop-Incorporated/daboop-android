package com.example.lab5.fragment

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lab5.*
import com.example.lab5.SortOrder
import com.example.lab5.databinding.FragmentTodoListBinding
import com.example.lab5.datasource.TodoDatabaseHelper
import com.example.lab5.event.NavigationOptionItemSelectedEvent
import com.example.lab5.event.TodoTableUpdatedEvent
import com.example.lab5.helper.ActivityHelpers.createDetailsTodoFragment
import com.example.lab5.helper.isHorizontalOrientation
import com.example.lab5.model.Todo
import com.example.lab5.viewModel.TodosViewModel
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe


class TodoListFragment : Fragment(R.layout.fragment_todo_list), TodoInterface {

    companion object {
        fun newInstance() = TodoListFragment()
    }

    private val viewModel: TodosViewModel by activityViewModels()
    private lateinit var todoAdapter: TodoCursorAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        val binding = FragmentTodoListBinding.inflate(layoutInflater, container, false)
        binding.viewModel = viewModel

        viewModel.layoutManager.value =
            viewModel.layoutManager.value ?: LinearLayoutManager(context)

/*
        binding.sortOrderSpinner.adapter = ArrayAdapter(requireContext(), android.R.layout
            .simple_list_item_1,
            listOf("Ascending","Descending"))
*/

        activity?.let {

            val recyclerView = binding.root.findViewById<RecyclerView>(R.id.todos_recycler_view)
            val adapter = TodoCursorAdapter(activity!!, this)
            todoAdapter = adapter
            recyclerView.adapter = adapter
            registerForContextMenu(recyclerView)

        }

        viewModel.taskFilter.observe(viewLifecycleOwner) {
            todoAdapter.taskFilter = it
            todoAdapter.updateCursor()
        }

        viewModel.sortOrderSelectedItemPosition.observe(viewLifecycleOwner) {
            todoAdapter.sortOrder = SortOrder.values()[it]
            todoAdapter.updateCursor()
        }

        viewModel.layoutManager.observe(viewLifecycleOwner) {
            val recyclerView = binding.root.findViewById<RecyclerView>(R.id.todos_recycler_view)
            recyclerView.layoutManager = it
        }
        return binding.root
    }
    
    fun updateTodo(todo: Todo) {
        val helper = TodoDatabaseHelper(activity!!)
        helper.upsert(todo)
        onTodoTableUpdatedEvent(TodoTableUpdatedEvent())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }


    val MENU_VIEW_DETAILS = 1
    val MENU_FINISH_TODO = 2
    val MENU_DELETE_TODO = 3
    override fun createContextMenu(
        todo: Todo,
        menu: ContextMenu,
        view: View,
        contextMenuInfo: ContextMenu.ContextMenuInfo?,
    ) {
        menu.add(0, MENU_VIEW_DETAILS, 0, "View details").setOnMenuItemClickListener {
            viewDetails(todo)
            true
        }
        if (todo.isDone == true) {

            menu.add(0, MENU_FINISH_TODO, 0, "Restore task").setOnMenuItemClickListener {
                todo.isDone = false
                updateTodo(todo)
                true
            }
        } else {
            menu.add(0, MENU_FINISH_TODO, 0, "Finish task").setOnMenuItemClickListener {
                todo.isDone = true
                updateTodo(todo)
                true
            }
        }

        menu.add(0, MENU_DELETE_TODO, 0, "Delete task").setOnMenuItemClickListener {
            activity?.let {
                AlertDialog.Builder(it)
                    .setTitle("Confirm delete")
                    .setMessage("Do you really want to delete this task?")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton(android.R.string.yes
                    ) { _, _ ->
//                        viewModel.deleteTodo(todo)
                        val todoDatabaseHelper = TodoDatabaseHelper(requireContext())
                        todoDatabaseHelper.delete(todo)
                        onTodoTableUpdatedEvent(TodoTableUpdatedEvent())

                    }
                    .setNegativeButton(android.R.string.no, null).show()
            }
            true
        }


    }


    private fun viewDetails(todo: Todo) {

        activity?.let {
            if (it.isHorizontalOrientation()) {
                createDetailsTodoFragment(it, todo)
                return
            }
            val intent = Intent(it, TodoDetailsActivity::class.java)
            intent.putExtra("todo", todo as java.io.Serializable)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_main, menu)
        super.onCreateOptionsMenu(menu, menuInflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add_action -> {
                createNew()
                return true
            }
            R.id.grid_layout_action -> {
                viewModel.layoutManager.value = GridLayoutManager(context, 2)
                return true
            }
            R.id.linear_layout_action -> {
                viewModel.layoutManager.value = LinearLayoutManager(context)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun createNew() {
        activity?.let {
            if (it.isHorizontalOrientation()) {
                createDetailsTodoFragment(it, null)
                return
            }

            val intent = Intent(it, TodoDetailsActivity::class.java)
            startActivity(intent)
        }
    }


    @Subscribe
    fun onTodoTableUpdatedEvent(event: TodoTableUpdatedEvent) {
        todoAdapter.updateCursor()
    }
    
    @Subscribe
    fun onNavigationOptionItemSelected(event: NavigationOptionItemSelectedEvent) {
        onOptionsItemSelected(event.item)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.layoutManager.value = null
        
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        EventBus.getDefault().register(this)
    }

    override fun onDetach() {
        super.onDetach()
        EventBus.getDefault().unregister(this)
    }

}