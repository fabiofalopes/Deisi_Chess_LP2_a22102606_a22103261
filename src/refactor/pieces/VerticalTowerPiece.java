package refactor.pieces;

import refactor.Square;
import refactor.Team;
import refactor.movements.VerticalMovement;
import java.util.List;

public class VerticalTowerPiece extends BasePiece{
    public static final int ID = 6;

    public VerticalTowerPiece(String nickname, Team team) {
        super(nickname, team);
        this.id = ID;
        this.typeName = "TorreVert";
        this.value = 3;
        //this.movementLimit = 3;
    }

    @Override
    public boolean validMoveRules(List<List<Square>> board, int destinyX, int destinyY) {
        int currentX = this.square.getX(),
            currentY = this.square.getY();

        /* means the piece didn't move vertically,
           or moved in X which isn't allowed */
        if(currentY == destinyY || currentX != destinyX){
            return false;
        }

        /* uncomment if this piece has movement limit
        if(this.movementLimit < Math.abs(currentY - y)){
            return false;
        }*/

        return !new VerticalMovement().isOverlapping(board, currentX, currentY, destinyX, destinyY);
    }
}
