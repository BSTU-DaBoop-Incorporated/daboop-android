package com.example.lab3

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

abstract class BaseFormActivity : AppCompatActivity() {
    lateinit var viewModel: GameViewModel
    lateinit var layoutViewModel: LayoutViewModel

    val game: Game?
        get() = viewModel.game.get()

    companion object {
        @JvmStatic
        val stepClassMapping = mapOf(
            0 to MainActivity::class.java,
            1 to SecondActivity::class.java,
            2 to ThirdActivity::class.java,
            3 to FinalActivity::class.java,
        )
        @JvmStatic
        val reverseStepClassMapping = stepClassMapping.entries.associate { (k, v) -> v to k }

    }
 

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d("BaseFormActivity", "onRestoreInstanceState: $game")
        if (game == null) return
        outState.putSerializable("game", game)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        Log.d("BaseFormActivity", "onRestoreInstanceState: $game")
        val game = savedInstanceState.getSerializable("game") as Game
        viewModel.game.set(game)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("BaseFormActivity", "onCreate")
        val vm: GameViewModel by viewModels()

        viewModel = vm
        layoutViewModel = LayoutViewModel(this)
        layoutViewModel.baseFormActivity = this
        val intentFromGame = intent.getSerializableExtra("game") as Game?
        if (intentFromGame != null) {
            viewModel.game.set(intentFromGame)
        }

        super.onCreate(savedInstanceState)
    }

    private fun sendGameIntent(activity: Class<out Activity>) {
        sendGameIntent(game, activity)
    }

    private val stepClassMapping = mapOf(
        0 to MainActivity::class.java,
        1 to SecondActivity::class.java,
        2 to ThirdActivity::class.java,
        3 to FinalActivity::class.java,
    )
    private val reverseStepClassMapping = stepClassMapping.entries.associate { (k, v) -> v to k }
    protected fun sendGameIntent(game: Game?, activity: Class<out Activity>) {
        val intent = Intent(this, activity)
        intent.type = "game intent"
        intent.putExtra("game", game)
        startActivity(intent)
    }

    fun goToStep(step: Int) {
        val activityClass = stepClassMapping[step]
            ?: throw Error("No such step exists")
        sendGameIntent(activityClass)
    }

    fun next() {
        val currentStep = getCurrentStep()
        val newStep = (stepClassMapping[currentStep + 1]
            ?: stepClassMapping[0]) as Class<out BaseFormActivity>
        sendGameIntent(newStep)
    }

    fun getCurrentStep(): Int {
        return reverseStepClassMapping[this::class.java]
            ?: throw Error("No such step exists")
    }
    
    fun saveStateInFile() {
        val downloadsFolder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString()
        val fileName = "$downloadsFolder/game.json"

        
        val gameDto = GameDto.fromGame(game!!)
        val jsonString = Json.encodeToString(gameDto)
        FileHelpers.create(fileName, jsonString)
        
        Toast.makeText(this, "Game saved", Toast.LENGTH_SHORT).show()
        
    }

    fun readStateFromFile() {
        val downloadsFolder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString()
        val fileName = "$downloadsFolder/game.json"
        val jsonString = FileHelpers.read(fileName)
        if (jsonString != null) {
            try {

                val game: GameDto = Json.decodeFromString(jsonString)
                viewModel.game.set(game.toGame())

            }
            catch (e: RuntimeException) {
                Log.d("BaseFormActivity", "readStateFromFile: $e")
            }
        }
    }

}