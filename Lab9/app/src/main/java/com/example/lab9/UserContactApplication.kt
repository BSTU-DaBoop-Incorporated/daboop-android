package com.example.lab9

import android.app.Application
import com.example.lab9.room.database.UserContactDatabase

class UserContactApplication :Application() {
    val database by lazy { UserContactDatabase.getDatabase(this) }
    
    
}