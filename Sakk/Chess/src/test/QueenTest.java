package test;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import piece.*;

public class QueenTest {

    @Test
    public void testValidMoves() {
        ChessPiece[][] board = new ChessPiece[8][8];
        Queen queen = new Queen(PieceColor.WHITE, 3, 3);
        board[3][3] = queen; // Hozzáadjuk a királynőt a táblához

        // Valid diagonal move
        assertTrue(queen.isValidMove(3, 3, 5, 5, board));

        // Valid vertical move
        assertTrue(queen.isValidMove(3, 3, 3, 6, board));

        // Valid horizontal move
        assertTrue(queen.isValidMove(3, 3, 0, 3, board));

        // Valid diagonal move
        assertTrue(queen.isValidMove(3, 3, 4, 2, board));
    }

    @Test
    public void testInvalidMoves() {
        ChessPiece[][] board = new ChessPiece[8][8];
        Queen queen = new Queen(PieceColor.WHITE, 3, 3);
        board[3][3] = queen; // Hozzáadjuk a királynőt a táblához

        // Invalid L-shape move
        assertFalse(queen.isValidMove(3, 3, 5, 4, board));
    }

    @Test
    public void testCannotCaptureOwnPiece() {
        ChessPiece[][] board = new ChessPiece[8][8];
        Queen queen = new Queen(PieceColor.WHITE, 3, 3);
        Rook rook = new Rook(PieceColor.WHITE, 5, 5);
        board[3][3] = queen;
        board[5][5] = rook;

        // Should not be able to capture own piece
        assertFalse(queen.isValidMove(3, 3, 5, 5, board));
    }

    @Test
    public void testCanCaptureEnemyPiece() {
        ChessPiece[][] board = new ChessPiece[8][8];
        Queen queen = new Queen(PieceColor.WHITE, 3, 3);
        Rook rook = new Rook(PieceColor.BLACK, 5, 5);
        board[3][3] = queen;
        board[5][5] = rook;

        // Should be able to capture enemy piece
        assertTrue(queen.isValidMove(3, 3, 5, 5, board));
    }

    @Test
    public void testCannotCaptureKing() {
        ChessPiece[][] board = new ChessPiece[8][8];
        Queen queen = new Queen(PieceColor.WHITE, 3, 3);
        King king = new King(PieceColor.BLACK, 5, 5);
        board[3][3] = queen;
        board[5][5] = king;

        // Should not be able to capture the king
        assertFalse(queen.isValidMove(3, 3, 5, 5, board));
    }
}
