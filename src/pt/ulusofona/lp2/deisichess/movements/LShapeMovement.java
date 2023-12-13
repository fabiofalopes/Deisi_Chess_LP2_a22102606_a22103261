package pt.ulusofona.lp2.deisichess.movements;

import pt.ulusofona.lp2.deisichess.Square;
import java.util.List;

public class LShapeMovement extends BaseMovement {
    public LShapeMovement(){
        super();
        this.lShape = true;
    }


    private boolean isOverlappingFromSectionA(List<List<Square>> board,
                                              int minX,
                                              int maxX,
                                              int minY,
                                              int maxY){

        for (int x = minX + 1; x <= maxX; x++) {
            if(board.get(minY).get(x).hasPiece()){
                return true;
            }
        }

        for (int y = minY + 1; y < maxY; y++) {
            if(board.get(y).get(maxX).hasPiece()){
                return true;
            }
        }

        return false;
    }
    private boolean isOverlappingFromSectionB(List<List<Square>> board,
                                              int minX,
                                              int maxX,
                                              int minY,
                                              int maxY){

        for (int y = maxY - 1; y > minY; y--) {
            if(board.get(y).get(minX).hasPiece()){
                return true;
            }
        }

        for (int x = minX; x <= maxX - 1; x++) {
            if(board.get(maxY).get(x).hasPiece()){
                return true;
            }
        }

        return false;
    }
    @Override
    public boolean isOverlapping(List<List<Square>> board,
                                 int currentX,
                                 int currentY,
                                 int destinyX,
                                 int destinyY) {

        int minX = Math.min(currentX, destinyX),
            maxX = Math.max(currentX, destinyX),
            minY = Math.min(currentY, destinyY),
            maxY = Math.max(currentY, destinyY);

        return (this.isOverlappingFromSectionA(board, minX, maxX, minY, maxY) &&
                this.isOverlappingFromSectionB(board, minX, maxX, minY, maxY));
    }
}
