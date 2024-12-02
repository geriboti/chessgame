package PGNHandling;

import piece.ChessPiece;
import piece.PieceColor;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class PGNWriter {

    public static void saveGame(ChessPiece[][] board, String fileName, PieceColor currentPlayer) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            // Kiírjuk a játékos színét
            writer.write("[CurrentPlayer " + (currentPlayer == PieceColor.WHITE ? "White" : "Black") + "]");
            writer.newLine();

            // Kiírjuk a tábla állapotát
            for (int row = 0; row < 8; row++) {
                for (int col = 0; col < 8; col++) {
                    ChessPiece piece = board[row][col];
                    if (piece != null) {
                        writer.write(piece.getType().name() + " " + piece.getColor().name() + " " + row + " " + col);
                        writer.newLine();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
