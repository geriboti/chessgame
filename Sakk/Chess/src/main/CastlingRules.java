package main;

import piece.*;

public class CastlingRules {

    public static boolean isPathClear(ChessPiece[][] board, int row, int startCol, int endCol) {
        int direction = Integer.signum(endCol - startCol);
        for (int col = startCol + direction; col != endCol; col += direction) {
            if (board[row][col] != null) {
                return false;
            }
        }
        return true;
    }

    public static boolean canCastle(ChessPiece[][] board, King king, Rook rook) {
        // Ellenőrizzük, hogy a király és a bástya még nem mozdultak-e
        if (!king.isFirstMove() || !rook.isFirstMove()) {
            return false;
        }

        int row = king.getRow();
        int kingCol = king.getCol();
        int rookCol = rook.getCol();

        // Ellenőrizzük, hogy az út szabad és nincs támadva
        if (isPathClear(board, row, kingCol, rookCol)) {
            for (int col = Math.min(kingCol, rookCol); col <= Math.max(kingCol, rookCol); col++) {
                if (CheckRules.isAttackedByEnemy(board, row, col, king.getColor())) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public static void performCastling(ChessPiece[][] board, King king, Rook rook) {
        if (!canCastle(board, king, rook)) {
            System.out.println("Castling cannot be performed due to previous moves or path blockage.");
            return;
        }

        int kingRow = king.getRow();
        int kingCol = king.getCol();
        int rookCol = rook.getCol();

        if (rookCol == 0) {
            // Hosszú sáncolás
            if (kingRow < board.length && kingCol - 2 >= 0) {
                // Király áthelyezése balra 2-vel
                board[kingRow][kingCol - 2] = king;
                board[kingRow][kingCol] = null;
                king.setPosition(kingRow, kingCol - 2);

                // Bástya áthelyezése 3-mal jobbra
                board[kingRow][kingCol - 1] = rook;
                board[kingRow][rookCol] = null;
                rook.setPosition(kingRow, kingCol - 1);

                System.out.println("Castling performed: King to [" + kingRow + ", " + (kingCol - 2) + "], Rook to [" + kingRow + ", " + (kingCol - 1) + "]");
            }
        } else if (rookCol == 7) {
            // Rövid sáncolás
            if (kingRow < board.length && kingCol + 2 < board[0].length) {
                // Király áthelyezése jobbra 2-vel
                board[kingRow][kingCol + 2] = king;
                board[kingRow][kingCol] = null;
                king.setPosition(kingRow, kingCol + 2);

                // Bástya áthelyezése 2-vel balra
                board[kingRow][kingCol + 1] = rook;
                board[kingRow][rookCol] = null;
                rook.setPosition(kingRow, kingCol + 1);

                System.out.println("Castling performed: King to [" + kingRow + ", " + (kingCol + 2) + "], Rook to [" + kingRow + ", " + (kingCol + 1) + "]");
            }
        }

        // Első lépés beállítása
        king.setFirstMove(false);
        rook.setFirstMove(false);
    }
}






