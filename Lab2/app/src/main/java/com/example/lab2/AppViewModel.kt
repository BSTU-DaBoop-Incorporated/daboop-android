package com.example.lab2

import android.util.Log
import androidx.databinding.Bindable
import androidx.lifecycle.ViewModel
import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry

open class AppViewModel : ViewModel(), Observable {
    @get:Bindable
    var loanSum: Float = 0f
        set(value) {
            if(value == field)
                return
            notifyPropertyChanged(BR.loanSum)
            field = value
        }   
    
    @get:Bindable
    var payType: PayType = PayType.Annual
        set(value) {
            notifyPropertyChanged(BR.payType)
            field = value
        }    
    
    @get:Bindable
    var dwellingType: DwellingType = DwellingType.New
        set(value) {
            notifyPropertyChanged(BR.dwellingType)
            Log.d("ViewModel", "dwellingType = $value")
            field = value
        }    
    
    @get:Bindable
    var loanTerm: Int = 10
        set(value) {
            notifyPropertyChanged(BR.loanTerm)
            field = value
        }
       
    @get:Bindable
    var loanPercentage: Float = 26f
        set(value) {
            notifyPropertyChanged(BR.loanPercentage)
            field = value
        }
    
    
    
    private val callbacks: PropertyChangeRegistry = PropertyChangeRegistry()
    override fun addOnPropertyChangedCallback(
        callback: Observable.OnPropertyChangedCallback
    ) {
        callbacks.add(callback)
    }

    override fun removeOnPropertyChangedCallback(
        callback: Observable.OnPropertyChangedCallback
    ) {
        callbacks.remove(callback)
    }

    fun notifyChange() {
        callbacks.notifyCallbacks(this, 0, null)
    }

    fun notifyPropertyChanged(fieldId: Int) {
        callbacks.notifyCallbacks(this, fieldId, null)
    }
}

