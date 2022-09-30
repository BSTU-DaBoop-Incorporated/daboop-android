package com.example.lab5

import android.view.ContextMenu
import android.view.View
import com.example.lab5.model.Todo

interface TodoInterface {
    
    fun createContextMenu(todo: Todo, contextMenu: ContextMenu,
                          view: View,
                          contextMenuInfo: ContextMenu.ContextMenuInfo?)
    
}