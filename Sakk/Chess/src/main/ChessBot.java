package main;

import piece.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.JOptionPane;

public class ChessBot {
    private Random random = new Random();
    private boolean gameOver = false; // Új mező a játék végének jelzésére

    public boolean isGameOver() {
        return gameOver;
    }

    public void makeMove(ChessPiece[][] board, PieceColor botColor) {
        if (CheckRules.isCheckmate(board, botColor)) {
            System.out.println("Checkmate! " + (botColor == PieceColor.WHITE ? "White" : "Black") + " wins!");
            JOptionPane.showMessageDialog(null, "Checkmate! " + (botColor == PieceColor.WHITE ? "White" : "Black") + " wins!");
            gameOver = true;
            return;
        }

        if (CheckRules.isStalemate(board, botColor)) {
            System.out.println("Stalemate! The game is a draw.");
            JOptionPane.showMessageDialog(null, "Stalemate! The game is a draw.");
            gameOver = true;
            return;
        }

        List<Move> validMoves = getAllValidMoves(board, botColor);
        if (!validMoves.isEmpty()) {
            Move move = validMoves.get(random.nextInt(validMoves.size()));
            ChessPiece piece = board[move.getStartRow()][move.getStartCol()];

            // Végezze el a lépést
            board[move.getEndRow()][move.getEndCol()] = piece;
            board[move.getStartRow()][move.getStartCol()] = null;
            piece.setPosition(move.getEndRow(), move.getEndCol());

            // Bábuk előléptetése
            if (piece instanceof Pawn) {
                if ((botColor == PieceColor.WHITE && move.getEndRow() == 0) ||
                        (botColor == PieceColor.BLACK && move.getEndRow() == 7)) {
                    String[] options = {"Queen", "Rook", "Bishop", "Knight"};
                    String choice = options[random.nextInt(options.length)];
                    board[move.getEndRow()][move.getEndCol()] = ((Pawn) piece).promote(choice);
                }
            }

            // Ellenőrizze, hogy a lépés után sakk-matt vagy patt helyzet van-e
            if (CheckRules.isCheckmate(board, botColor)) {
                System.out.println("Checkmate! " + (botColor == PieceColor.WHITE ? "White" : "Black") + " wins!");
                JOptionPane.showMessageDialog(null, "Checkmate! " + (botColor == PieceColor.WHITE ? "White" : "Black") + " wins!");
                gameOver = true;
            } else if (CheckRules.isStalemate(board, botColor)) {
                System.out.println("Stalemate! The game is a draw.");
                JOptionPane.showMessageDialog(null, "Stalemate! The game is a draw.");
                gameOver = true;
            }

        } else {
            System.out.println("No valid moves for " + (botColor == PieceColor.WHITE ? "White" : "Black"));
        }
    }

    private List<Move> getAllValidMoves(ChessPiece[][] board, PieceColor color) {
        List<Move> validMoves = new ArrayList<>();
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                ChessPiece piece = board[row][col];
                if (piece != null && piece.getColor() == color) {
                    for (int newRow = 0; newRow < board.length; newRow++) {
                        for (int newCol = 0; newCol < board[newRow].length; newCol++) {
                            if (piece.isValidMove(row, col, newRow, newCol, board)) {
                                if (isMoveSafe(board, row, col, newRow, newCol, color)) {
                                    validMoves.add(new Move(row, col, newRow, newCol));
                                }
                            }
                        }
                    }
                }
            }
        }
        return validMoves;
    }

    private boolean isMoveSafe(ChessPiece[][] board, int startRow, int startCol, int endRow, int endCol, PieceColor color) {
        ChessPiece[][] newBoard = copyBoard(board);
        ChessPiece movingPiece = newBoard[startRow][startCol];
        newBoard[endRow][endCol] = movingPiece;
        newBoard[startRow][startCol] = null;
        return !CheckRules.isKingInCheck(newBoard, color);
    }

    private ChessPiece[][] copyBoard(ChessPiece[][] board) {
        ChessPiece[][] newBoard = new ChessPiece[board.length][board[0].length];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] != null) {
                    newBoard[i][j] = board[i][j].clone(); // Győződjünk meg róla, hogy a ChessPiece osztály támogatja a klónozást
                }
            }
        }
        return newBoard;
    }
}

class Move {
    private int startRow, startCol, endRow, endCol;

    public Move(int startRow, int startCol, int endRow, int endCol) {
        this.startRow = startRow;
        this.startCol = startCol;
        this.endRow = endRow;
        this.endCol = endCol;
    }

    public int getStartRow() {
        return startRow;
    }

    public int getStartCol() {
        return startCol;
    }

    public int getEndRow() {
        return endRow;
    }

    public int getEndCol() {
        return endCol;
    }
}
