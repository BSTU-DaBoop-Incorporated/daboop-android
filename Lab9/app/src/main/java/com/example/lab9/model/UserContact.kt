package com.example.lab9.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserContact(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val name: String? = null,
    val phone: String? = null,
    val email: String? = null,
    val imageUri: String? = null,
) 