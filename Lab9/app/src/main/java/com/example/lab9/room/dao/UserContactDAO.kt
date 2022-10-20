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
    suspend fun getLiveData(): LiveData<List<UserContact>>
    
    @Query("SELECT * FROM UserContact ORDER BY name ASC")
    suspend fun getFlow(): Flow<List<UserContact>>
    
    

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(word: UserContact)
    
    @Delete
    suspend fun delete(word: UserContact)

    @Update
    suspend fun upsert(word: UserContact)

}