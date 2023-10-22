package pt.ulusofona.lp2.deisichess;

public class Square {
    int id;
    int coordX;
    int coordY;
    boolean white;
    ChessPiece piece;


    public Square(int id, int coordX, int coordY){
        this.id = id;
        this.coordX = coordX;
        this.coordY = coordY;
        this.white = false;
        this.piece = null;
    }

    public Square(int id, int coordX, int coordY, boolean white){
        this.id = id;
        this.coordX = coordX;
        this.coordY = coordY;
        this.white = white;
        this.piece = null;
    }

    public Square(int id, int coordX, int coordY, boolean white, ChessPiece piece){
        this.id = id;
        this.coordX = coordX;
        this.coordY = coordY;
        this.white = white;
        this.piece = piece;
    }
}
