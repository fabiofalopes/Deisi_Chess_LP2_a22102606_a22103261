package pt.ulusofona.lp2.deisichess;

import java.util.ArrayList;

public class MovementLShape extends Movement{
    @Override
    public boolean isLShape(){ return true; }

    private boolean isOverlappingFromSectionA(ArrayList<Piece> pieces,
                                              int minX,
                                              int maxX,
                                              int minY,
                                              int maxY){

        for (int x = minX + 1; x <= maxX; x++) {
            for (Piece piece : pieces) {
                if(piece.isOnPosition(x, minY)){
                    return true; // overlap found
                }
            }
        }

        for (int y = minY + 1; y < maxY; y++) {
            for (Piece piece : pieces) {
                if(piece.isOnPosition(maxX, y)){
                    return true; // overlap found
                }
            }
        }

        return false;
    }

    private boolean isOverlappingFromSectionB(ArrayList<Piece> pieces,
                                              int minX,
                                              int maxX,
                                              int minY,
                                              int maxY){

        for (int y = maxY - 1; y > minY; y--) {
            for (Piece piece : pieces) {
                if(piece.isOnPosition(minX, y)){
                    return true; // overlap found
                }
            }
        }

        for (int x = minX; x <= maxX - 1; x++) {
            for (Piece piece : pieces) {
                if(piece.isOnPosition(x, maxY)){
                    return true; // overlap found
                }
            }
        }

        return false;
    }

    @Override
    public boolean isOverlapping(ArrayList<Piece> pieces,
                                 int currentX,
                                 int currentY,
                                 int destinyX,
                                 int destinyY) {
        int minX = Math.min(currentX, destinyX),
            maxX = Math.max(currentX, destinyX),
            minY = Math.min(currentY, destinyY),
            maxY = Math.max(currentY, destinyY);

        return (this.isOverlappingFromSectionA(pieces, minX, maxX, minY, maxY) &&
                this.isOverlappingFromSectionB(pieces, minX, maxX, minY, maxY));
    }
}
