package test;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import piece.*;

public class RookTest {

    @Test
    public void testValidMoves() {
        ChessPiece[][] board = new ChessPiece[8][8];
        Rook rook = new Rook(PieceColor.WHITE, 0, 0);
        board[0][0] = rook; // Hozzáadjuk a bástyát a táblához

        // Valid vertical move
        assertTrue(rook.isValidMove(0, 0, 0, 5, board));

        // Valid horizontal move
        assertTrue(rook.isValidMove(0, 0, 5, 0, board));
    }

    @Test
    public void testInvalidMoves() {
        ChessPiece[][] board = new ChessPiece[8][8];
        Rook rook = new Rook(PieceColor.WHITE, 0, 0);
        board[0][0] = rook; // Hozzáadjuk a bástyát a táblához

        // Invalid diagonal move
        assertFalse(rook.isValidMove(0, 0, 3, 3, board));

        // Invalid L-shape move
        assertFalse(rook.isValidMove(0, 0, 1, 2, board));
    }

    @Test
    public void testCannotCaptureOwnPiece() {
        ChessPiece[][] board = new ChessPiece[8][8];
        Rook rook = new Rook(PieceColor.WHITE, 0, 0);
        Pawn pawn = new Pawn(PieceColor.WHITE, 0, 5);
        board[0][0] = rook;
        board[0][5] = pawn;

        // Should not be able to capture own piece
        assertFalse(rook.isValidMove(0, 0, 0, 5, board));
    }

    @Test
    public void testCanCaptureEnemyPiece() {
        ChessPiece[][] board = new ChessPiece[8][8];
        Rook rook = new Rook(PieceColor.WHITE, 0, 0);
        Pawn pawn = new Pawn(PieceColor.BLACK, 0, 5);
        board[0][0] = rook;
        board[0][5] = pawn;

        // Should be able to capture enemy piece
        assertTrue(rook.isValidMove(0, 0, 0, 5, board));
    }

    @Test
    public void testCannotCaptureKing() {
        ChessPiece[][] board = new ChessPiece[8][8];
        Rook rook = new Rook(PieceColor.WHITE, 0, 0);
        King king = new King(PieceColor.BLACK, 0, 5);
        board[0][0] = rook;
        board[0][5] = king;

        // Should not be able to capture the king
        assertFalse(rook.isValidMove(0, 0, 0, 5, board));
    }
}
