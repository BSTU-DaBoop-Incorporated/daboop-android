package com.example.lab9.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.lab9.model.UserContact
import com.example.lab9.room.dao.UserContactDAO

// Annotates class to be a Room Database with a table (entity) of the Word class
@Database(entities = [UserContact::class], version = 1, exportSchema = false)
public abstract class UserContactDatabase : RoomDatabase() {

    abstract fun userContactDao(): UserContactDAO

    companion object {
        @Volatile
        private var INSTANCE: UserContactDatabase? = null

        fun getDatabase(context: Context): UserContactDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserContactDatabase::class.java,
                    "word_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}