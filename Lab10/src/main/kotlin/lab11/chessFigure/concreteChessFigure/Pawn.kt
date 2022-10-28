package lab11.chessFigure.concreteChessFigure

import lab11.ChessBoard
import lab11.chessFigure.ChessFigure
import lab11.chessFigure.ChessFigureImpl
import lab11.chessFigure.ChessPosition

class Pawn(chessBoard: ChessBoard, color: ChessFigure.ChessColor, position: ChessPosition?): ChessFigureImpl(chessBoard, color, ChessFigure.ChessFigureType.PAWN, position) {
    constructor(chessBoard: ChessBoard, color: ChessFigure.ChessColor) : this(chessBoard, color, null) {
        
    }
    
    init {
        if (position != null) {
            if (color == ChessFigure.ChessColor.WHITE && position.second == 1) {
                throw IllegalArgumentException("White pawn can't be on 1st rank")
            }
            if (color == ChessFigure.ChessColor.BLACK && position.second == 8) {
                throw IllegalArgumentException("Black pawn can't be on 8th rank")
            }
        }
    }
    
    
    override fun canAttack(position: ChessPosition): Boolean {
        if (!canAttackDefault(position)) {
            return false
        }
            
        if (color == ChessFigure.ChessColor.WHITE) {
            return position.first == this.position!!.first + 1 && position.second == this.position!!.second + 1 ||
                    position.first == this.position!!.first - 1 && position.second == this.position!!.second + 1
        }
        return position.first == this.position!!.first + 1 && position.second == this.position!!.second - 1 ||
                position.first == this.position!!.first - 1 && position.second == this.position!!.second - 1
    }
    override fun canMoveToPosition(position: ChessPosition): Boolean {
        if (!canMoveToPositionDefault(position)) {
            return false
        }

        if(canAttack(position)) {
            return true
        }

        val (x, y) = this.position!!
        val (newX, newY) = position
        return when (color) {
            ChessFigure.ChessColor.WHITE -> {
                if (y == 2)
                    (newY == 3 || newY == 4) && newX == x
                else
                    newY == y + 1 && newX == x
            }
            ChessFigure.ChessColor.BLACK -> {
                if (y == 7)
                    (newY == 6 || newY == 5) && newX == x
                else
                    newY == y - 1 && newX == x
            }
        }
    }
}