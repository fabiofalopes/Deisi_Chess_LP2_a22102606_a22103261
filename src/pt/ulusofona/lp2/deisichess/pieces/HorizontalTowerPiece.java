package pt.ulusofona.lp2.deisichess.pieces;

import pt.ulusofona.lp2.deisichess.Square;
import pt.ulusofona.lp2.deisichess.movements.HorizontalMovement;
import pt.ulusofona.lp2.deisichess.Team;
import java.util.List;

public class HorizontalTowerPiece extends BasePiece{
    public static final int PIECE_TYPE_ID = 4;
    public static final String PIECE_FILE_IMAGE = "horizontal-tower-#.png";

    public HorizontalTowerPiece(int id, String nickname, Team team) {
        super(id, nickname, team, PIECE_FILE_IMAGE);
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
