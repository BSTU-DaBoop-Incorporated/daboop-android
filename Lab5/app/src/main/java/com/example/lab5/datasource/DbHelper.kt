package com.example.lab5.datasource

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.lab5.model.Todo

class TodoDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null,
    DATABASE_VERSION) {
    companion object {
        public val DATABASE_VERSION = 1
        public val DATABASE_NAME = "TodoDatabase"
        public val TABLE_TODOS = "EmployeeTable"
        public val KEY_ID = "id"
        public val KEY_TASK = "name"
        public val KEY_DIFFICULTY = "email"
        public val KEY_IS_DONE = "email"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        
        
        //language=sql
        val CREATE_TODO_TABLE =
            ("CREATE TABLE $TABLE_TODOS($KEY_ID STRING PRIMARY KEY AUTOINCREMENT,$KEY_TASK TEXT, " +
                    "$KEY_DIFFICULTY INTEGER" + "$KEY_IS_DONE INTEGER DEFAULT 0)")
        db?.execSQL(CREATE_TODO_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {

        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_TODOS")
        onCreate(db)
    }
    
    fun add (todo: Todo):Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, todo.id)
        contentValues.put(KEY_TASK, todo.task) 
        contentValues.put(KEY_DIFFICULTY,todo.difficulty )
        contentValues.put(KEY_IS_DONE, todo.isDone)
        
        
        val success = db.insert(TABLE_TODOS, null, contentValues)
        db.close() 
        return success
    }
    
    fun update(todo: Todo) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, todo.id)
        contentValues.put(KEY_TASK, todo.task) 
        contentValues.put(KEY_DIFFICULTY,todo.difficulty )
        contentValues.put(KEY_IS_DONE, todo.isDone)
        
        db.update(TABLE_TODOS, contentValues, "$KEY_ID = ?", arrayOf(todo.id.toString()))
        db.close()
    }
    
    fun upsert(todo: Todo) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, todo.id)
        contentValues.put(KEY_TASK, todo.task) 
        contentValues.put(KEY_DIFFICULTY,todo.difficulty )
        contentValues.put(KEY_IS_DONE, todo.isDone)
        
        val count = db.update(TABLE_TODOS, contentValues, "$KEY_ID = ?", arrayOf(todo.id.toString()))
        if (count == 0) {
            db.insert(TABLE_TODOS, null, contentValues)
        }
        db.close()
    }
    
    fun delete(todo: Todo) {
        val db = this.writableDatabase
        db.delete(TABLE_TODOS, "$KEY_ID = ?", arrayOf(todo.id.toString()))
        db.close()
    }
    
    fun getCursor() : Cursor {
        val db = this.readableDatabase
        val selectQuery = "SELECT * FROM $TABLE_TODOS"
        val cursor = db.rawQuery(selectQuery, null)
        return cursor
    }
    
}