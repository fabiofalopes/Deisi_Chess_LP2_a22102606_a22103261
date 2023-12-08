package refactor;

import refactor.pieces.BasePiece;

public class Square {
    private int x;
    private int y;
    private BasePiece piece;

    Square(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    public BasePiece getPiece(){
        return this.piece;
    }

    public boolean hasPiece(){
        return this.piece != null;
    }

    public void setPiece(BasePiece piece){
        this.piece = piece;
    }

    public void removePiece(){
        this.piece = null;
    }
}
