package PGNHandling;

import piece.ChessPiece;
import piece.PieceColor;
import piece.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PGNReader {

    public static PieceColor loadGame(ChessPiece[][] board, String fileName) {
        PieceColor currentPlayer = PieceColor.WHITE; // Alapértelmezett játékos

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;

            // Tábla ürítése
            for (int row = 0; row < 8; row++) {
                for (int col = 0; col < 8; col++) {
                    board[row][col] = null;
                }
            }

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("[CurrentPlayer")) {
                    currentPlayer = line.contains("White") ? PieceColor.WHITE : PieceColor.BLACK;
                } else {
                    String[] parts = line.split(" ");
                    String pieceType = parts[0];
                    String color = parts[1];
                    int row = Integer.parseInt(parts[2]);
                    int col = Integer.parseInt(parts[3]);

                    ChessPiece piece = null;
                    switch (pieceType) {
                        case "ROOK":
                            piece = new Rook(PieceColor.valueOf(color), row, col);
                            break;
                        case "KNIGHT":
                            piece = new Knight(PieceColor.valueOf(color), row, col);
                            break;
                        case "BISHOP":
                            piece = new Bishop(PieceColor.valueOf(color), row, col);
                            break;
                        case "QUEEN":
                            piece = new Queen(PieceColor.valueOf(color), row, col);
                            break;
                        case "KING":
                            piece = new King(PieceColor.valueOf(color), row, col);
                            break;
                        case "PAWN":
                            piece = new Pawn(PieceColor.valueOf(color), row, col);
                            break;
                    }
                    board[row][col] = piece;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return currentPlayer;
    }
}
