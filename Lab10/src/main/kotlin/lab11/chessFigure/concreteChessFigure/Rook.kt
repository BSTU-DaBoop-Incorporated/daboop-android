package lab11.chessFigure.concreteChessFigure

import lab11.ChessBoard
import lab11.chessFigure.ChessFigure
import lab11.chessFigure.ChessFigureImpl
import lab11.chessFigure.ChessPosition

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
            for (i in y..newY) {
                if (!chessBoard.isTileClear(ChessPosition(x, i))) {
                    return false
                }
            }
            return isTileOccupiedByEnemy(position)
        }
        if (y == newY) {
            for (i in x..newX) {
                if (!chessBoard.isTileClear(ChessPosition(i, y))) {
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
            for (i in y..newY) {
                if (!chessBoard.isTileClear(ChessPosition(x, i))) return false
            }
            return true
        }
        if (y == newY) {
            for (i in x..newX) {
                if (!chessBoard.isTileClear(ChessPosition(i, y))) return false
            }
            return true
        }
        
        return false;
    }


}