package lab11
import lab11.chessFigure.ChessFigure
import lab11.chessFigure.ChessFigure.ChessFigureType
import lab11.chessFigure.ChessFigure.ChessFigureType.*
import lab11.chessFigure.ChessPosition
import lab11.chessFigure.concreteChessFigure.Pawn
import lab11.chessFigure.concreteChessFigure.Rook

class ChessBoard {
    val MAX_ALLOWED_FIGURES = mapOf<ChessFigureType, Int>(
        PAWN to 8,
        ROOK to 2,
        KNIGHT to 2,
        BISHOP to 2,
        QUEEN to 1,
        KING to 1,
    )
    
    private var _chessFigures = mutableListOf<ChessFigure>()
    val chessFigures: List<ChessFigure>
        get() = _chessFigures.toList()
    
    fun isTileClear(position: ChessPosition): Boolean {
        getFigureAtPosition(position)?.let {
            return false
        }
        return true
    }

    fun getFigureAtPosition(position: ChessPosition): ChessFigure? {
        return chessFigures.firstOrNull { it.position == position }
    }
    
    fun getCountOfFigureVariant(color: ChessFigure.ChessColor, type: ChessFigure.ChessFigureType): Int {
        return this.chessFigures.count {chessFigure -> chessFigure.color == color && chessFigure.type == type }
    }
    
    
    fun addFigure(color:ChessFigure.ChessColor, type: ChessFigure.ChessFigureType, position: ChessPosition): ChessFigure {
        if (getCountOfFigureVariant(color, type) >= MAX_ALLOWED_FIGURES[type]!!) {
            throw IllegalArgumentException("Too many figures of this type")
        }
        if (!isTileClear(position)) {
            throw IllegalArgumentException("Tile is not clear")
        }
        val figure = when(type) {
            PAWN -> Pawn(this, color, position)
            ROOK -> Rook(this, color, position)
            else -> throw IllegalArgumentException("Unknown figure type")
        }

        _chessFigures += figure
        return figure
    }
    
}