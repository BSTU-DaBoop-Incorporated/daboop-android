package com.example.lab3

import android.os.Bundle
import android.view.View
import androidx.navigation.ui.AppBarConfiguration
import androidx.activity.viewModels
import com.example.lab3.databinding.ActivityMainBinding

class MainActivity : BaseFormActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()

        setContentView(R.layout.activity_main)
    }

    fun next(_view: View) {
        super.next()
    }
}