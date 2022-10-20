package com.example.lab9.viewModel

import androidx.lifecycle.*
import com.example.lab9.model.UserContact
import com.example.lab9.room.dao.UserContactDAO

class UserContactListViewModel (private val userContactDAO: UserContactDAO) : ViewModel() {
    val userContacts: LiveData<List<UserContact>> 
    get() = liveData {
        userContactDAO.getFlow().asLiveData()
    }
    suspend fun insert(word: UserContact) {
        userContactDAO.insert(word)
    }
    
    suspend fun delete(word: UserContact) {
        userContactDAO.delete(word)
    }
    
    suspend fun upsert(word: UserContact) {
        userContactDAO.upsert(word)
    }
}

class UserContactListViewModelFactory(private val userContactDAO: UserContactDAO) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserContactListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UserContactListViewModel(userContactDAO) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}