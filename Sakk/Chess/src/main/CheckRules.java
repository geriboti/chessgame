package main;

import piece.*;

public class CheckRules {

    public static boolean isKingInCheck(ChessPiece[][] board, PieceColor kingColor) {
        int kingRow = -1;
        int kingCol = -1;

        // Keressük meg a király pozícióját
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                ChessPiece piece = board[row][col];
                if (piece != null && piece.getType() == PieceType.KING && piece.getColor() == kingColor) {
                    kingRow = row;
                    kingCol = col;
                    break;
                }
            }
        }

        return isAttackedByEnemy(board, kingRow, kingCol, kingColor);
    }

    public static boolean isAttackedByEnemy(ChessPiece[][] board, int row, int col, PieceColor kingColor) {
        return attackedByPawn(board, row, col, kingColor) ||
                attackedByKnight(board, row, col, kingColor) ||
                attackedByBishop(board, row, col, kingColor) ||
                attackedByRook(board, row, col, kingColor) ||
                attackedByQueen(board, row, col, kingColor) ||
                attackedByKing(board, row, col, kingColor);
    }

    private static boolean attackedByPawn(ChessPiece[][] board, int row, int col, PieceColor kingColor) {
        int direction = (kingColor == PieceColor.WHITE) ? -1 : 1;
        int[] pawnRows = {row + direction, row + direction};
        int[] pawnCols = {col - 1, col + 1};

        for (int i = 0; i < 2; i++) {
            if (isInBounds(pawnRows[i], pawnCols[i])) {
                ChessPiece piece = board[pawnRows[i]][pawnCols[i]];
                if (piece != null && piece.getType() == PieceType.PAWN && piece.getColor() != kingColor) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean attackedByKnight(ChessPiece[][] board, int row, int col, PieceColor kingColor) {
        int[][] moves = {
                {row - 2, col - 1}, {row - 2, col + 1},
                {row - 1, col - 2}, {row - 1, col + 2},
                {row + 1, col - 2}, {row + 1, col + 2},
                {row + 2, col - 1}, {row + 2, col + 1}
        };

        for (int[] move : moves) {
            if (isInBounds(move[0], move[1])) {
                ChessPiece piece = board[move[0]][move[1]];
                if (piece != null && piece.getType() == PieceType.KNIGHT && piece.getColor() != kingColor) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean attackedByBishop(ChessPiece[][] board, int row, int col, PieceColor kingColor) {
        int[][] directions = {
                {1, 1}, {1, -1}, {-1, 1}, {-1, -1}
        };

        for (int[] direction : directions) {
            int newRow = row, newCol = col;
            while (true) {
                newRow += direction[0];
                newCol += direction[1];
                if (!isInBounds(newRow, newCol)) {
                    break;
                }
                ChessPiece piece = board[newRow][newCol];
                if (piece != null) {
                    if (piece.getType() == PieceType.BISHOP && piece.getColor() != kingColor) {
                        return true;
                    } else {
                        break;
                    }
                }
            }
        }
        return false;
    }

    private static boolean attackedByRook(ChessPiece[][] board, int row, int col, PieceColor kingColor) {
        int[][] directions = {
                {0, 1}, {0, -1}, {1, 0}, {-1, 0}
        };

        for (int[] direction : directions) {
            int newRow = row, newCol = col;
            while (true) {
                newRow += direction[0];
                newCol += direction[1];
                if (!isInBounds(newRow, newCol)) {
                    break;
                }
                ChessPiece piece = board[newRow][newCol];
                if (piece != null) {
                    if (piece.getType() == PieceType.ROOK && piece.getColor() != kingColor) {
                        return true;
                    } else {
                        break;
                    }
                }
            }
        }
        return false;
    }

    private static boolean attackedByQueen(ChessPiece[][] board, int row, int col, PieceColor kingColor) {
        int[][] directions = {
                {0, 1}, {0, -1}, {1, 0}, {-1, 0},
                {1, 1}, {1, -1}, {-1, 1}, {-1, -1}
        };

        for (int[] direction : directions) {
            int newRow = row, newCol = col;
            while (true) {
                newRow += direction[0];
                newCol += direction[1];
                if (!isInBounds(newRow, newCol)) {
                    break;
                }
                ChessPiece piece = board[newRow][newCol];
                if (piece != null) {
                    if (piece.getType() == PieceType.QUEEN && piece.getColor() != kingColor) {
                        return true;
                    } else {
                        break;
                    }
                }
            }
        }
        return false;
    }

    private static boolean attackedByKing(ChessPiece[][] board, int row, int col, PieceColor kingColor) {
        int[][] moves = {
                {row - 1, col - 1}, {row - 1, col}, {row - 1, col + 1},
                {row, col - 1},               {row, col + 1},
                {row + 1, col - 1}, {row + 1, col}, {row + 1, col + 1}
        };

        for (int[] move : moves) {
            if (isInBounds(move[0], move[1])) {
                ChessPiece piece = board[move[0]][move[1]];
                if (piece != null && piece.getType() == PieceType.KING && piece.getColor() != kingColor) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean isInBounds(int row, int col) {
        return row >= 0 && row < 8 && col >= 0 && col < 8;
    }

    public static boolean isSafeMove(ChessPiece[][] board, int startRow, int startCol, int endRow, int endCol, PieceColor kingColor) {
        ChessPiece[][] newBoard = copyBoard(board);
        ChessPiece movingPiece = newBoard[startRow][startCol];
        newBoard[endRow][endCol] = movingPiece;
        newBoard[startRow][startCol] = null;
        return !isKingInCheck(newBoard, kingColor);
    }

    private static ChessPiece[][] copyBoard(ChessPiece[][] board) {
        ChessPiece[][] newBoard = new ChessPiece[8][8];
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                newBoard[row][col] = board[row][col];
            }
        }
        return newBoard;
    }

    public static boolean isCheckmate(ChessPiece[][] board, PieceColor kingColor) {
        int kingRow = -1;
        int kingCol = -1;

        // Keressük meg a király pozícióját
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                ChessPiece piece = board[row][col];
                if (piece != null && piece.getType() == PieceType.KING && piece.getColor() == kingColor) {
                    kingRow = row;
                    kingCol = col;
                    break;
                }
            }
        }

        // Ellenőrizzük, hogy a király ki tud-e lépni a sakkból
        for (int dr = -1; dr <= 1; dr++) {
            for (int dc = -1; dc <= 1; dc++) {
                if (dr != 0 || dc != 0) {
                    int newRow = kingRow + dr;
                    int newCol = kingCol + dc;
                    if (isInBounds(newRow, newCol) && (board[newRow][newCol] == null || board[newRow][newCol].getColor() != kingColor)) {
                        ChessPiece originalPiece = board[newRow][newCol];
                        board[newRow][newCol] = board[kingRow][kingCol];
                        board[kingRow][kingCol] = null;
                        if (!isAttackedByEnemy(board, newRow, newCol, kingColor)) {
                            board[kingRow][kingCol] = board[newRow][newCol];
                            board[newRow][newCol] = originalPiece;
                            return false;
                        }
                        board[kingRow][kingCol] = board[newRow][newCol];
                        board[newRow][newCol] = originalPiece;
                    }
                }
            }
        }

        // Ellenőrizzük, hogy van-e másik bábu, amelyik megvédheti a királyt
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                ChessPiece piece = board[row][col];
                if (piece != null && piece.getColor() == kingColor && piece.getType() != PieceType.KING) {
                    for (int newRow = 0; newRow < 8; newRow++) {
                        for (int newCol = 0; newCol < 8; newCol++) {
                            if (piece.isValidMove(row, col, newRow, newCol, board)) {
                                ChessPiece originalPiece = board[newRow][newCol];
                                board[newRow][newCol] = piece;
                                board[row][col] = null;
                                if (!isKingInCheck(board, kingColor)) {
                                    board[row][col] = piece;
                                    board[newRow][newCol] = originalPiece;
                                    return false;
                                }
                                board[row][col] = piece;
                                board[newRow][newCol] = originalPiece;
                            }
                        }
                    }
                }
            }
        }

        return true; // Ha semmilyen lépés nem tudja megvédeni a királyt, akkor sakkmatt
    }


    public static boolean isStalemate(ChessPiece[][] board, PieceColor kingColor) {
        if (isKingInCheck(board, kingColor)) {
            return false; // A király sakkban van, ez nem patt helyzet
        }

        // Ellenőrizzük, hogy van-e érvényes lépés
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                ChessPiece piece = board[row][col];
                if (piece != null && piece.getColor() == kingColor) {
                    for (int newRow = 0; newRow < board.length; newRow++) {
                        for (int newCol = 0; newCol < board[newRow].length; newCol++) {
                            if (piece.isValidMove(row, col, newRow, newCol, board)) {
                                ChessPiece originalDestPiece = board[newRow][newCol];
                                board[newRow][newCol] = piece;
                                board[row][col] = null;
                                boolean isStillInCheck = isKingInCheck(board, kingColor);
                                board[row][col] = piece;
                                board[newRow][newCol] = originalDestPiece;
                                if (!isStillInCheck) {
                                    return false; // Van érvényes lépés, ez nem patt helyzet
                                }
                            }
                        }
                    }
                }
            }
        }
        return true; // Nincs érvényes lépés, patt helyzet
    }

    public static boolean isInsufficientMaterial(ChessPiece[][] board) {
        int whiteBishops = 0, blackBishops = 0;
        int whiteKnights = 0, blackKnights = 0;
        int whiteKings = 0, blackKings = 0;
        boolean whiteHasOtherPieces = false, blackHasOtherPieces = false;

        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                ChessPiece piece = board[row][col];
                if (piece != null) {
                    switch (piece.getType()) {
                        case KING:
                            if (piece.getColor() == PieceColor.WHITE) whiteKings++;
                            else blackKings++;
                            break;
                        case BISHOP:
                            if (piece.getColor() == PieceColor.WHITE) whiteBishops++;
                            else blackBishops++;
                            break;
                        case KNIGHT:
                            if (piece.getColor() == PieceColor.WHITE) whiteKnights++;
                            else blackKnights++;
                            break;
                        default:
                            if (piece.getColor() == PieceColor.WHITE) whiteHasOtherPieces = true;
                            else blackHasOtherPieces = true;
                            break;
                    }
                }
            }
        }

        // Patt helyzet, ha csak a királyok maradtak
        if (whiteKings == 1 && blackKings == 1 &&
                !whiteHasOtherPieces && !blackHasOtherPieces &&
                whiteBishops == 0 && blackBishops == 0 &&
                whiteKnights == 0 && blackKnights == 0) {
            return true;
        }

        // Patt helyzet, ha csak a királyok és egy-egy futó vagy ló maradtak
        if ((whiteKings == 1 && !whiteHasOtherPieces && (whiteBishops == 1 || whiteKnights == 1) &&
                blackKings == 1 && !blackHasOtherPieces && blackBishops == 0 && blackKnights == 0) ||
                (blackKings == 1 && !blackHasOtherPieces && (blackBishops == 1 || blackKnights == 1) &&
                        whiteKings == 1 && !whiteHasOtherPieces && whiteBishops == 0 && whiteKnights == 0)) {
            return true;
        }

        return false;
    }
}
