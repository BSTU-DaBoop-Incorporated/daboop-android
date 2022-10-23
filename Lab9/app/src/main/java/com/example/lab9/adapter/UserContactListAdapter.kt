package com.example.lab9.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.lab9.R
import com.example.lab9.model.UserContact
import com.example.lab9.viewHolder.UserContactViewHolder
import kotlin.reflect.KFunction1

class UserContactListAdapter(private val lifecycleOwner: LifecycleOwner, private val UserContacts: 
LiveData<List<UserContact>>, private val clickListener: ( (UserContact, View) -> Unit)?
= null) : 
    RecyclerView
.Adapter<UserContactViewHolder>
    () {
    init {
        UserContacts.observe(lifecycleOwner) { it ->
            notifyDataSetChanged()
        }
    }
    
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserContactViewHolder {
        val binding: ViewDataBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.user_contact_card,
            parent,
            false)
        return UserContactViewHolder(binding)
    }
    
    override fun onBindViewHolder(holder: UserContactViewHolder, position: Int) {
        holder.bind(UserContacts.value!![position])
        clickListener?.let { holder.setOnItemClickListener(it) }
    }

    override fun getItemCount(): Int {
        return UserContacts.value?.size ?: 0
    }

}