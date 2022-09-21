package com.example.lab3

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.*

abstract class BaseFormActivity : AppCompatActivity() {
    lateinit var viewModel: GameViewModel
    lateinit var layoutViewModel: LayoutViewModel

    val game: Game?
        get() = viewModel.game.get()

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d("BaseFormActivity", "onRestoreInstanceState: $game")
        if (game == null) return
        outState.putSerializable("game", game)
        
        saveStateInFile()
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




    protected fun read(context: Context, fileName: String): String? {
        return try {
            val fis: FileInputStream = context.openFileInput(fileName)
            val isr = InputStreamReader(fis)
            val bufferedReader = BufferedReader(isr)
            val sb = StringBuilder()
            var line: String?
            while (bufferedReader.readLine().also { line = it } != null) {
                sb.append(line)
            }
            sb.toString()
        } catch (fileNotFound: FileNotFoundException) {
            null
        } catch (ioException: IOException) {
            null
        }
    }

    protected fun create(context: Context, fileName: String, jsonString: String?) {
        
            val fos: FileOutputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE)
            if (jsonString != null) {
                fos.write(jsonString.toByteArray())
            }
            fos.close()
    }

    protected fun isFilePresent(context: Context, fileName: String): Boolean {
        val path: String = "${context.filesDir.absolutePath}/$fileName"
        val file = File(path)
        return file.exists()
    }
    
    protected fun saveStateInFile() {
        val fileName = "game.json"
        
        val gameDto = GameDto.fromGame(game!!)
        val jsonString = Json.encodeToString(gameDto)
        create(this, fileName, jsonString)
    }
    
    protected fun readStateFromFile() {
        val fileName = "game.json"
        val jsonString = read(this, fileName)
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