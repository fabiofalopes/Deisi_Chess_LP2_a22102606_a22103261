package pt.ulusofona.lp2.deisichess;

import java.util.ArrayList;

public abstract class Movement {
    public boolean isVertical(){ return false; }
    public boolean isHorizontal(){ return false; }
    public boolean isDiagonal(){ return false; }
    public boolean isLShape(){ return false; }

    public static boolean isWithinBounds(ArrayList<Square> squares, int destinyX, int destinyY){
        for (Square square : squares) {
            if(square.equals(destinyX, destinyY)){
                return true;
            }
        }

        return false;
    }

    public abstract boolean isOverlapping(ArrayList<Piece> pieces,
                                          int currentX,
                                          int currentY,
                                          int destinyX,
                                          int destinyY);
}
