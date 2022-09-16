package com.example.lab3

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.util.Log.INFO
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.lab3.databinding.ActivityMainBinding
import kotlin.math.log

abstract class BaseFormActivity: AppCompatActivity() {
    protected lateinit var viewModel: GameViewModel
    private lateinit var binding: ActivityMainBinding
    
    protected fun init() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        val vm: GameViewModel by viewModels()
        viewModel = vm
        binding.viewModel = vm
//        binding.activity = this
        vm.game = (intent.getSerializableExtra("game") ?: Game()) as Game
        Log.d("init", vm.game.toString())

    }
    private fun sendGameIntent(activity: Class<out Activity>) {
        sendGameIntent(binding.viewModel?.game!!, activity)
    }
    
    private val stepClassMapping = mapOf(
        0 to MainActivity::class.java,
        1 to SecondActivity::class.java
    )
    private val reverseStepClassMapping = stepClassMapping.entries.associate { (k, v) -> v to k }
    protected fun sendGameIntent (game: Game, activity: Class<out Activity> )  {
        val intent = Intent(this, activity)
        intent.putExtra("game", game)
        startActivity(intent)
    }
    fun goToStep(step: Int) {
        val activityClass = stepClassMapping[step] 
            ?: throw Error("No such step exists")
        sendGameIntent(activityClass)
    }
    fun next() {
        val currentStep = reverseStepClassMapping[this::class.java] 
            ?: throw Error("No such step exists")
        val newStep = stepClassMapping[currentStep+1] ?: stepClassMapping[0]
        sendGameIntent(newStep!!)
    }
    
    
}