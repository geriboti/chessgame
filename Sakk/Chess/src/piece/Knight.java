package piece;

import main.CheckRules;

public class Knight extends ChessPiece {
    public Knight(PieceColor color, int row, int col) {
        super(PieceType.KNIGHT, color, color == PieceColor.WHITE ?
                        "C:/_3._félév/A programozás alapjai 3/Sakk/pieces-basic-png/white-knight.png" :
                        "C:/_3._félév/A programozás alapjai 3/Sakk/pieces-basic-png/black-knight.png",
                row, col);
    }

    @Override
    public boolean isValidMove(int startRow, int startCol, int endRow, int endCol, ChessPiece[][] board) {
        if (!checkDestinationPiece(endCol, endRow, board)) {
            return false;
        }

        int rowDiff = Math.abs(startRow - endRow);
        int colDiff = Math.abs(startCol - endCol);
        if (rowDiff == 2 && colDiff == 1 || rowDiff == 1 && colDiff == 2) {
            return CheckRules.isSafeMove(board, startRow, startCol, endRow, endCol, this.getColor());
        }
        return false;
    }

    @Override
    public Knight clone() {
        return (Knight) super.clone();
    }
}
