package com.example.lab2

import androidx.databinding.Bindable
import androidx.lifecycle.ViewModel
import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry

open class AppViewModel : ViewModel(), Observable {
//    private var _loan = Loan()
////    fun getLoan(): Loan = _loan
//
//    @get:Bindable
//    var loan
//        get() = _loan
//        set(value) {
//            notifyPropertyChanged(BR.loan)
//            _loan = value
//        }

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

