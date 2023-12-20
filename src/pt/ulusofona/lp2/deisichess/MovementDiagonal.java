package pt.ulusofona.lp2.deisichess;

import java.util.ArrayList;

public class MovementDiagonal extends Movement {
    @Override
    public boolean isOverlapping(ArrayList<Piece> pieces,
                                 int currentX,
                                 int currentY,
                                 int destinyX,
                                 int destinyY) {
        // Integer compare (on equals) = 0; (on <) - 1; (on >) 1
        int xDir = Integer.compare(destinyX, currentX);
        int yDir = Integer.compare(destinyY, currentY);
        int x = currentX + xDir;
        int y = currentY + yDir;

        while (x != destinyX || y != destinyY) {
            for (Piece piece : pieces) {
                if(piece.isOnPosition(x, y)){
                    return true; // overlap found
                }
            }

            x += xDir;
            y += yDir;
        }

        return false;
    }
}
