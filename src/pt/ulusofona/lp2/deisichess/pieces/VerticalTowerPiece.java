package pt.ulusofona.lp2.deisichess.pieces;

import pt.ulusofona.lp2.deisichess.Square;
import pt.ulusofona.lp2.deisichess.Team;
import pt.ulusofona.lp2.deisichess.movements.VerticalMovement;
import java.util.List;

public class VerticalTowerPiece extends BasePiece{
    public static final int PIECE_TYPE_ID = 5;
    public static final String PIECE_FILE_IMAGE = "vertical-tower-#.png";

    public VerticalTowerPiece(int id, String nickname, Team team) {
        super(id, nickname, team, PIECE_FILE_IMAGE);
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