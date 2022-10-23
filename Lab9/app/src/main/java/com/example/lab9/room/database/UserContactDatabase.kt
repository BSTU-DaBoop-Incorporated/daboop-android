package com.example.lab9.room.database

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.AutoMigrationSpec
import com.example.lab9.model.UserContact
import com.example.lab9.room.dao.UserContactDAO

@Database(entities = [UserContact::class], version = 5, exportSchema = true,
    autoMigrations = [
        AutoMigration(from = 1, to = 2),
        AutoMigration(from = 2, to = 3),
        AutoMigration(from = 3, to = 4),
        AutoMigration(from = 4, to = 5),
    ]
)
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
                )
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }


}