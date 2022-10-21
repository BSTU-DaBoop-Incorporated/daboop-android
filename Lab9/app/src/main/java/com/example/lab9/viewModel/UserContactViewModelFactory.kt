package com.example.lab9.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.lab9.room.dao.UserContactDAO

class UserContactViewModelFactory(private val userContactDAO: UserContactDAO) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserContactListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UserContactListViewModel(userContactDAO) as T
        }

        if (modelClass.isAssignableFrom(UserContactDetailsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UserContactDetailsViewModel(userContactDAO) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}