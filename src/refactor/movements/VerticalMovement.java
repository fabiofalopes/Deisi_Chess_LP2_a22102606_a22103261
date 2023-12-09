package refactor.movements;

import refactor.Square;
import java.util.List;

public class VerticalMovement extends BaseMovement{
    public VerticalMovement(){
        super();
        this.vertical = true;
    }

    @Override
    public boolean isOverlapping(List<List<Square>> board,
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
            Square square = board.get(i).get(destinyX);
            if (square.hasPiece()) {
                return true; // overlap found
            }
        }

        return false;
    }
}
