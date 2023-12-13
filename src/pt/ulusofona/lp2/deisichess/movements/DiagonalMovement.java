package pt.ulusofona.lp2.deisichess.movements;

import pt.ulusofona.lp2.deisichess.Square;
import java.util.List;

public class DiagonalMovement extends BaseMovement{
    public DiagonalMovement(){
        super();
        this.diagonal = true;
    }

    @Override
    public boolean isOverlapping(List<List<Square>> board,
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
            Square square = board.get(y).get(x);
            if (square.hasPiece()) {
                return true; // overlap found
            }

            x += xDir;
            y += yDir;
        }

        return false;
    }
}
