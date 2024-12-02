package main;

import piece.*;

public class EnPassantRules {

    private static int lastMoveStartRow = -1;
    private static int lastMoveStartCol = -1;
    private static int lastMoveEndRow = -1;
    private static int lastMoveEndCol = -1;

    public static boolean canEnPassant(ChessPiece[][] board, Pawn pawn, int targetRow, int targetCol) {
        int direction = (pawn.getColor() == PieceColor.WHITE) ? -1 : 1;
        int startRow = pawn.getRow();
        int startCol = pawn.getCol();

        // Ellenőrizzük, hogy a célmező átlósan egy mezővel van-e a gyalogtól
        if (Math.abs(startCol - targetCol) == 1 && targetRow == startRow + direction) {
            ChessPiece targetPawn = board[startRow][targetCol];
            if (targetPawn instanceof Pawn && targetPawn.getColor() != pawn.getColor() &&
                    lastMoveStartRow == startRow + direction * 2 && lastMoveEndRow == startRow &&
                    lastMoveStartCol == targetCol && lastMoveEndCol == targetCol) {
                return true;
            }
        }
        return false;
    }

    public static void performEnPassant(ChessPiece[][] board, Pawn pawn, int targetRow, int targetCol) {
        if (canEnPassant(board, pawn, targetRow, targetCol)) {
            int direction = (pawn.getColor() == PieceColor.WHITE) ? -1 : 1;
            board[pawn.getRow()][pawn.getCol()] = null; // Eredeti hely kiürítése
            board[targetRow][targetCol] = pawn; // Új hely beállítása
            board[pawn.getRow()][targetCol] = null; // Célgyalog eltávolítása
            pawn.setPosition(targetRow, targetCol); // Gyalog új pozíciójának beállítása
            System.out.println("En passant performed: Pawn to [" + targetRow + ", " + targetCol + "]");
        }
    }

    public static void recordLastMove(int startRow, int startCol, int endRow, int endCol) {
        lastMoveStartRow = startRow;
        lastMoveStartCol = startCol;
        lastMoveEndRow = endRow;
        lastMoveEndCol = endCol;
    }
}
