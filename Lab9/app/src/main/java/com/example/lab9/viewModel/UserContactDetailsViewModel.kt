package com.example.lab9.viewModel

import android.net.Uri
import androidx.databinding.ObservableField
import androidx.lifecycle.*
import com.example.lab9.model.UserContact
import com.example.lab9.room.dao.UserContactDAO

class UserContactDetailsViewModel (private val userContactDAO: UserContactDAO) : ViewModel() {

    var isEditMode = ObservableField(false)

    val userContact = MutableLiveData<UserContact>()
    
    val name = ObservableField<String>()
    val phone = ObservableField<String>()
    val email = ObservableField<String>()
    val imageUri = ObservableField<Uri>()
    
    
    fun bindObservables() {
        name.set(userContact.value?.name)
        phone.set(userContact.value?.phone)
        email.set(userContact.value?.email)
        userContact.value?.imageUri?.let {
            imageUri.set(Uri.parse(it)) // TODO dangerous parse
        }
    }
    
    suspend fun insert(word: UserContact) = userContactDAO.insert(word)

    suspend fun delete(word: UserContact) = userContactDAO.delete(word)

    suspend fun upsert(word: UserContact) = userContactDAO.upsert(word)
}
