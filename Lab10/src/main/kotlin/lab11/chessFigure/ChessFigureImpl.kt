package lab11.chessFigure

import lab11.ChessBoard

abstract class ChessFigureImpl(
    protected val chessBoard: ChessBoard,
    final override val color: ChessFigure.ChessColor,
    final override val type: ChessFigure.ChessFigureType,
    final override var position: ChessPosition?,
) : ChessFigure {
    init {
        checkValidBoardPosition(position)
        if(chessBoard.getCountOfFigureVariant(color, ChessFigure.ChessFigureType.PAWN) >= chessBoard.MAX_ALLOWED_FIGURES[ChessFigure.ChessFigureType.PAWN]!!) {
            throw IllegalArgumentException("Too many figures of this type")
        }
    }
    
    final override var state = ChessFigure.State(this, chessBoard)

    protected fun canAttackDefault(position: ChessPosition): Boolean {
        checkValidBoardPosition(position)
        if (this.position == null) {
            return false
        }
       
        if(position == this.position) {
            return false
        }

        return isTileOccupiedByEnemy(position)
    }
    
    protected fun canMoveToPositionDefault(position: ChessPosition): Boolean {
        checkValidBoardPosition(position)
        if (this.position == null)
            return false
        return true
    }
    
    protected fun isTileOccupiedByEnemy(position: ChessPosition): Boolean {
        checkValidBoardPosition(position)
        val figure = chessBoard.getFigureAtPosition(position) ?: return false
        return figure.color != color
    }
    
    companion object {
        protected fun checkValidBoardPosition(checkedPosition: ChessPosition?) {
            if (checkedPosition != null && (checkedPosition.first !in 'A'..'H' || checkedPosition.second !in 1..8)) {
                throw IllegalArgumentException("Position must be on the board")
            }
        }
    }
    
    
    final override var history: List<ChessPosition> = listOf()
}