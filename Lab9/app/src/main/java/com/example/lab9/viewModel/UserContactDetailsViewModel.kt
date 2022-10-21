package com.example.lab9.viewModel

import androidx.databinding.ObservableField
import androidx.lifecycle.*
import com.example.lab9.model.UserContact
import com.example.lab9.room.dao.UserContactDAO

class UserContactDetailsViewModel (private val userContactDAO: UserContactDAO) : ViewModel() {
    val userContact = MutableLiveData<UserContact>()
    
    val name = ObservableField<String>()
    val phone = ObservableField<String>()
    val email = ObservableField<String>()
    
    
    fun bindObservables() {
        name.set(userContact.value?.name)
        phone.set(userContact.value?.phone)
        email.set(userContact.value?.email)
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
