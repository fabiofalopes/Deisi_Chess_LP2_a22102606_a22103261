package pt.ulusofona.lp2.deisichess;

import java.util.ArrayList;

public class MovementHorizontal extends Movement{
    @Override
    public boolean isOverlapping(ArrayList<Piece> pieces,
                                 int currentX,
                                 int currentY,
                                 int destinyX,
                                 int destinyY) {
        int start = currentX;
        int end = destinyX;

        if (currentX > destinyX) { // is moving backwards
            start = destinyX + 1;
            end = currentX;
        }
        else { // is moving forwards
            start += 1;
        }

        for (int i = start; i < end; i++) {
            for (Piece piece : pieces) {
                if(piece.isOnPosition(i, destinyY)){
                    return true; // overlap found
                }
            }
        }

        return false;
    }
}
