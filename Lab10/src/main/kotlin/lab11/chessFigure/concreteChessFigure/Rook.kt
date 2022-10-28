package lab11.chessFigure.concreteChessFigure

import lab11.ChessBoard
import lab11.chessFigure.ChessFigure
import lab11.chessFigure.ChessFigureImpl
import lab11.chessFigure.ChessPosition
import kotlin.math.min

class Rook(chessBoard: ChessBoard, color: ChessFigure.ChessColor, position: ChessPosition?): ChessFigureImpl(chessBoard, color, ChessFigure.ChessFigureType.ROOK, position) {
    constructor(chessBoard: ChessBoard, color: ChessFigure.ChessColor) : this(chessBoard, color, null) {

    }

    override fun canAttack(position: ChessPosition): Boolean {
        if(!canAttackDefault(position)) return false
        
        val (x, y) = this.position!!
        val (newX, newY) = position

        
        if (x!= newX && y!= newY)
            return false
        if (x == newX) {
            val minY = if (y < newY) y else newY
            val maxY = if (y > newY) y else newY
            for (i in minY..maxY) {
                if(y == i) continue
                if (!chessBoard.isTileClear(ChessPosition(x, i)) && !isTileOccupiedByEnemy(position)) {
                    return false
                }
            }
            return isTileOccupiedByEnemy(position)
        }
        if (y == newY) {
            val minX = if (x < newX) x else newX
            val maxX = if (x > newX) x else newX
            for (i in minX..maxX) {
                if(x == i) continue
                if (!chessBoard.isTileClear(ChessPosition(i, y)) && !isTileOccupiedByEnemy(position)) {
                    return false
                }
            }
            return isTileOccupiedByEnemy(position)
        }
        
        return false
    }

    override fun canMoveToPosition(position: ChessPosition): Boolean {
        if (!canMoveToPositionDefault(position)) return false
        if(canAttack(position)) return true
        
        val (x, y) = this.position!!
        val (newX, newY) = position
        
        if (x!= newX && y!= newY)
            return false
        
        if (x == newX) {
            val minY = if (y < newY) y else newY
            val maxY = if (y > newY) y else newY
            for (i in minY..maxY) {
                if(y == i) continue
                if (!chessBoard.isTileClear(ChessPosition(x, i))) return false
            }
            return true
        }
        if (y == newY) {
            val minX = if (x < newX) x else newX
            val maxX = if (x > newX) x else newX
            for (i in minX..maxX) {
                if(x == i) continue
                if (!chessBoard.isTileClear(ChessPosition(i, y))) return false
            }
            return true
        }
        
        return false;
    }


}