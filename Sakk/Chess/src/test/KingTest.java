package test;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import piece.*;

public class KingTest {

    @Test
    public void testValidMoves() {
        ChessPiece[][] board = new ChessPiece[8][8];
        King king = new King(PieceColor.WHITE, 4, 4);
        board[4][4] = king; // Hozzáadjuk a királyt a táblához

        // Valid vertical move
        assertTrue(king.isValidMove(4, 4, 5, 4, board));

        // Valid horizontal move
        assertTrue(king.isValidMove(4, 4, 4, 5, board));

        // Valid diagonal move
        assertTrue(king.isValidMove(4, 4, 5, 5, board));
    }

    @Test
    public void testInvalidMoves() {
        ChessPiece[][] board = new ChessPiece[8][8];
        King king = new King(PieceColor.WHITE, 4, 4);
        board[4][4] = king; // Hozzáadjuk a királyt a táblához

        // Invalid L-shape move
        assertFalse(king.isValidMove(4, 4, 6, 5, board));

        // Invalid two-square move
        assertFalse(king.isValidMove(4, 4, 4, 6, board));
    }

    @Test
    public void testCannotCaptureOwnPiece() {
        ChessPiece[][] board = new ChessPiece[8][8];
        King king = new King(PieceColor.WHITE, 4, 4);
        Rook rook = new Rook(PieceColor.WHITE, 5, 5);
        board[4][4] = king;
        board[5][5] = rook;

        // Should not be able to capture own piece
        assertFalse(king.isValidMove(4, 4, 5, 5, board));
    }

    @Test
    public void testCanCaptureEnemyPiece() {
        ChessPiece[][] board = new ChessPiece[8][8];
        King king = new King(PieceColor.WHITE, 4, 4);
        Rook rook = new Rook(PieceColor.BLACK, 5, 5);
        board[4][4] = king;
        board[5][5] = rook;

        // Should be able to capture enemy piece
        assertTrue(king.isValidMove(4, 4, 5, 5, board));
    }

    @Test
    public void testCannotCaptureKing() {
        ChessPiece[][] board = new ChessPiece[8][8];
        King whiteKing = new King(PieceColor.WHITE, 4, 4);
        King blackKing = new King(PieceColor.BLACK, 5, 5);
        board[4][4] = whiteKing;
        board[5][5] = blackKing;

        // Should not be able to capture the king
        assertFalse(whiteKing.isValidMove(4, 4, 5, 5, board));
    }
}
