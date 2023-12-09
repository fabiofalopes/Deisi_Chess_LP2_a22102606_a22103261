package refactor.pieces;

import refactor.Square;
import refactor.Team;
import refactor.movements.LShapeMovement;
import java.util.List;

public class MagicLittleHorsePiece extends BasePiece{
    public static final int ID = 3;

    public MagicLittleHorsePiece(String nickname, Team team) {
        super(nickname, team);
        this.id = ID;
        this.typeName = "Ponei Mágico";
        this.value = 5;
        this.movementLimit = 2;
    }

    @Override
    public boolean validMoveRules(List<List<Square>> board, int destinyX, int destinyY) {
        int currentX = this.square.getX(),
            currentY = this.square.getY();
        int deltaX = Math.abs(currentX - destinyX),
            deltaY = Math.abs(currentY - destinyY);

        if (deltaX != this.movementLimit && deltaY != this.movementLimit){
            return false;
        }

        return !new LShapeMovement().isOverlapping(board, currentX, currentY, destinyX, destinyY);
    }
}
