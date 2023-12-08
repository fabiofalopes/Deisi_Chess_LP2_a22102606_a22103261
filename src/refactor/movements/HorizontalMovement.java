package refactor.movements;

import refactor.Square;
import java.util.List;

public class HorizontalMovement extends BaseMovement{
    public HorizontalMovement(){
        super();
        this.horizontal = true;
    }

    @Override
    public boolean isOverlapping(List<List<Square>> board,
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
            Square square = board.get(destinyY).get(i);
            if (square.hasPiece()) {
                return true; // overlap found
            }
        }

        return false;
    }
}
