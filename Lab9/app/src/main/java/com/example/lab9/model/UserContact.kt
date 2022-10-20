package com.example.lab9.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserContact(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val phone: String,
    val email: String,
) 