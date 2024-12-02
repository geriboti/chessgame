package piece;

import main.CheckRules;
import main.CastlingRules;

public class King extends ChessPiece {

    private boolean isFirstMove = true;

    public King(PieceColor color, int row, int col) {
        super(PieceType.KING, color, color == PieceColor.WHITE ?
                        "C:/_3._félév/A programozás alapjai 3/Sakk/pieces-basic-png/white-king.png" :
                        "C:/_3._félév/A programozás alapjai 3/Sakk/pieces-basic-png/black-king.png",
                row, col);
    }

    @Override
    public boolean isValidMove(int startRow, int startCol, int endRow, int endCol, ChessPiece[][] board) {
        // Ellenőrizzük, hogy a célmezőn lévő bábu nem azonos színű-e
        if (!checkDestinationPiece(endCol, endRow, board)) {
            return false;
        }

        int rowDiff = Math.abs(startRow - endRow);
        int colDiff = Math.abs(startCol - endCol);

        // Ellenőrizzük, hogy a király nem léphet ugyanarra a helyre
        if (startRow == endRow && startCol == endCol) {
            return false;
        }

        // Ellenőrizzük, hogy a két király nem lehet egymás közvetlen közelében
        if (rowDiff <= 1 && colDiff <= 1) {
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    int newRow = endRow + i;
                    int newCol = endCol + j;
                    if (newRow >= 0 && newRow < board.length && newCol >= 0 && newCol < board[0].length) {
                        ChessPiece adjacentPiece = board[newRow][newCol];
                        if (adjacentPiece != null && adjacentPiece.getType() == PieceType.KING && adjacentPiece.getColor() != this.getColor()) {
                            return false; // Nem engedjük meg, hogy a két király közvetlen közelében legyen egymásnak
                        }
                    }
                }
            }
            return CheckRules.isSafeMove(board, startRow, startCol, endRow, endCol, this.getColor());
        }

        // Sáncolás ellenőrzése
        if (this.isFirstMove && rowDiff == 0 && (colDiff == 2 || colDiff == -2)) {
            ChessPiece rookPiece = board[startRow][(endCol == 6) ? 7 : 0];
            if (rookPiece instanceof Rook) {
                Rook rook = (Rook) rookPiece;
                if (rook != null && rook.isFirstMove() && CastlingRules.canCastle(board, this, rook)) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public void setPosition(int row, int col) {
        super.setPosition(row, col);
        // Az első lépés státuszának frissítése
        setFirstMove(false);
    }

    public void setFirstMove(boolean isFirstMove) {
        this.isFirstMove = isFirstMove;
    }

    @Override
    public boolean isFirstMove() {
        return isFirstMove;
    }

    @Override
    public King clone() {
        King clonedKing = (King) super.clone();
        clonedKing.isFirstMove = this.isFirstMove; // Az isFirstMove állapotának másolása
        return clonedKing;
    }
}