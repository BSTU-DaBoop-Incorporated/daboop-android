package com.example.lab9.viewHolder

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.lab9.BR
import com.example.lab9.model.UserContact

class UserContactViewHolder(private val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(userContact: UserContact) {
        binding.setVariable(BR.userContact, userContact)
    }
}
