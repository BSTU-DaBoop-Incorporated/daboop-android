package com.example.lab12.fragment.currency

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.lab12.R

class CurrencyListAdapter(private val lifecycleOwner: LifecycleOwner, private val Currencys: 
LiveData<List<Currency>>, private val clickListener: ( (Currency, View) -> Unit)?
= null) : 
    RecyclerView
.Adapter<CurrencyViewHolder>
    () {
    init {
        Currencys.observe(lifecycleOwner) { it ->
            notifyDataSetChanged()
        }
    }
    
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val binding: ViewDataBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.currency_card,
            parent,
            false)
        return CurrencyViewHolder(binding)
    }
    
    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        holder.bind(Currencys.value!![position])
        clickListener?.let { holder.setOnItemClickListener(it) }
    }

    override fun getItemCount(): Int {
        return Currencys.value?.size ?: 0
    }

}