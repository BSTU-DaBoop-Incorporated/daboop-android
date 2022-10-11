package com.example.lab5

import android.content.res.Configuration
import androidx.fragment.app.FragmentActivity
import com.example.lab5.fragment.TodoDetailsFragment
import com.example.lab5.model.Todo

object ActivityHelpers {
    @JvmStatic
    fun createDetailsTodoFragment(activity: FragmentActivity, todo: Todo?) {
            val fragment = TodoDetailsFragment.newInstance(todo)
            activity.supportFragmentManager.beginTransaction()
                .replace(R.id.todo_details_fragment_container, fragment)
                .commit()

    }
    
}

fun FragmentActivity.isHorizontalOrientation() : Boolean {
    return resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
}