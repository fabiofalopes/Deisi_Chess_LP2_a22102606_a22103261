package refactor.movements;

import refactor.Square;
import java.util.List;

public abstract class BaseMovement {
    protected boolean vertical;
    protected boolean horizontal;
    protected boolean diagonal;
    protected boolean lShape;

    public boolean isVertical(){
        return this.vertical;
    }
    public boolean isHorizontal(){
        return this.horizontal;
    }
    public boolean isDiagonal(){ return this.diagonal; }
    public boolean isLShape(){ return this.lShape; }

    public static boolean isWithinBounds(List<List<Square>> board, int x, int y) {
        int rows = board.size();
        int columns = board.get(0).size();

        return (x >= 0 && x < columns) &&
               (y >= 0 && y < rows);
    }
    public abstract boolean isOverlapping(List<List<Square>> board,
                                          int currentX,
                                          int currentY,
                                          int destinyX,
                                          int destinyY);
}
