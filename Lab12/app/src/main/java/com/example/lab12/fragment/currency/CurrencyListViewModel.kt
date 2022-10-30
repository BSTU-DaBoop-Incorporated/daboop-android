package com.example.lab12.fragment.currency

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CurrencyListViewModel : ViewModel() {
    val currencies = MutableLiveData<List<Currency>>()
    
}