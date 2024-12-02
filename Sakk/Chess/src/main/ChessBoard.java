package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import piece.*;

public class ChessBoard extends JPanel {
    private static final int SIZE = 8;
    private ChessPiece[][] board;
    private PieceColor currentPlayerColor = PieceColor.WHITE;
    private ChessPiece selectedPiece;
    private ChessBot bot = new ChessBot(); // Bot inicializálása
    private boolean isBotGame = false; // Bot játék mód flag

    public void startNewGame(boolean isBotGame) {
        this.isBotGame = isBotGame;
        resetBoard(); // Új játék indítása
        setCurrentPlayerColor(PieceColor.WHITE); // Visszaállítás a fehér játékosra
        System.out.println("Új játék indítva! Bot ellen: " + isBotGame);
    }

    public ChessBoard() {
        setLayout(new GridLayout(SIZE, SIZE));
        board = new ChessPiece[SIZE][SIZE];
        initializeBoard();
        initializePieces();
    }

    private void initializeBoard() {
        removeAll();
        setLayout(new GridLayout(SIZE, SIZE));

        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                JPanel square = new JPanel();
                square.setBackground((row + col) % 2 == 0 ? Color.WHITE : Color.GRAY);

                final int r = row;
                final int c = col;

                square.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent evt) {
                        squareClicked(r, c);
                    }
                });

                add(square);
            }
        }

        revalidate();
        repaint();
    }

    private void initializePieces() {
        board[0][0] = new Rook(PieceColor.BLACK, 0, 0);
        board[0][1] = new Knight(PieceColor.BLACK, 0, 1);
        board[0][2] = new Bishop(PieceColor.BLACK, 0, 2);
        board[0][3] = new Queen(PieceColor.BLACK, 0, 3);
        board[0][4] = new King(PieceColor.BLACK, 0, 4);
        board[0][5] = new Bishop(PieceColor.BLACK, 0, 5);
        board[0][6] = new Knight(PieceColor.BLACK, 0, 6);
        board[0][7] = new Rook(PieceColor.BLACK, 0, 7);
        for (int i = 0; i < SIZE; i++) {
            board[1][i] = new Pawn(PieceColor.BLACK, 1, i);
        }

        board[7][0] = new Rook(PieceColor.WHITE, 7, 0);
        board[7][1] = new Knight(PieceColor.WHITE, 7, 1);
        board[7][2] = new Bishop(PieceColor.WHITE, 7, 2);
        board[7][3] = new Queen(PieceColor.WHITE, 7, 3);
        board[7][4] = new King(PieceColor.WHITE, 7, 4);
        board[7][5] = new Bishop(PieceColor.WHITE, 7, 5);
        board[7][6] = new Knight(PieceColor.WHITE, 7, 6);
        board[7][7] = new Rook(PieceColor.WHITE, 7, 7);
        for (int i = 0; i < SIZE; i++) {
            board[6][i] = new Pawn(PieceColor.WHITE, 6, i);
        }

        updateBoard();
    }

    public void updateBoard() {
        removeAll();
        setLayout(new GridLayout(SIZE, SIZE)); // Győződjünk meg róla, hogy a GridLayout beállításra kerül

        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                JPanel square = new JPanel();
                square.setBackground((row + col) % 2 == 0 ? Color.WHITE : Color.GRAY);

                ChessPiece piece = board[row][col];
                if (piece != null) {
                    JLabel label = new JLabel(getPieceIcon(piece));
                    square.add(label);
                }

                final int r = row;
                final int c = col;
                square.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent evt) {
                        squareClicked(r, c);
                    }
                });

                add(square);
            }
        }

        revalidate();
        repaint();
    }

    private ImageIcon getPieceIcon(ChessPiece piece) {
        if (piece == null) return null;
        return new ImageIcon(piece.getIconPath());
    }

    private void squareClicked(int row, int col) {
        System.out.println("Square clicked: [" + row + ", " + col + "]");

        ChessPiece clickedPiece = board[row][col];

        if (selectedPiece == null) {
            if (clickedPiece != null && clickedPiece.getColor() == currentPlayerColor) {
                selectedPiece = clickedPiece;
                System.out.println("Selected piece: " + selectedPiece.getType());
            }
        } else {
            int startRow = selectedPiece.getRow();
            int startCol = selectedPiece.getCol();

            System.out.println("Trying to move from [" + startRow + ", " + startCol + "] to [" + row + ", " + col + "]");

            if (selectedPiece.isValidMove(startRow, startCol, row, col, board)) {
                boolean isCastlingMove = selectedPiece.getType() == PieceType.KING && Math.abs(startCol - col) == 2;
                if (isCastlingMove) {
                    Rook rook = (Rook) board[startRow][(col == 6) ? 7 : 0];
                    if (rook != null && CastlingRules.canCastle(board, (King) selectedPiece, rook)) {
                        CastlingRules.performCastling(board, (King) selectedPiece, rook);
                        System.out.println("Castling performed: King to [" + row + ", " + col + "], Rook to [" + startRow + ", " + ((col == 6) ? 5 : 3) + "]");

                        currentPlayerColor = (currentPlayerColor == PieceColor.WHITE) ? PieceColor.BLACK : PieceColor.WHITE;

                        // Ellenőrizzük, hogy sakkban van-e a király
                        if (CheckRules.isKingInCheck(board, currentPlayerColor)) {
                            System.out.println("Check!");
                            if (CheckRules.isCheckmate(board, currentPlayerColor)) {
                                System.out.println("Checkmate! " + (currentPlayerColor == PieceColor.WHITE ? "Black" : "White") + " wins!");
                                JOptionPane.showMessageDialog(this, "Checkmate! " + (currentPlayerColor == PieceColor.WHITE ? "Black" : "White") + " wins!");
                                return;
                            }
                        }

                        if (CheckRules.isStalemate(board, currentPlayerColor)) {
                            System.out.println("Stalemate! The game is a draw.");
                            JOptionPane.showMessageDialog(this, "Stalemate! The game is a draw.");
                            return;
                        } else if (CheckRules.isInsufficientMaterial(board)) {
                            System.out.println("Draw due to insufficient material.");
                            JOptionPane.showMessageDialog(this, "Draw due to insufficient material.");
                            return;
                        }

                        // Ha bot játék van, akkor a bot lépése következik
                        if (isBotGame && currentPlayerColor == PieceColor.BLACK) {
                            bot.makeMove(board, PieceColor.BLACK);
                            currentPlayerColor = PieceColor.WHITE;
                            System.out.println("Bot lépése megtörtént.");

                            if (CheckRules.isKingInCheck(board, currentPlayerColor)) {
                                System.out.println("Check!");
                                if (CheckRules.isCheckmate(board, currentPlayerColor)) {
                                    System.out.println("Checkmate! " + (currentPlayerColor == PieceColor.WHITE ? "Black" : "White") + " wins!");
                                    JOptionPane.showMessageDialog(this, "Checkmate! " + (currentPlayerColor == PieceColor.WHITE ? "Black" : "White") + " wins!");
                                    return;
                                }
                            }

                            if (CheckRules.isStalemate(board, currentPlayerColor)) {
                                System.out.println("Stalemate! The game is a draw.");
                                JOptionPane.showMessageDialog(this, "Stalemate! The game is a draw.");
                                return;
                            } else if (CheckRules.isInsufficientMaterial(board)) {
                                System.out.println("Draw due to insufficient material.");
                                JOptionPane.showMessageDialog(this, "Draw due to insufficient material.");
                                return;
                            }
                        }

                        selectedPiece = null;
                        updateBoard();
                        return; // Sáncolás után kilépés a metódusból
                    }
                }

                if (selectedPiece instanceof Pawn && EnPassantRules.canEnPassant(board, (Pawn) selectedPiece, row, col)) {
                    EnPassantRules.performEnPassant(board, (Pawn) selectedPiece, row, col);
                    System.out.println("En passant move performed.");
                } else {
                    ChessPiece originalDestPiece = board[row][col];
                    board[row][col] = selectedPiece;
                    board[startRow][startCol] = null;
                    selectedPiece.setPosition(row, col);

                    boolean isCurrentPlayerKingInCheck = CheckRules.isKingInCheck(board, currentPlayerColor);

                    if (isCurrentPlayerKingInCheck) {
                        board[startRow][startCol] = selectedPiece;
                        board[row][col] = originalDestPiece;
                        selectedPiece.setPosition(startRow, startCol);
                        System.out.println("Invalid move: King cannot be in check.");
                    } else {
                        System.out.println("Moved piece: " + selectedPiece.getType() + " to [" + row + ", " + col + "]");

                        if (selectedPiece instanceof Pawn) {
                            EnPassantRules.recordLastMove(startRow, startCol, row, col);
                            if ((selectedPiece.getColor() == PieceColor.WHITE && row == 0) ||
                                    (selectedPiece.getColor() == PieceColor.BLACK && row == 7)) {
                                String[] options = {"Queen", "Rook", "Bishop", "Knight"};
                                String choice = (String) JOptionPane.showInputDialog(
                                        null,
                                        "Válassz egy bábut a promotinghoz:",
                                        "Pawn Promotion",
                                        JOptionPane.PLAIN_MESSAGE,
                                        null,
                                        options,
                                        options[0]
                                );
                                if (choice != null) {
                                    ChessPiece promotedPiece = ((Pawn) selectedPiece).promote(choice);
                                    board[row][col] = promotedPiece;
                                    System.out.println("Pawn promoted to " + choice);
                                }
                            }
                        }

                        currentPlayerColor = (currentPlayerColor == PieceColor.WHITE) ? PieceColor.BLACK : PieceColor.WHITE;

                        if (CheckRules.isKingInCheck(board, currentPlayerColor)) {
                            System.out.println("Check!");
                            if (CheckRules.isCheckmate(board, currentPlayerColor)) {
                                System.out.println("Checkmate! " + (currentPlayerColor == PieceColor.WHITE ? "Black" : "White") + " wins!");
                                JOptionPane.showMessageDialog(this, "Checkmate! " + (currentPlayerColor == PieceColor.WHITE ? "Black" : "White") + " wins!");
                                return;
                            }
                        }

                        if (CheckRules.isStalemate(board, currentPlayerColor)) {
                            System.out.println("Stalemate! The game is a draw.");
                            JOptionPane.showMessageDialog(this, "Stalemate! The game is a draw.");
                            return;
                        } else if (CheckRules.isInsufficientMaterial(board)) {
                            System.out.println("Draw due to insufficient material.");
                            JOptionPane.showMessageDialog(this, "Draw due to insufficient material.");
                            return;
                        }

                        if (isBotGame && currentPlayerColor == PieceColor.BLACK) {
                            bot.makeMove(board, PieceColor.BLACK);
                            currentPlayerColor = PieceColor.WHITE;
                            System.out.println("Bot lépése megtörtént.");

                            if (CheckRules.isKingInCheck(board, currentPlayerColor)) {
                                System.out.println("Check!");
                                if (CheckRules.isCheckmate(board, currentPlayerColor)) {
                                    System.out.println("Checkmate! " + (currentPlayerColor == PieceColor.WHITE ? "Black" : "White") + " wins!");
                                    JOptionPane.showMessageDialog(this, "Checkmate! " + (currentPlayerColor == PieceColor.WHITE ? "Black" : "White") + " wins!");
                                    return;
                                }
                            }

                            if (CheckRules.isStalemate(board, currentPlayerColor)) {
                                System.out.println("Stalemate! The game is a draw.");
                                JOptionPane.showMessageDialog(this, "Stalemate! The game is a draw.");
                                return;
                            } else if (CheckRules.isInsufficientMaterial(board)) {
                                System.out.println("Draw due to insufficient material.");
                                JOptionPane.showMessageDialog(this, "Draw due to insufficient material.");
                                return;
                            }
                        }
                    }
                }
            } else {
                System.out.println("Invalid move!");
            }
            selectedPiece = null;
        }

        // Frissítés
        updateBoard();
    }

    // Getter metódus a board-hoz
    public ChessPiece[][] getBoard() {
        return board;
    }

    // Getter metódus a currentPlayerColor-hoz
    public PieceColor getCurrentPlayerColor() {
        return currentPlayerColor;
    }

    // Setter metódus a currentPlayerColor-hoz
    public void setCurrentPlayerColor(PieceColor currentPlayerColor) {
        this.currentPlayerColor = currentPlayerColor;
    }

    public void resetBoard() {
        // A tábla visszaállítása a kezdeti állapotra
        board = new ChessPiece[SIZE][SIZE]; // A sakktábla újrainicializálása
        initializePieces(); // A figurák újrainicializálása
    }
}