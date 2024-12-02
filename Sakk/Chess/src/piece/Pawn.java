package piece;

import main.CheckRules;
import main.EnPassantRules;

public class Pawn extends ChessPiece {
    private boolean isFirstMove = true;

    public Pawn(PieceColor color, int row, int col) {
        super(PieceType.PAWN, color, color == PieceColor.WHITE ?
                        "C:/_3._félév/A programozás alapjai 3/Sakk/pieces-basic-png/white-pawn.png" :
                        "C:/_3._félév/A programozás alapjai 3/Sakk/pieces-basic-png/black-pawn.png",
                row, col);
    }

    @Override
    public boolean isValidMove(int startRow, int startCol, int endRow, int endCol, ChessPiece[][] board) {
        // Ellenőrizzük a célbábukat
        ChessPiece destinationPiece = board[endRow][endCol];
        if (destinationPiece != null && (destinationPiece.getColor() == this.getColor() || destinationPiece.getType() == PieceType.KING)) {
            return false;
        }

        int direction = (getColor() == PieceColor.WHITE) ? -1 : 1;

        if (startCol == endCol && board[endRow][endCol] == null) {
            if (endRow == startRow + direction || (isFirstMove && endRow == startRow + 2 * direction)) {
                return true; // Single or double move forward
            }
        } else if (Math.abs(startCol - endCol) == 1 && endRow == startRow + direction) {
            if ((board[endRow][endCol] != null && board[endRow][endCol].getColor() != this.getColor()) ||
                    EnPassantRules.canEnPassant(board, this, endRow, endCol)) {
                return true; // Capturing move or en passant
            }
        }
        return false;
    }

    public ChessPiece promote(String newPieceType) {
        if ((getColor() == PieceColor.WHITE && getRow() == 0) || (getColor() == PieceColor.BLACK && getRow() == 7)) {
            switch (newPieceType.toUpperCase()) {
                case "ROOK":
                    return new Rook(getColor(), getRow(), getCol());
                case "BISHOP":
                    return new Bishop(getColor(), getRow(), getCol());
                case "QUEEN":
                    return new Queen(getColor(), getRow(), getCol());
                case "KNIGHT":
                    return new Knight(getColor(), getRow(), getCol());
                default:
                    throw new IllegalArgumentException("Invalid promotion piece type: " + newPieceType);
            }
        }
        throw new IllegalStateException("Pawn is not in a promotion position");
    }

    @Override
    public void setPosition(int row, int col) {
        super.setPosition(row, col);
        isFirstMove = false;
    }

    public Pawn clone() {
        return (Pawn) super.clone();
    }
}