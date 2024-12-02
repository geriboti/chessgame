package piece;

import main.CheckRules;

public class Bishop extends ChessPiece {
    public Bishop(PieceColor color, int row, int col) {
        super(PieceType.BISHOP, color, color == PieceColor.WHITE ?
                        "C:/_3._félév/A programozás alapjai 3/Sakk/pieces-basic-png/white-bishop.png" :
                        "C:/_3._félév/A programozás alapjai 3/Sakk/pieces-basic-png/black-bishop.png",
                row, col);
    }

    @Override
    public boolean isValidMove(int startRow, int startCol, int endRow, int endCol, ChessPiece[][] board) {
        if (!checkDestinationPiece(endCol, endRow, board)) {
            return false;
        }

        if (Math.abs(startRow - endRow) == Math.abs(startCol - endCol)) {
            if (isPathClear(startRow, startCol, endRow, endCol, board)) {
                return CheckRules.isSafeMove(board, startRow, startCol, endRow, endCol, this.getColor());
            }
        }
        return false;
    }

    @Override
    public Bishop clone() {
        return (Bishop) super.clone();
    }
}
