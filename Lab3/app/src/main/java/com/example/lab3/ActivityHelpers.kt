package com.example.lab3

import android.app.Activity
import android.content.Context
import android.content.Intent
import java.util.*

object ActivityHelpers {
    @JvmStatic
    fun Activity.sendGameIntent (game: Game, activity: Class<out Activity> )  {
        val intent = Intent(this, activity)
        intent.putExtra("game", game)
        startActivity(intent)
    }
}