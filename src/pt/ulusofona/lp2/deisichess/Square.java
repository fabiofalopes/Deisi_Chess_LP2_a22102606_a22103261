package pt.ulusofona.lp2.deisichess;

public class Square {
    private int coordX;
    private int coordY;
    private boolean isWhite;
    private ChessPiece piece;

    public Square(int coordX, int coordY, boolean isWhite, ChessPiece piece){
        this.coordX = coordX;
        this.coordY = coordY;
        this.isWhite = isWhite;
        this.piece = piece;
    }

    // region Methods
    public boolean equals(int coordX, int coordY){
        return this.coordX == coordX && this.coordY == coordY;
    }
    public ChessPiece getPiece(){
        return this.piece;
    }
    // endregion
}
