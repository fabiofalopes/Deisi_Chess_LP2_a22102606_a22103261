package pt.ulusofona.lp2.deisichess;

import java.util.ArrayList;

public class PieceVerticalTower extends Piece{
    public static final int PIECE_TYPE_ID = 5;
    public static final String PIECE_FILE_IMAGE = "vertical-tower-#.png";

    PieceVerticalTower(int id, String nickname, int teamId) {
        super(id, nickname, teamId, PIECE_FILE_IMAGE);
        this.value = 3;
        //this.movementLimit = ;
        this.typeName = "TorreVert";
    }

    @Override
    public boolean validMoveRules(ArrayList<Piece> pieces, int destinyX, int destinyY, int countValidRounds) {
        // means the piece didn't move vertically,
        // or moved in X which isn't allowed
        if(this.positionY == destinyY || this.positionX != destinyX){
            return false;
        }

        /* uncomment if this piece has movement limit
        if(this.movementLimit < Math.abs(currentY - y)){
            return false;
        }*/

        return !new MovementVertical().isOverlapping(pieces, this.positionX, this.positionY, destinyX, destinyY);
    }
}
