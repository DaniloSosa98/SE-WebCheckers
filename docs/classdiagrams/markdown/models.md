classDiagram


class Board {
	-[Row] gameBoard
	-final int BOARD_LENGTH = 8
	+placeRedPieces()
	+placeWhitePieces()
}

class Game {
	- redPlayer: Player
	- whitePlayer: Player
	- activeColor: Piece.Color
	- boardView: BoardView
	- id: String
	- gameIsOver: boolean
	- gameOverReason: GameOverReason
	- final int NUM_ROWS_COLS = 7
	+ void makeMove(move: Move, boolean: isBackwards)
    + void changeTurn()
    + void playerResign()
    + Player getActivePlyaer()
    + Player getOtherPlayer()
}


class Piece {
	+ enum Type (SINGLE, KING)
	+ enum Color (RED, WHITE)
	- color: Piece.Color
	- index: int
	- type: Piece.Type
	+ void makeKing()
	+ void promoteToKingCheck(targetColor: Color, boardView: BoardView)$
}

class Player {
	- username: String
	- isPlaying: boolean
	- isReplaying: boolean
	- color: Piece.Color
	- pieceCount: Int
	+ void incrementPieceCount()
	+ void decrementPieceCount()
}

class Move {
	- Board board
	- Position start
	- Position end
	// Various move states...
	+ Position getJumpMidpoint(start: Position, end: Position)
	+ boolean isMoveJump()
	- redValidationSetup()
	- whiteValidationSetup()
	+ boolean isValid(movingPiece: Piece)
	+ boolean jumpAvailable(pieceColor: Piece.Color)
	- boolean canJumpFwdLeft(spot: Position)
	- boolean canJumpFwdRight(spot: Position)
	- boolean canJumpBackLeft(piece: Piece, spot: Position)
	- boolean canJumpBackRight(piece: Piece, spot: Position)
	+ boolean doesSpaceHaveJumpMove(position: Position, piece: Piece)
	+ Move flipOrientation()
	+ private simpleMoveAvailable(pieceColor: Piece.Color)
	- boolean campMovFwdLeft(piece: Piece, spot: Position)
	- boolean campMovFwdRight(piece: Piece, spot: Position)
	- boolean campMovBackLeft(piece: Piece, spot: Position)
	- boolean campMovBackRight(piece: Piece, spot: Position)
}
class Position {
	- int row
	- int cell
	+ Position flip()
}

class Replay {
	- moves: List<Move>
	- moveIterator: ListIterator<Move>
	- gameID: String
	- index: int
	+ boolean hasNextMove()
	+ boolean hasPreviousMove()
    + Move getNextMove()
    + Move getPreviousMove()
    + boolean isInitialState()
}

class Row {
	- final int ROW_LENGTH = 8
	- [Space] row
	- int index
	+ Row flip()
}

class RowIterator {
	- int cursor
	+ boolean hasNext()
	+ Space next()
}

class Space {
	+ SpaceColor = (BLACK, WHITE)
	- Piece piece;
	- boolean isValid
	- int cellIdx
	- SpaceColor spaceColor
	+ void putWhitePiece()
	+ void putRedPiece()
	+ void makeSpaceWhite()
	+ void putPiece(piece: Piece)
	+ boolean isValid()
}

class BoardView {
	- Board board
	+ Board getBoard()
}

class BoardIterator {
	- int curr
	+ boolean hasNext()
	+ Row next()
}


Row*--RowIterator
BoardView*--BoardIterator

Board-->Row
Board-->Player
Board..>Move
Board..>Position

Game-->Player
Game-->Piece
Game-->Board
Game..>Move

Move-->Board
Move-->Position
Move..>Row
Move..>Piece

Piece..>Board

Player-->Piece

Replay-->Move

Row-->Space

Space-->Piece

BoardView-->Board
BoardView..>Player
BoardView..>Move


