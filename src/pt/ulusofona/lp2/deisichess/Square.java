package pt.ulusofona.lp2.deisichess;

public class Square {
    private int coordX;
    private int coordY;
    private ChessPiece piece;

    public Square(int coordX, int coordY){
        this.coordX = coordX;
        this.coordY = coordY;
    }
    public ChessPiece getPiece(){
        return this.piece;
    }
    public void updatePiece(ChessPiece piece){
        this.piece = piece;
    }
    public boolean equals(int x, int y){
        return this.coordX == x && this.coordY == y;
    }
}
