package com.example.lab9.adapter

import android.content.Context
import android.view.LayoutInflater
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

class UserContactListAdapter(private val Context: Context, private val UserContacts: 
LiveData<List<UserContact>>) : 
    RecyclerView
.Adapter<UserContactViewHolder>
    () {
    init {
        UserContacts.observe(Context as LifecycleOwner) { notifyDataSetChanged() }
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
    }

    override fun getItemCount(): Int {
        return UserContacts.value!!.size
    }

}