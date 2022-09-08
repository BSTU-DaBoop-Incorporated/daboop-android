package com.example.lab2

import android.util.Log
import androidx.databinding.Bindable
import androidx.lifecycle.ViewModel
import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import kotlin.math.pow

open class AppViewModel : ViewModel(), Observable {
    @get:Bindable
    var loanSum: Float = 0f
        set(value) {
            if (value == field)
                return
            notifyPropertyChanged(BR.loanSum)
            field = value
        }

    @get:Bindable
    var payType: PayType = PayType.Differential
        set(value) {
            notifyPropertyChanged(BR.payType)
            field = value
            Log.d("button", value.toString());
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

    @get:Bindable
    var totalPercentage: Float = 0f
        set(value) {
            notifyPropertyChanged(BR.totalPercentage)
            field = value
        }

    @get:Bindable
    var overPay: Float = 0f
        set(value) {
            notifyPropertyChanged(BR.overPay)
            field = value
        }


    @get:Bindable
    var totalPay: Float = 0f
        set(value) {
            notifyPropertyChanged(BR.totalPay)
            field = value
        }

    @get:Bindable
    var monthlyPay: Float = 0f
        set(value) {
            notifyPropertyChanged(BR.monthlyPay)
            field = value
        }    
    @get:Bindable
    var lastMonthlyPay: Float = 0f
        set(value) {
            notifyPropertyChanged(BR.lastMonthlyPay)
            field = value
        }
    
    @get:Bindable
    var overPayPercentage: Float = 0f
        set(value) {
            notifyPropertyChanged(BR.overPayPercentage)
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


    fun calculate() {
        if(payType == PayType.Annual) {
            calculateAnnualLoan()
            return
        }
        
        calculateDifferentialLoan()
    }

    private fun calculateDifferentialLoan() {
        val percent = loanPercentage / 100
        val paymentsPerYear = 12
        val periods = loanTerm*paymentsPerYear;
        data class LoanStatsEntry (val period: Int, val monthlyPay: Float, val leftSum: Float )
        val chart = mutableListOf<LoanStatsEntry>()
        
        for(i in 1..periods) {
            val prevLeftSum = if (i == 1) loanSum else chart[i-2].leftSum
            val monthlyPay = loanSum/periods + prevLeftSum * percent/paymentsPerYear
            val newLeftSum = prevLeftSum - prevLeftSum/paymentsPerYear
            val entry = LoanStatsEntry(i, monthlyPay, newLeftSum)
            chart.add(entry)    
        }
        totalPay = chart.fold(0f) { current, value -> current + value.monthlyPay }
        overPay = totalPay - loanSum
        overPayPercentage = totalPay / loanSum * 100
        lastMonthlyPay = chart[periods-1].monthlyPay
        monthlyPay = chart[1].monthlyPay
        
    }
    private fun calculateAnnualLoan() {
        val percent = loanPercentage / 100
        val paymentsPerYear = 12
        monthlyPay =
            loanSum * percent / paymentsPerYear * (1 + percent / paymentsPerYear).pow(loanTerm * paymentsPerYear) / ((1 + percent / paymentsPerYear).pow(
                loanTerm * paymentsPerYear
            ) - 1)
        totalPay = monthlyPay * loanTerm * paymentsPerYear
        overPay = totalPay - loanSum
        overPayPercentage = totalPay / loanSum * 100
    }

}

