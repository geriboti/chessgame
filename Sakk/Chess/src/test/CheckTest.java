package test;

import main.CheckRules;
import org.junit.jupiter.api.Test;
import piece.*;

import static org.junit.jupiter.api.Assertions.*;

public class CheckTest {

    @Test
    public void testKingInCheck() {
        ChessPiece[][] board = new ChessPiece[8][8];
        King whiteKing = new King(PieceColor.WHITE, 7, 4);
        Rook blackRook = new Rook(PieceColor.BLACK, 7, 5);
        board[7][4] = whiteKing;
        board[7][5] = blackRook;

        assertTrue(CheckRules.isKingInCheck(board, PieceColor.WHITE));
    }

    @Test
    public void testKingNotInCheck() {
        ChessPiece[][] board = new ChessPiece[8][8];
        King whiteKing = new King(PieceColor.WHITE, 7, 4);
        Rook blackRook = new Rook(PieceColor.BLACK, 6, 5);
        board[7][4] = whiteKing;
        board[6][5] = blackRook;

        assertFalse(CheckRules.isKingInCheck(board, PieceColor.WHITE));
    }

    @Test
    public void testCheckmate() {
        ChessPiece[][] board = new ChessPiece[8][8];
        King blackKing = new King(PieceColor.BLACK, 0, 4);
        Queen whiteQueen = new Queen(PieceColor.WHITE, 1, 4);
        Rook whiteRook1 = new Rook(PieceColor.WHITE, 1, 3);
        Rook whiteRook2 = new Rook(PieceColor.WHITE, 1, 5);
        Pawn whitePawn1 = new Pawn(PieceColor.WHITE, 2, 5);
        Pawn whitePawn2 = new Pawn(PieceColor.WHITE, 3, 6);
        King whiteKing = new King(PieceColor.WHITE, 7, 6);
        Bishop whiteBishop = new Bishop(PieceColor.WHITE, 2, 1);

        board[0][4] = blackKing;
        board[1][4] = whiteQueen;
        board[1][3] = whiteRook1;
        board[1][5] = whiteRook2;
        board[2][5] = whitePawn1;
        board[3][6] = whitePawn2;
        board[7][6] = whiteKing;
        board[2][1] = whiteBishop;

        assertTrue(CheckRules.isCheckmate(board, PieceColor.BLACK));
    }

    @Test
    public void testNotCheckmate() {
        ChessPiece[][] board = new ChessPiece[8][8];
        King whiteKing = new King(PieceColor.WHITE, 7, 4);
        King blackKing = new King(PieceColor.BLACK, 0, 4);
        Rook blackRook = new Rook(PieceColor.BLACK, 7, 7);
        Bishop whiteBishop = new Bishop(PieceColor.WHITE, 6, 3);
        board[7][4] = whiteKing;
        board[0][4] = blackKing;
        board[7][7] = blackRook;
        board[6][3] = whiteBishop;

        assertFalse(CheckRules.isCheckmate(board, PieceColor.WHITE));
    }

    @Test
    public void testKingsProximity() {
        ChessPiece[][] board = new ChessPiece[8][8];
        King whiteKing = new King(PieceColor.WHITE, 0, 0);
        King blackKing = new King(PieceColor.BLACK, 1, 1);
        board[0][0] = whiteKing;
        board[1][1] = blackKing;

        // Ellenőrizze, hogy a két király nem lehet egymás közvetlen közelében
        assertFalse(whiteKing.isValidMove(0, 0, 1, 1, board));
        assertFalse(blackKing.isValidMove(1, 1, 0, 0, board));
    }

    @Test
    public void testStalemate() {
        ChessPiece[][] board = new ChessPiece[8][8];
        King whiteKing = new King(PieceColor.WHITE, 0, 0);
        King blackKing = new King(PieceColor.BLACK, 1, 2);
        Queen blackQueen = new Queen(PieceColor.BLACK, 2, 1);
        board[0][0] = whiteKing;
        board[1][2] = blackKing;
        board[2][1] = blackQueen;

        assertTrue(CheckRules.isStalemate(board, PieceColor.WHITE));
    }

    @Test
    public void testNotStalemate() {
        ChessPiece[][] board = new ChessPiece[8][8];
        King whiteKing = new King(PieceColor.WHITE, 0, 0);
        King blackKing = new King(PieceColor.BLACK, 7, 7);
        Rook blackRook = new Rook(PieceColor.BLACK, 1, 1);
        board[0][0] = whiteKing;
        board[7][7] = blackKing;
        board[1][1] = blackRook;

        // Ellenőrizzük, hogy nincs patt helyzet, mert van érvényes lépés
        assertFalse(CheckRules.isStalemate(board, PieceColor.WHITE));
    }

    @Test
    public void testInsufficientMaterial() {
        ChessPiece[][] board = new ChessPiece[8][8];
        King whiteKing = new King(PieceColor.WHITE, 0, 0);
        King blackKing = new King(PieceColor.BLACK, 7, 7);
        Bishop whiteBishop = new Bishop(PieceColor.WHITE, 3, 3);
        board[0][0] = whiteKing;
        board[7][7] = blackKing;
        board[3][3] = whiteBishop;

        assertTrue(CheckRules.isInsufficientMaterial(board));
    }

    @Test
    public void testSufficientMaterial() {
        ChessPiece[][] board = new ChessPiece[8][8];
        King whiteKing = new King(PieceColor.WHITE, 0, 0);
        King blackKing = new King(PieceColor.BLACK, 7, 7);
        Queen whiteQueen = new Queen(PieceColor.WHITE, 3, 3);
        board[0][0] = whiteKing;
        board[7][7] = blackKing;
        board[3][3] = whiteQueen;

        assertFalse(CheckRules.isInsufficientMaterial(board));
    }

    @Test
    public void testStalemateOnlyKings() {
        ChessPiece[][] board = new ChessPiece[8][8];
        King whiteKing = new King(PieceColor.WHITE, 0, 0);
        King blackKing = new King(PieceColor.BLACK, 7, 7);
        board[0][0] = whiteKing;
        board[7][7] = blackKing;

        assertTrue(CheckRules.isInsufficientMaterial(board));
    }

    @Test
    public void testStalemateKingAndKnight() {
        ChessPiece[][] board = new ChessPiece[8][8];
        King whiteKing = new King(PieceColor.WHITE, 0, 0);
        King blackKing = new King(PieceColor.BLACK, 7, 7);
        Knight whiteKnight = new Knight(PieceColor.WHITE, 3, 3);
        board[0][0] = whiteKing;
        board[7][7] = blackKing;
        board[3][3] = whiteKnight;

        assertTrue(CheckRules.isInsufficientMaterial(board));
    }
}