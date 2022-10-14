package com.example.lab5.fragment

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lab5.*
import com.example.lab5.datasource.TodoDatabaseHelper
import com.example.lab5.helper.ActivityHelpers.createDetailsTodoFragment
import com.example.lab5.helper.FileHelpers
import com.example.lab5.helper.isHorizontalOrientation
import com.example.lab5.model.Todo
import com.example.lab5.viewModel.TodosViewModel
import org.greenrobot.eventbus.EventBus

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

        val view: View? = inflater.inflate(R.layout.fragment_todo_list, container, false)
        activity?.let {
//            val todosList = FileHelpers.loadTodos(it)

//            viewModel.allTodos.value = todosList
//            if (it.intent.action == "save todo") { // TODO: Still not working
//                val todo = it.intent.getSerializableExtra("todo") as Todo
//
//                viewModel.upsertTodo(todo)
//            }

            val recyclerView = view!!.findViewById<RecyclerView>(R.id.todos_recycler_view)
            val adapter = TodoCursorAdapter(activity!!, this)
            todoAdapter = adapter
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(it)
            registerForContextMenu(recyclerView)
            
            val dbhelper = TodoDatabaseHelper(activity!!)
            val fakeTodo = Todo(
                id = 0,
                task = "Fake todo",
                difficulty = 1,
                isDone = false
            )

            val fakeTodo2 = Todo(
                id = 1,
                task = "Fake todo 2",
                difficulty = 6,
                isDone = false
            )
            dbhelper.upsert(fakeTodo)
            dbhelper.upsert(fakeTodo2)
//
//            viewModel.allTodos.observe(it) { todos ->
//                adapter.submitList(todos)
//                FileHelpers.saveTodos(viewModel.allTodos.value!!, it)
//
//            }
        }
        return view
    }

    override fun onResume() {
        super.onResume()
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

                val copy = viewModel.copyTodo(todo)
                copy.isDone = false
                viewModel.upsertTodo(copy)
                true
            }
        } else {
            menu.add(0, MENU_FINISH_TODO, 0, "Finish task").setOnMenuItemClickListener {
                val copy = viewModel.copyTodo(todo)
                copy.isDone = true
                viewModel.upsertTodo(copy)
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

                    }
                    .setNegativeButton(android.R.string.no, null).show()
            }
            true
        }


    }


    private fun viewDetails(todo: Todo) {
        
        activity?.let {
            if(it.isHorizontalOrientation()) {
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
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun createNew() {
        activity?.let {
            if(it.isHorizontalOrientation()) {
                createDetailsTodoFragment(it, null)
                return
            }

            val intent = Intent(it, TodoDetailsActivity::class.java)
            startActivity(intent)
        }
    }

    
    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

}