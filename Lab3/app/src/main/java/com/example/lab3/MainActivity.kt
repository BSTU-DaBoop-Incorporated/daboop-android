package com.example.lab3

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.lab3.databinding.ActivityMainBinding

class MainActivity : BaseFormActivity(){
    private lateinit var binding: ActivityMainBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        
        Log.i("MainActivity", "Save Path!!:" + this.filesDir.absolutePath)
        if (intent?.type?.equals("game intent") != true) {
            readStateFromFile()
        }
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.layoutViewModel = layoutViewModel

    }

    fun next(_view: View) {
        super.next()
    }
    
}