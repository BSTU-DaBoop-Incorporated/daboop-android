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
    chessBoard.addFigure(WHITE, ROOK, ChessPosition('B', 6))
    chessBoard.addFigure(BLACK, ROOK, ChessPosition('G', 6))
    chessBoard.addFigure(BLACK, PAWN, ChessPosition('E', 2))
    val pawn = chessBoard.addFigure(BLACK, PAWN, ChessPosition('A', 7))

    val rook = chessBoard.addFigure(WHITE, ROOK, ChessPosition('E', 6))
    
    ChessBoardPrinter.drawInConsole(chessBoard, pawn)
    println()
    print(rook.state.availablePositions)


    chessBoard.removeFigure(pawn.position!!)
    ChessBoardPrinter.drawInConsole(chessBoard, rook)
    println()

    val a = A(1)
    a.setUp()
    a.display()
    val b = A(2)
    val c = A(1)
    val sum = a + b
    println("sum: ${sum.a}\nc==a: ${c == a}")
    
    println("3 5 sum: ${converter('+')(3.0, 5.0)}\n3 5 sub: ${converter('-')(3.0, 5.0)}")

}