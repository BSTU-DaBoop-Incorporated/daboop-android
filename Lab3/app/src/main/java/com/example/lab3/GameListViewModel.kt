package com.example.lab3

import androidx.databinding.ObservableArrayList
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameListViewModel: ViewModel() {
    var games = MutableLiveData<ArrayList<Game>>()
}