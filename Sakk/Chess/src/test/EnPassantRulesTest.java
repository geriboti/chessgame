package test;

import org.junit.jupiter.api.Test;
import piece.*;
import main.EnPassantRules;
import static org.junit.jupiter.api.Assertions.*;

public class EnPassantRulesTest {

    @Test
    public void testEnPassantValid() {
        ChessPiece[][] board = new ChessPiece[8][8];
        Pawn whitePawn = new Pawn(PieceColor.WHITE, 3, 6);  // g4
        board[3][6] = whitePawn;

        // Fekete gyalog két lépést lép e7-ről e5-re
        Pawn blackPawn = new Pawn(PieceColor.BLACK, 1, 5);  // f7
        board[1][5] = blackPawn;

        EnPassantRules.recordLastMove(1, 5, 3, 5); // Fekete gyalog két lépést lépett előre (f7 -> f5)
        board[3][5] = blackPawn;
        board[1][5] = null;

        // Ellenőrzés: Fehér gyalog en passant (g4 -> f5)
        assertTrue(EnPassantRules.canEnPassant(board, whitePawn, 2, 5));
    }

    @Test
    public void testPerformEnPassant() {
        ChessPiece[][] board = new ChessPiece[8][8];
        Pawn whitePawn = new Pawn(PieceColor.WHITE, 3, 6);  // g4
        board[3][6] = whitePawn;

        // Fekete gyalog két lépést lép e7-ről e5-re
        Pawn blackPawn = new Pawn(PieceColor.BLACK, 1, 5);  // f7
        board[1][5] = blackPawn;

        EnPassantRules.recordLastMove(1, 5, 3, 5); // Fekete gyalog két lépést lépett előre (f7 -> f5)
        board[3][5] = blackPawn;
        board[1][5] = null;

        // Fehér gyalog en passant (g4 -> f5)
        EnPassantRules.performEnPassant(board, whitePawn, 2, 5);

        // Ellenőrzések
        assertNull(board[3][6]);  // Eredeti hely üres (g4)
        assertEquals(whitePawn, board[2][5]);  // Gyalog új helyen (f5)
        assertNull(board[3][5]);  // Célgyalog eltávolítva (f5)
    }

    @Test
    public void testCannotEnPassantWhitePawn() {
        ChessPiece[][] board = new ChessPiece[8][8];
        Pawn whitePawn = new Pawn(PieceColor.WHITE, 3, 6);  // g4
        board[3][6] = whitePawn;

        // Fekete gyalog csak egyet lép f6-ról f5-re
        Pawn blackPawn = new Pawn(PieceColor.BLACK, 2, 5);  // f6
        board[2][5] = blackPawn;

        EnPassantRules.recordLastMove(2, 5, 3, 5); // Fekete gyalog egyet lépett előre (f6 -> f5)
        board[3][5] = blackPawn;
        board[2][5] = null;

        // NEM lehetséges az en passant
        assertFalse(EnPassantRules.canEnPassant(board, whitePawn, 2, 5));
    }

    @Test
    public void testCannotEnPassantDifferentPosition() {
        ChessPiece[][] board = new ChessPiece[8][8];
        Pawn whitePawn = new Pawn(PieceColor.WHITE, 2, 2);  // c3
        board[2][2] = whitePawn;

        // Fekete gyalog két lépést lép d7-ről d5-re
        Pawn blackPawn = new Pawn(PieceColor.BLACK, 6, 3);  // d7
        board[6][3] = blackPawn;

        EnPassantRules.recordLastMove(6, 3, 4, 3); // Fekete gyalog két lépést lépett előre (d7 -> d5)
        board[4][3] = blackPawn;
        board[6][3] = null;

        // NEM lehetséges az en passant
        assertFalse(EnPassantRules.canEnPassant(board, whitePawn, 3, 3));
    }
}
