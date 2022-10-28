package lab11
import  lab11.chessFigure.ChessFigure.ChessFigureType.*
import  lab11.chessFigure.ChessFigure.ChessColor.*
import lab11.chessFigure.ChessPosition

fun main() {
    val chessBoard = ChessBoard()
    
    chessBoard.addFigure(BLACK, PAWN, ChessPosition('A', 2))
    chessBoard.addFigure(BLACK, PAWN, ChessPosition('B', 2))
    chessBoard.addFigure(BLACK, PAWN, ChessPosition('C', 2))
    chessBoard.addFigure(WHITE, PAWN, ChessPosition('D', 8))
    val figure = chessBoard.addFigure(WHITE, ROOK, ChessPosition('E', 6))
    
    ChessBoardPrinter.drawInConsole(chessBoard)
    println()
    print(figure.state.availablePositions)
}