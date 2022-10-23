package com.example.lab9.viewHolder

import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.lab9.BR
import com.example.lab9.model.UserContact

class UserContactViewHolder(private val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {

    lateinit var userContact: UserContact
    
    fun setOnItemClickListener(listener: (UserContact, View) -> Unit) {
        itemView.setOnClickListener { listener(userContact, itemView) }
    }
    fun bind(userContact: UserContact) {
        binding.setVariable(BR.userContact, userContact)
        this.userContact = userContact
    }
}
