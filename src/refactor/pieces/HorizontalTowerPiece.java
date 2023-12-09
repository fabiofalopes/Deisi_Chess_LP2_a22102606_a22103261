package refactor.pieces;

import refactor.Square;
import refactor.Team;
import refactor.movements.HorizontalMovement;
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
    public boolean validMoveRules(List<List<Square>> board, int destinyX, int destinyY) {
        int currentX = this.square.getX(),
            currentY = this.square.getY();

        /* means the piece didn't move horizontally,
           or moved in Y which isn't allowed */
        if(currentX == destinyX || currentY != destinyY){
            return false;
        }

        /* uncomment if this piece has movement limit
        if(this.movementLimit < Math.abs(currentX - x)){
            return false;
        }*/

        return !new HorizontalMovement().isOverlapping(board, currentX, currentY, destinyX, destinyY);
    }
}
