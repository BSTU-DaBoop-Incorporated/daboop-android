package lab11.chessFigure

import lab11.ChessBoard

typealias ChessPosition = Pair<Char, Int>
interface ChessFigure {
    enum class ChessColor {
        BLACK,
        WHITE
    }

    enum class ChessFigureType {
        PAWN,
        ROOK,
        KNIGHT,
        BISHOP,
        QUEEN,
        KING
    }
    class State(val figure: ChessFigure, val chessBoard: ChessBoard) {
        val isUnderAttack: Boolean
            get() {
                if (figure.position == null) {
                    return false
                }
                if (chessBoard.chessFigures.any { figure -> figure.canAttack(figure.position!!) }) {
                    return true
                }
                return false;
            }
        val availablePositions: List<ChessPosition>
            get() {
                val list = mutableListOf<ChessPosition>()
                for (i in 'A'..'H') {
                    for (j in 1..8) {
                        val pos = ChessPosition(i, j)
                        if (figure.canMoveToPosition(pos)) {
                            list.add(pos)
                        }
                    }
                }
                return list
            }
    }

    
    val state: State
    val color: ChessColor
    val type: ChessFigureType
    var position: ChessPosition? 
    var history: List<ChessPosition>
    // wouldn't use var for pos and history, but the task requires move to have default implementation,
    // and it's impossible to use protected setters in interface
    fun canAttack(position: ChessPosition): Boolean
    fun canMoveToPosition(position: ChessPosition): Boolean
    fun move(newPosition: ChessPosition) {
        if(!canMoveToPosition(newPosition)) {
            throw IllegalArgumentException("Can't move $color $type to this position")
        }   
        position = newPosition
        history = history + newPosition
    }
}