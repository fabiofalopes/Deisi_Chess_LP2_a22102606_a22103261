package pt.ulusofona.lp2.deisichess;

import java.util.ArrayList;

public abstract class Movement {
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
