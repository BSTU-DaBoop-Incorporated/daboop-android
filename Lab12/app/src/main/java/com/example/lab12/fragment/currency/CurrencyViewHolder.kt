package com.example.lab12.fragment.currency

import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.lab12.BR
import com.example.lab12.fragment.currency.Currency

class CurrencyViewHolder(private val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {

    lateinit var currency: Currency
    
    fun setOnItemClickListener(listener: (Currency, View) -> Unit) {
        itemView.setOnClickListener { listener(currency, itemView) }
    }
    fun bind(Currency: Currency) {
        binding.setVariable(BR.currency, Currency)
        this.currency = Currency
    }
}
