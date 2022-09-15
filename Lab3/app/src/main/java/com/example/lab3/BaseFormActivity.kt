package com.example.lab3

import android.app.Activity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.Switch
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import com.example.lab3.ActivityHelpers.sendGameIntent
import com.example.lab3.databinding.ActivityMainBinding

abstract class BaseFormActivity: AppCompatActivity() {
    protected lateinit var viewModel: GameViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        init()
    }
    fun init() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        val vm: GameViewModel by viewModels()
        binding.viewModel = vm
        binding.activity = this
        viewModel = vm

    }
    private fun sendGameIntent(activity: Class<out Activity>) {
        sendGameIntent(viewModel.game, activity)
    }
    fun next() {
        
        when(this) {
            is MainActivity -> sendGameIntent(SecondActivity::class.java)
            is SecondActivity -> sendGameIntent(MainActivity::class.java)
                    
        }
    }
        
//    fun next(_view: View) {
//        next()
//    }
    
}