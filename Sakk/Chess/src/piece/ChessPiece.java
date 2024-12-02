package piece;

public abstract class ChessPiece implements Cloneable {
    private PieceType type;
    private PieceColor color;
    private String iconPath;
    private int row;
    private int col;
    private boolean firstMove = true;

    @Override
    public ChessPiece clone() {
        try {
            return (ChessPiece) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    public ChessPiece(PieceType type, PieceColor color, String iconPath, int row, int col) {
        this.type = type;
        this.color = color;
        this.iconPath = iconPath;
        this.row = row;
        this.col = col;
    }

    public PieceType getType() {
        return type;
    }

    public PieceColor getColor() {
        return color;
    }

    public String getIconPath() {
        return iconPath;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void setPosition(int row, int col) {
        this.row = row;
        this.col = col;
        this.firstMove = false;
    }

    public boolean isFirstMove() {
        return firstMove;
    }

    public void setFirstMove(boolean firstMove) {
        this.firstMove = firstMove;
    }

    public abstract boolean isValidMove(int startX, int startY, int endX, int endY, ChessPiece[][] board);

    public boolean checkDestinationPiece(int endX, int endY, ChessPiece[][] board) {
        ChessPiece destinationPiece = board[endY][endX];
        System.out.println("Checking destination piece at (" + endX + ", " + endY + "): " +
                (destinationPiece != null ? destinationPiece.getColor() + " " + destinationPiece.getType() : "none"));

        // Saját színű bábu ellenőrzése
        if (destinationPiece != null && destinationPiece.getColor() == this.color) {
            System.out.println("Invalid move: cannot capture own piece at (" + endX + ", " + endY + ")");
            return false;
        }

        // Király ellenőrzése
        if (destinationPiece != null && destinationPiece.getType() == PieceType.KING) {
            System.out.println("Invalid move: cannot capture the king at (" + endX + ", " + endY + ")");
            return false;
        }

        return true;
    }

    protected boolean isPathClear(int startRow, int startCol, int endRow, int endCol, ChessPiece[][] board) {
        int rowDirection = Integer.signum(endRow - startRow);
        int colDirection = Integer.signum(endCol - startCol);
        int currentRow = startRow + rowDirection;
        int currentCol = startCol + colDirection;
        while (currentRow != endRow || currentCol != endCol) {
            if (board[currentRow][currentCol] != null) {
                return false;
            }
            currentRow += rowDirection;
            currentCol += colDirection;
        }
        return true;
    }
}

