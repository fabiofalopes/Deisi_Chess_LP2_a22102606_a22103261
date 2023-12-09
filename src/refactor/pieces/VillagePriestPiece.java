package refactor.pieces;

import refactor.Square;
import refactor.Team;
import refactor.movements.DiagonalMovement;
import java.util.List;

public class VillagePriestPiece extends BasePiece{
    public static final int ID = 4;

    public VillagePriestPiece(String nickname, Team team) {
        super(nickname, team);
        this.id = ID;
        this.typeName = "Padre da Vila";
        this.value = 3;
        this.movementLimit = 3;
    }

    @Override
    public boolean validMoveRules(List<List<Square>> board, int destinyX, int destinyY) {
        int currentX = this.square.getX(),
            currentY = this.square.getY();
        int deltaX = Math.abs(currentX - destinyX),
            deltaY = Math.abs(currentY - destinyY);

        // means the piece didn't move diagonally
        if(deltaX != deltaY &&
           deltaX < this.movementLimit &&
           deltaY < this.movementLimit ) {
            return false;
        }

        return !new DiagonalMovement().isOverlapping(board,currentX, currentY, destinyX, destinyY);
    }
}
