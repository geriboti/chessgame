package piece;

import main.CheckRules;

public class Rook extends ChessPiece {
    public Rook(PieceColor color, int row, int col) {
        super(PieceType.ROOK, color, color == PieceColor.WHITE ?
                        "C:/_3._félév/A programozás alapjai 3/Sakk/pieces-basic-png/white-rook.png" :
                        "C:/_3._félév/A programozás alapjai 3/Sakk/pieces-basic-png/black-rook.png",
                row, col);
    }

    @Override
    public boolean isValidMove(int startRow, int startCol, int endRow, int endCol, ChessPiece[][] board) {
        if (!checkDestinationPiece(endCol, endRow, board)) {
            return false;
        }

        if (startRow == endRow || startCol == endCol) {
            if (!isPathClear(startRow, startCol, endRow, endCol, board)) {
                return false;
            }
            return CheckRules.isSafeMove(board, startRow, startCol, endRow, endCol, this.getColor());
        }
        return false;
    }

    @Override
    public void setPosition(int row, int col) {
        super.setPosition(row, col);
        // Az első lépés státuszának frissítése
        setFirstMove(false);
    }

    public Rook clone() {
        return (Rook) super.clone();
    }
}
