package com.example.lab9.viewModel

import android.util.Log
import androidx.lifecycle.*
import com.example.lab9.model.UserContact
import com.example.lab9.room.dao.UserContactDAO

class UserContactListViewModel (private val userContactDAO: UserContactDAO) : ViewModel() {
    var _userContacts: LiveData<List<UserContact>>? = null
    val userContacts: LiveData<List<UserContact>> 
    get() = userContactDAO.getFlow().asLiveData()
    suspend fun insert(word: UserContact) = userContactDAO.insert(word)
    suspend fun delete(word: UserContact) = userContactDAO.delete(word)
    suspend fun upsert(word: UserContact) = userContactDAO.upsert(word)
}