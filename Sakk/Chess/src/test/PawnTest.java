package test;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import piece.*;

public class PawnTest {

    @Test
    public void testValidMoves() {
        ChessPiece[][] board = new ChessPiece[8][8];
        Pawn pawn = new Pawn(PieceColor.WHITE, 6, 0);
        board[6][0] = pawn;

        // Valid single step move
        assertTrue(pawn.isValidMove(6, 0, 5, 0, board));

        // Valid double step move from initial position
        assertTrue(pawn.isValidMove(6, 0, 4, 0, board));
    }

    @Test
    public void testInvalidMoves() {
        ChessPiece[][] board = new ChessPiece[8][8];
        Pawn pawn = new Pawn(PieceColor.WHITE, 6, 0);
        board[6][0] = pawn;

        // Invalid move backwards
        assertFalse(pawn.isValidMove(6, 0, 7, 0, board));

        // Invalid move sideways
        assertFalse(pawn.isValidMove(6, 0, 6, 1, board));
    }

    @Test
    public void testCannotCaptureOwnPiece() {
        ChessPiece[][] board = new ChessPiece[8][8];
        Pawn pawn = new Pawn(PieceColor.WHITE, 6, 0);
        Rook rook = new Rook(PieceColor.WHITE, 5, 1);
        board[6][0] = pawn;
        board[5][1] = rook;

        // Should not be able to capture own piece
        assertFalse(pawn.isValidMove(6, 0, 5, 1, board));
    }

    @Test
    public void testCanCaptureEnemyPiece() {
        ChessPiece[][] board = new ChessPiece[8][8];
        Pawn pawn = new Pawn(PieceColor.WHITE, 6, 0);
        Rook rook = new Rook(PieceColor.BLACK, 5, 1);
        board[6][0] = pawn;
        board[5][1] = rook;

        // Should be able to capture enemy piece
        assertTrue(pawn.isValidMove(6, 0, 5, 1, board));
    }

    @Test
    public void testCannotCaptureKing() {
        ChessPiece[][] board = new ChessPiece[8][8];
        Pawn pawn = new Pawn(PieceColor.WHITE, 6, 0);
        King king = new King(PieceColor.BLACK, 5, 1);
        board[6][0] = pawn;
        board[5][1] = king;

        // Should not be able to capture the king
        assertFalse(pawn.isValidMove(6, 0, 5, 1, board));
    }
}
