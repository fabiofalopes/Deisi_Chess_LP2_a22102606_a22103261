package refactor.pieces;

import refactor.Square;
import refactor.Team;
import refactor.movements.BaseMovement;
import refactor.movements.HorizontalMovement;
import refactor.movements.VerticalMovement;

import java.util.List;

public class HorizontalTowerPiece extends BasePiece{
    public static final int ID = 5;
    public HorizontalTowerPiece(String nickname, Team team) {
        super(nickname, team);
        this.id = ID;
        this.typeName = "TorreHor";
        this.value = 3;
        //this.movementLimit = 3;
    }

    @Override
    public boolean validMoveRules(List<List<Square>> board, int x, int y) {
        int currentX = this.square.getX();
        int currentY = this.square.getY();

        if(currentX == x || currentY != y){ // means the piece didn't move horizontally
            return false;                   // or moved in Y which isn't allowed
        }

        // uncomment if this piece has movement limit
        /*if(this.movementLimit != null && this.movementLimit < Math.abs(currentY - y)){
            return false;
        }*/

        return !new HorizontalMovement().isOverlapping(board, currentX, currentY, x, y);
    }
}
