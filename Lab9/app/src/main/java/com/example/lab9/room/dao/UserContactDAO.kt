package com.example.lab9.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.lab9.model.UserContact
import kotlinx.coroutines.flow.Flow


@Dao
interface UserContactDAO {

    @Query("SELECT * FROM UserContact ORDER BY name ASC")
    suspend fun getAll(): List<UserContact>

    @Query("SELECT * FROM UserContact ORDER BY name ASC")
    fun getFlow(): Flow<List<UserContact>>
    
    
    @Query("SELECT * FROM UserContact ORDER BY name ASC")
    fun getLiveData(): LiveData<List<UserContact>>
    

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(word: UserContact) : Long
    
    @Delete
    suspend fun delete(word: UserContact)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(word: UserContact)

}