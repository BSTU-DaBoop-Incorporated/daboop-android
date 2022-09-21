package com.example.lab3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.lab3.databinding.ActivityMainBinding
import com.example.lab3.databinding.ActivitySecondBinding

class SecondActivity : BaseFormActivity() {
    private lateinit var binding: ActivitySecondBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_second)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.layoutViewModel = layoutViewModel

    }
    
    fun next(_view: View) {
        super.next()
    }
}