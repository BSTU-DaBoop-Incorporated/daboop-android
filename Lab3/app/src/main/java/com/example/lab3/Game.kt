package com.example.lab3

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.databinding.ObservableFloat
import androidx.databinding.ObservableInt
import androidx.lifecycle.MutableLiveData
import java.io.Serializable
import java.util.*


class Game : Serializable {
    var title = ObservableField<String>("")
    var releaseYear = ObservableField(2022)
    var genre = ObservableField("")
    var rating = ObservableField(0)
    var publisher = ObservableField("")
    var developer = ObservableField("")
    var isInStock = ObservableBoolean(true)
    
}
@kotlinx.serialization.Serializable
data class GameDto (
    var title: String,
    var releaseYear: Int,
    var genre: String,
    var rating: Int,
    var publisher: String,
    val developer: String,
    val isInStock: Boolean
    
  
) {

    fun toGame(): Game {
        val game = Game()
        game.title.set(title)
        game.releaseYear.set(releaseYear)
        game.genre.set(genre)
        game.rating.set(rating)
        game.publisher.set(publisher)
        game.developer.set(developer)
        game.isInStock.set(isInStock)
        return game
    }

    companion object {
        @JvmStatic
        fun fromGame(game: Game) :GameDto {
            val gameDto = GameDto(
                game.title.get()!!,
                game.releaseYear.get()!!,
                game.genre.get()!!,
                game.rating.get()!!,
                game.publisher.get()!!,
                game.developer.get()!!,
                game.isInStock.get()!!
            )
            return gameDto
        }
    }
}