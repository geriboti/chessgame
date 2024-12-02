package test;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import piece.*;

public class KnightTest {

    @Test
    public void testValidMoves() {
        ChessPiece[][] board = new ChessPiece[8][8];
        Knight knight = new Knight(PieceColor.WHITE, 4, 4);
        board[4][4] = knight;

        // Valid L-shape moves
        assertTrue(knight.isValidMove(4, 4, 6, 5, board));
        assertTrue(knight.isValidMove(4, 4, 6, 3, board));
        assertTrue(knight.isValidMove(4, 4, 2, 5, board));
        assertTrue(knight.isValidMove(4, 4, 2, 3, board));
        assertTrue(knight.isValidMove(4, 4, 5, 6, board));
        assertTrue(knight.isValidMove(4, 4, 5, 2, board));
        assertTrue(knight.isValidMove(4, 4, 3, 6, board));
        assertTrue(knight.isValidMove(4, 4, 3, 2, board));
    }

    @Test
    public void testInvalidMoves() {
        ChessPiece[][] board = new ChessPiece[8][8];
        Knight knight = new Knight(PieceColor.WHITE, 4, 4);
        board[4][4] = knight;

        // Invalid horizontal move
        assertFalse(knight.isValidMove(4, 4, 4, 2, board));

        // Invalid vertical move
        assertFalse(knight.isValidMove(4, 4, 6, 4, board));
    }

    @Test
    public void testCannotCaptureOwnPiece() {
        ChessPiece[][] board = new ChessPiece[8][8];
        Knight knight = new Knight(PieceColor.WHITE, 4, 4);
        Pawn pawn = new Pawn(PieceColor.WHITE, 6, 5);
        board[4][4] = knight;
        board[6][5] = pawn;

        // Should not be able to capture own piece
        assertFalse(knight.isValidMove(4, 4, 6, 5, board));
    }

    @Test
    public void testCanCaptureEnemyPiece() {
        ChessPiece[][] board = new ChessPiece[8][8];
        Knight knight = new Knight(PieceColor.WHITE, 4, 4);
        Pawn pawn = new Pawn(PieceColor.BLACK, 6, 5);
        board[4][4] = knight;
        board[6][5] = pawn;

        // Should be able to capture enemy piece
        assertTrue(knight.isValidMove(4, 4, 6, 5, board));
    }

    @Test
    public void testCannotCaptureKing() {
        ChessPiece[][] board = new ChessPiece[8][8];
        Knight knight = new Knight(PieceColor.WHITE, 4, 4);
        King king = new King(PieceColor.BLACK, 6, 5);
        board[4][4] = knight;
        board[6][5] = king;

        // Should not be able to capture the king
        assertFalse(knight.isValidMove(4, 4, 6, 5, board));
    }
}
