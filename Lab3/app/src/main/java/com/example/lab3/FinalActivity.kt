package com.example.lab3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.lab3.databinding.ActivityFinalBinding
import com.example.lab3.databinding.ActivitySecondBinding

class FinalActivity : BaseFormActivity() {
    private lateinit var binding: ActivityFinalBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_final)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.layoutViewModel = layoutViewModel

    }

    fun next(_view: View) {
        super.next()
    }
}