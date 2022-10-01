package com.example.lab3

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.UUID

abstract class BaseFormActivity : AppCompatActivity() {
    lateinit var viewModel: GameViewModel
    lateinit var layoutViewModel: LayoutViewModel
    val downloadsFolder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString()
    val fileName = "$downloadsFolder/games.json"
    val game: Game?
        get() = viewModel.game.get()

    val games
        get() = viewModel.games.value
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
        if (game != null) {
            outState.putSerializable("game", game)
        }
        
        if(games != null) {
            outState.putSerializable("games", games as java.io.Serializable)
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        Log.d("BaseFormActivity", "onRestoreInstanceState: $game")
        val game = savedInstanceState.getSerializable("game") as Game
        val games = savedInstanceState.getSerializable("games") as MutableList<Game>
        viewModel.game.set(game)
        viewModel.games.value = games

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("BaseFormActivity", "onCreate")
        val vm: GameViewModel by viewModels()

        viewModel = vm
        layoutViewModel = LayoutViewModel(this)
        layoutViewModel.baseFormActivity = this
        val gameFromIntent = intent.getSerializableExtra("game") as Game?
        val gamesFromIntent = intent.getSerializableExtra("games") as MutableList<Game>?
        if (gameFromIntent != null) {
            viewModel.game.set(gameFromIntent)
        }
        if(gamesFromIntent != null) {
            viewModel.games.value = gamesFromIntent
        }

        super.onCreate(savedInstanceState)
    }

    private fun sendGameIntent(activity: Class<out Activity>) {
        sendGameIntent(game, games, activity)
    }

    private val stepClassMapping = mapOf(
        0 to MainActivity::class.java,
        1 to SecondActivity::class.java,
        2 to ThirdActivity::class.java,
        3 to FinalActivity::class.java,
    )
    private val reverseStepClassMapping = stepClassMapping.entries.associate { (k, v) -> v to k }
    protected fun sendGameIntent(game: Game?, games: MutableList<Game>?, activity: Class<out Activity>) {
        val intent = Intent(this, activity)
        intent.type = "game intent"
        intent.putExtra("game", game)
        intent.putExtra("games", games as java.io.Serializable)
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
    
    fun goToList() {
        val intent = Intent(this, ListActivity::class.java)
        startActivity(intent)
    }
    
    fun saveButtonClick() {
        saveStateInFile()
        goToList()
    }
    fun saveStateInFile() {
//        val fileName = "$downloadsFolder/game.json"

        
        if (game?.id?.get()  != null) {
            viewModel.games.value?.removeAll { it.id.get() == game!!.id.get() }
        }
        else {
            game?.id?.set(UUID.randomUUID().toString())
        }
        viewModel.games.value?.add(game!!)
//        val gameDto = GameDto.fromGame(game!!)
        val gamesDtos = viewModel.games.value?.map { GameDto.fromGame(it) }
        val jsonString = Json.encodeToString(gamesDtos)
        FileHelpers.create(fileName, jsonString)
        
        Toast.makeText(this, "Game saved", Toast.LENGTH_SHORT).show()
        
    }

    fun readStateFromFile() {
//        val downloadsFolder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString()
//        val fileName = "$downloadsFolder/game.json"
        val jsonString = FileHelpers.read(fileName)
        if (jsonString != null) {
            try {

                val games: MutableList<GameDto> = Json.decodeFromString(jsonString)
                viewModel.games.value = games.map { it.toGame() }.toMutableList()

            }
            catch (e: RuntimeException) {
                Log.d("BaseFormActivity", "readStateFromFile: $e")
            }
        }
        
    }

}