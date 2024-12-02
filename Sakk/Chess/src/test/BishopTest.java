package test;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import piece.*;

public class BishopTest {

    @Test
    public void testValidMoves() {
        ChessPiece[][] board = new ChessPiece[8][8];
        Bishop bishop = new Bishop(PieceColor.WHITE, 2, 0);
        board[2][0] = bishop;

        // Valid diagonal move
        assertTrue(bishop.isValidMove(2, 0, 4, 2, board));
        assertTrue(bishop.isValidMove(2, 0, 0, 2, board));
    }

    @Test
    public void testInvalidMoves() {
        ChessPiece[][] board = new ChessPiece[8][8];
        Bishop bishop = new Bishop(PieceColor.WHITE, 2, 0);
        board[2][0] = bishop;

        // Invalid vertical move
        assertFalse(bishop.isValidMove(2, 0, 2, 3, board));
        assertFalse(bishop.isValidMove(2, 0, 5, 0, board));
    }

    @Test
    public void testCannotCaptureOwnPiece() {
        ChessPiece[][] board = new ChessPiece[8][8];
        Bishop bishop = new Bishop(PieceColor.WHITE, 2, 0);
        Pawn pawn = new Pawn(PieceColor.WHITE, 4, 2);
        board[2][0] = bishop;
        board[4][2] = pawn;

        // Should not be able to capture own piece
        assertFalse(bishop.isValidMove(2, 0, 4, 2, board));
    }

    @Test
    public void testCanCaptureEnemyPiece() {
        ChessPiece[][] board = new ChessPiece[8][8];
        Bishop bishop = new Bishop(PieceColor.WHITE, 2, 0);
        Pawn pawn = new Pawn(PieceColor.BLACK, 4, 2);
        board[2][0] = bishop;
        board[4][2] = pawn;

        // Should be able to capture enemy piece
        assertTrue(bishop.isValidMove(2, 0, 4, 2, board));
    }

    @Test
    public void testCannotCaptureKing() {
        ChessPiece[][] board = new ChessPiece[8][8];
        Bishop bishop = new Bishop(PieceColor.WHITE, 2, 0);
        King king = new King(PieceColor.BLACK, 4, 2);
        board[2][0] = bishop;
        board[4][2] = king;

        // Should not be able to capture the king
        assertFalse(bishop.isValidMove(2, 0, 4, 2, board));
    }
}
