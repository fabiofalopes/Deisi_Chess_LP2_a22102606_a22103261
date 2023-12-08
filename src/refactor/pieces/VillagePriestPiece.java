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
    public boolean validMoveRules(List<List<Square>> board, int x, int y) {

        int currentX = this.square.getX();
        int currentY = this.square.getY();

        int deltaX = Math.abs(currentX - x);
        int deltaY = Math.abs(currentY - y);

        if(deltaX != deltaY && deltaX < this.movementLimit && deltaY < this.movementLimit ) { // means the piece didn't move diagonally
            return false;
        }

        return !new DiagonalMovement().isOverlapping(board,currentX, currentY, x, y);
    }
}
