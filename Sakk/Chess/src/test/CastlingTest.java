package test;

import org.junit.jupiter.api.Test;
import piece.*;
import main.CastlingRules;
import static org.junit.jupiter.api.Assertions.*;

public class CastlingTest {

    @Test
    public void testPathClear() {
        ChessPiece[][] board = new ChessPiece[8][8];
        King king = new King(PieceColor.WHITE, 7, 4);
        Rook rook = new Rook(PieceColor.WHITE, 7, 0);
        board[7][4] = king;
        board[7][0] = rook;

        assertTrue(CastlingRules.isPathClear(board, 7, 4, 0));
    }

    @Test
    public void testPathNotClear() {
        ChessPiece[][] board = new ChessPiece[8][8];
        King king = new King(PieceColor.WHITE, 7, 4);
        Rook rook = new Rook(PieceColor.WHITE, 7, 0);
        Pawn pawn = new Pawn(PieceColor.WHITE, 7, 2);
        board[7][4] = king;
        board[7][0] = rook;
        board[7][2] = pawn;

        assertFalse(CastlingRules.isPathClear(board, 7, 4, 0));
    }

    @Test
    public void testCanCastle() {
        ChessPiece[][] board = new ChessPiece[8][8];
        King king = new King(PieceColor.WHITE, 7, 4);
        Rook rook = new Rook(PieceColor.WHITE, 7, 0);
        board[7][4] = king;
        board[7][0] = rook;

        assertTrue(CastlingRules.canCastle(board, king, rook));
    }

    @Test
    public void testCannotCastleDueToMove() {
        ChessPiece[][] board = new ChessPiece[8][8];
        King king = new King(PieceColor.WHITE, 7, 4);
        Rook rook = new Rook(PieceColor.WHITE, 7, 0);
        king.setFirstMove(false);
        board[7][4] = king;
        board[7][0] = rook;

        assertFalse(CastlingRules.canCastle(board, king, rook));
    }

    @Test
    public void testPerformCastlingShort() {
        ChessPiece[][] board = new ChessPiece[8][8];
        King king = new King(PieceColor.WHITE, 7, 4);
        Rook rook = new Rook(PieceColor.WHITE, 7, 7);
        board[7][4] = king;
        board[7][7] = rook;

        CastlingRules.performCastling(board, king, rook);
        assertNull(board[7][4]);
        assertNull(board[7][7]);
        assertEquals(king, board[7][6]);
        assertEquals(rook, board[7][5]);
        assertEquals(7, king.getRow());
        assertEquals(6, king.getCol());
        assertEquals(7, rook.getRow());
        assertEquals(5, rook.getCol());
    }

    @Test
    public void testPerformCastlingLong() {
        ChessPiece[][] board = new ChessPiece[8][8];
        King king = new King(PieceColor.WHITE, 7, 4);
        Rook rook = new Rook(PieceColor.WHITE, 7, 0);
        board[7][4] = king;
        board[7][0] = rook;

        CastlingRules.performCastling(board, king, rook);
        assertNull(board[7][4]);
        assertNull(board[7][0]);
        assertEquals(king, board[7][2]);
        assertEquals(rook, board[7][3]);
        assertEquals(7, king.getRow());
        assertEquals(2, king.getCol());
        assertEquals(7, rook.getRow());
        assertEquals(3, rook.getCol());
    }

    @Test
    public void testPerformCastlingShortBlockedPath() {
        ChessPiece[][] board = new ChessPiece[8][8];
        King king = new King(PieceColor.WHITE, 7, 4);
        Rook rook = new Rook(PieceColor.WHITE, 7, 7);
        Pawn pawn = new Pawn(PieceColor.WHITE, 7, 5);
        board[7][4] = king;
        board[7][7] = rook;
        board[7][5] = pawn;

        CastlingRules.performCastling(board, king, rook);
        assertNotNull(board[7][4]); // Király nem mozdul
        assertEquals(king, board[7][4]);
        assertNotNull(board[7][7]); // Bástya nem mozdul
        assertEquals(rook, board[7][7]);
        assertEquals(pawn, board[7][5]); // Gátló bábu a helyén marad
        assertNull(board[7][6]);
    }

    @Test
    public void testPerformCastlingLongBlockedPath() {
        ChessPiece[][] board = new ChessPiece[8][8];
        King king = new King(PieceColor.WHITE, 7, 4);
        Rook rook = new Rook(PieceColor.WHITE, 7, 0);
        Pawn pawn = new Pawn(PieceColor.WHITE, 7, 3);
        board[7][4] = king;
        board[7][0] = rook;
        board[7][3] = pawn;

        CastlingRules.performCastling(board, king, rook);
        assertNotNull(board[7][4]); // Király nem mozdul
        assertEquals(king, board[7][4]);
        assertNotNull(board[7][0]); // Bástya nem mozdul
        assertEquals(rook, board[7][0]);
        assertEquals(pawn, board[7][3]); // Gátló bábu a helyén marad
        assertNull(board[7][2]);
    }

    @Test
    public void testPerformCastlingShortAttackedPath() {
        ChessPiece[][] board = new ChessPiece[8][8];
        King king = new King(PieceColor.WHITE, 7, 4);
        Rook rook = new Rook(PieceColor.WHITE, 7, 7);
        Rook blackRook = new Rook(PieceColor.BLACK, 5, 5);
        board[7][4] = king;
        board[7][7] = rook;
        board[5][5] = blackRook;

        CastlingRules.performCastling(board, king, rook);
        assertNotNull(board[7][4]); // Király nem mozdul
        assertEquals(king, board[7][4]);
        assertNotNull(board[7][7]); // Bástya nem mozdul
        assertEquals(rook, board[7][7]);
        assertNull(board[7][6]);
    }

    @Test
    public void testPerformCastlingLongAttackedPath() {
        ChessPiece[][] board = new ChessPiece[8][8];
        King king = new King(PieceColor.WHITE, 7, 4);
        Rook rook = new Rook(PieceColor.WHITE, 7, 0);
        Rook blackRook = new Rook(PieceColor.BLACK, 5, 3);
        board[7][4] = king;
        board[7][0] = rook;
        board[5][3] = blackRook;

        CastlingRules.performCastling(board, king, rook);
        assertNotNull(board[7][4]); // Király nem mozdul
        assertEquals(king, board[7][4]);
        assertNotNull(board[7][0]); // Bástya nem mozdul
        assertEquals(rook, board[7][0]);
        assertNull(board[7][2]);
    }

    @Test
    public void testPerformCastlingShortAfterKingMove() {
        ChessPiece[][] board = new ChessPiece[8][8];
        King king = new King(PieceColor.WHITE, 7, 4);
        Rook rook = new Rook(PieceColor.WHITE, 7, 7);
        board[7][4] = king;
        board[7][7] = rook;
        king.setFirstMove(false);

        CastlingRules.performCastling(board, king, rook);
        assertNotNull(board[7][4]); // Király nem mozdul
        assertEquals(king, board[7][4]);
        assertNotNull(board[7][7]); // Bástya nem mozdul
        assertEquals(rook, board[7][7]);
        assertNull(board[7][6]);
    }

    @Test
    public void testPerformCastlingShortAfterRookMove() {
        ChessPiece[][] board = new ChessPiece[8][8];
        King king = new King(PieceColor.WHITE, 7, 4);
        Rook rook = new Rook(PieceColor.WHITE, 7, 7);
        board[7][4] = king;
        board[7][7] = rook;
        rook.setFirstMove(false);

        CastlingRules.performCastling(board, king, rook);
        assertNotNull(board[7][4]); // Király nem mozdul
        assertEquals(king, board[7][4]);
        assertNotNull(board[7][7]); // Bástya nem mozdul
        assertEquals(rook, board[7][7]);
        assertNull(board[7][6]);
        assertNull(board[7][5]);
    }

    @Test
    public void testPerformCastlingLongAfterKingMove() {
        ChessPiece[][] board = new ChessPiece[8][8];
        King king = new King(PieceColor.WHITE, 7, 4);
        Rook rook = new Rook(PieceColor.WHITE, 7, 0);
        board[7][4] = king;
        board[7][0] = rook;
        king.setFirstMove(false);

        CastlingRules.performCastling(board, king, rook);
        assertNotNull(board[7][4]); // Király nem mozdul
        assertEquals(king, board[7][4]);
        assertNotNull(board[7][0]); // Bástya nem mozdul
        assertEquals(rook, board[7][0]);
        assertNull(board[7][2]);
        assertNull(board[7][3]);
    }

    @Test
    public void testPerformCastlingLongAfterRookMove() {
        ChessPiece[][] board = new ChessPiece[8][8];
        King king = new King(PieceColor.WHITE, 7, 4);
        Rook rook = new Rook(PieceColor.WHITE, 7, 0);
        board[7][4] = king;
        board[7][0] = rook;
        rook.setFirstMove(false);

        CastlingRules.performCastling(board, king, rook);
        assertNotNull(board[7][4]); // Király nem mozdul
        assertEquals(king, board[7][4]);
        assertNotNull(board[7][0]); // Bástya nem mozdul
        assertEquals(rook, board[7][0]);
        assertNull(board[7][2]);
    }

    @Test
    public void testKingSetPositionUpdatesFirstMove() {
        King king = new King(PieceColor.WHITE, 7, 4);
        assertTrue(king.isFirstMove());
        king.setPosition(7, 5);
        assertFalse(king.isFirstMove());
    }

    @Test
    public void testRookSetPositionUpdatesFirstMove() {
        Rook rook = new Rook(PieceColor.WHITE, 7, 0);
        assertTrue(rook.isFirstMove());
        rook.setPosition(7, 1);
        assertFalse(rook.isFirstMove());
    }

    @Test
    public void testPerformCastlingKingFirstMoveSetToFalse() {
        ChessPiece[][] board = new ChessPiece[8][8];
        King king = new King(PieceColor.WHITE, 7, 4);
        Rook rook = new Rook(PieceColor.WHITE, 7, 0);
        board[7][4] = king;
        board[7][0] = rook;

        CastlingRules.performCastling(board, king, rook);
        assertFalse(king.isFirstMove());
        assertFalse(rook.isFirstMove());
    }

    @Test
    public void testPerformCastlingRookFirstMoveSetToFalse() {
        ChessPiece[][] board = new ChessPiece[8][8];
        King king = new King(PieceColor.WHITE, 7, 4);
        Rook rook = new Rook(PieceColor.WHITE, 7, 0);
        board[7][4] = king;
        board[7][0] = rook;

        CastlingRules.performCastling(board, king, rook);
        assertFalse(king.isFirstMove());
        assertFalse(rook.isFirstMove());
    }
}