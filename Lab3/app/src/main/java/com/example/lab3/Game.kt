package com.example.lab3

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import java.io.Serializable
import java.util.*


class Game : Serializable {
    var id = ObservableField<String>()
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
    var id : String,
    var title: String?,
    var releaseYear: Int?,
    var genre: String?,
    var rating: Int?,
    var publisher: String?,
    val developer: String?,
    val isInStock: Boolean = false

    
  
) {

    fun toGame(): Game {
        val game = Game()
        game.id.set(id)
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
        fun fromGame(game: Game): GameDto {
            return GameDto(
                game.id.get() ?: UUID.randomUUID().toString(),
                game.title.get(),
                game.releaseYear.get(),
                game.genre.get(),
                game.rating.get(),
                game.publisher.get(),
                game.developer.get(),
                game.isInStock.get()
            )
        }
    }
}