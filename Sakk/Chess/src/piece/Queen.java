package piece;

import main.CheckRules;

public class Queen extends ChessPiece {
    public Queen(PieceColor color, int row, int col) {
        super(PieceType.QUEEN, color, color == PieceColor.WHITE ?
                        "C:/_3._félév/A programozás alapjai 3/Sakk/pieces-basic-png/white-queen.png" :
                        "C:/_3._félév/A programozás alapjai 3/Sakk/pieces-basic-png/black-queen.png",
                row, col);
    }

    @Override
    public boolean isValidMove(int startRow, int startCol, int endRow, int endCol, ChessPiece[][] board) {
        if (!checkDestinationPiece(endCol, endRow, board)) {
            return false;
        }

        if (startRow == endRow || startCol == endCol) {
            if (isPathClear(startRow, startCol, endRow, endCol, board)) {
                return CheckRules.isSafeMove(board, startRow, startCol, endRow, endCol, this.getColor());
            }
        }

        if (Math.abs(startRow - endRow) == Math.abs(startCol - endCol)) {
            if (isPathClear(startRow, startCol, endRow, endCol, board)) {
                return CheckRules.isSafeMove(board, startRow, startCol, endRow, endCol, this.getColor());
            }
        }

        return false;
    }

    public Queen clone() {
        return (Queen) super.clone();
    }
}