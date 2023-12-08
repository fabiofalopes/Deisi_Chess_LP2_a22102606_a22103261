package refactor.movements;

import refactor.Square;
import java.util.List;

public abstract class BaseMovement {
    protected boolean vertical;
    protected boolean horizontal;
    protected boolean diagonal;

    public boolean isVertical(){
        return this.vertical;
    }

    public boolean isHorizontal(){
        return this.horizontal;
    }

    public boolean isDiagonal(){
        return this.diagonal;
    }

    public static boolean isWithinBounds(List<List<Square>> board,
                                 int destinyX,
                                 int destinyY) {
        int rows = board.size();
        int columns = board.get(0).size();

        return (destinyX >= 0 && destinyX < columns) &&
               (destinyY >= 0 && destinyY < rows);
    }

    public abstract boolean isOverlapping(List<List<Square>> board,
                                          int currentX,
                                          int currentY,
                                          int destinyX,
                                          int destinyY);
}
