package com.example.lab5.viewModel

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel

class TodoDetailsViewModel : ViewModel() {
    val isEditMode = ObservableField<Boolean>(false)
    val task = ObservableField<String>()
    val difficulty = ObservableField<Int>()
    val isDone = ObservableField<Boolean>()
}