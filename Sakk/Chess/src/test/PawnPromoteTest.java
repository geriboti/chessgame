package test;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import piece.*;

public class PawnPromoteTest {

    @Test
    public void testWhitePawnPromotionToQueen() {
        ChessPiece[][] board = new ChessPiece[8][8];
        Pawn pawn = new Pawn(PieceColor.WHITE, 1, 0);
        board[1][0] = pawn;
        pawn.setPosition(0, 0); // Mozgassuk a gyalogot a promotion sorba

        ChessPiece promotedPiece = pawn.promote("Queen");
        assertTrue(promotedPiece instanceof Queen);
        assertEquals(PieceColor.WHITE, promotedPiece.getColor());
        assertEquals(0, promotedPiece.getRow());
        assertEquals(0, promotedPiece.getCol());
    }

    @Test
    public void testBlackPawnPromotionToKnight() {
        ChessPiece[][] board = new ChessPiece[8][8];
        Pawn pawn = new Pawn(PieceColor.BLACK, 6, 0);
        board[6][0] = pawn;
        pawn.setPosition(7, 0); // Mozgassuk a gyalogot a promotion sorba

        ChessPiece promotedPiece = pawn.promote("Knight");
        assertTrue(promotedPiece instanceof Knight);
        assertEquals(PieceColor.BLACK, promotedPiece.getColor());
        assertEquals(7, promotedPiece.getRow());
        assertEquals(0, promotedPiece.getCol());
    }

    @Test
    public void testInvalidPromotionPosition() {
        ChessPiece[][] board = new ChessPiece[8][8];
        Pawn pawn = new Pawn(PieceColor.WHITE, 2, 0);
        board[2][0] = pawn;
        assertThrows(IllegalStateException.class, () -> {
            pawn.promote("Queen");
        });
    }

    @Test
    public void testInvalidPromotionPieceType() {
        ChessPiece[][] board = new ChessPiece[8][8];
        Pawn pawn = new Pawn(PieceColor.WHITE, 1, 0);
        board[1][0] = pawn;
        pawn.setPosition(0, 0); // Mozgassuk a gyalogot a promotion sorba
        assertThrows(IllegalArgumentException.class, () -> {
            pawn.promote("King");
        });
    }
}
