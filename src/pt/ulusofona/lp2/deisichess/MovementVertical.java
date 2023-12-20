package pt.ulusofona.lp2.deisichess;

import java.util.ArrayList;

public class MovementVertical extends Movement{
    @Override
    public boolean isVertical(){ return true; }

    @Override
    public boolean isOverlapping(ArrayList<Piece> pieces,
                                 int currentX,
                                 int currentY,
                                 int destinyX,
                                 int destinyY) {
        int start = currentY;
        int end = destinyY;

        if (currentY > destinyY) { // is moving backwards
            start = destinyY + 1;
            end = currentY;
        }
        else { // is moving forwards
            start += 1;
        }

        for (int i = start; i < end; i++) {
            for (Piece piece : pieces) {
                if(piece.isOnPosition(destinyX, i)){
                    return true; // overlap found
                }
            }
        }

        return false;
    }
}
