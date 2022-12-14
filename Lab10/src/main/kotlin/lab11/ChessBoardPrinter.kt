package lab11

import lab11.chessFigure.ChessFigure
import lab11.chessFigure.ChessFigure.ChessFigureType.*
import lab11.chessFigure.ChessPosition


object ChessBoardPrinter {
    @JvmStatic
    val figurePrintSymbolMap = mapOf(
        PAWN to 'P',
        ROOK to 'R'
        
    )
    @JvmStatic
    fun drawInConsole(board: ChessBoard, watchedFigure: ChessFigure? = null) {
        val yellowBg = "\u001B[43m"
        val blueBg = "\u001B[44m"
        val red = "\u001b[31m"
        val reset = "\u001b[0m"
        
        print("  ")
        for (i in 1..8) {
            print(" ${(i+'A'.code-1).toChar()} ")
        }
        println()
        for (y in 8 downTo 1) {
            for (x in 'A'..'H') {
                if (x == 'A') {
                    print("$y  ")
                }
                val position = ChessPosition(x, y)
                val figure = board.getFigureAtPosition(position)
                val symbol = figure?.let {
                    figurePrintSymbolMap[figure.type]
                } ?: ' '
                val color = figure?.color?.let {
                    if (it == ChessFigure.ChessColor.BLACK) red else ""
                } ?: ""
                val bg = if (watchedFigure?.canAttack(position) == true) blueBg else  if (watchedFigure?.canMoveToPosition(position) == true) yellowBg else ""
                print("$bg$color$symbol  $reset")
            }
            println()
        }
    }
}