package pt.ulusofona.lp2.deisichess;

import java.util.ArrayList;

public class PieceHorizontalTower extends Piece{
    public static final int PIECE_TYPE_ID = 4;
    public static final String PIECE_FILE_IMAGE = "horizontal-tower-#.png";

    PieceHorizontalTower(int id, String nickname, int teamId) {
        super(id, nickname, teamId, PIECE_FILE_IMAGE);
        this.value = 3;
        //this.movementLimit = ;
        this.typeName = "TorreHor";
    }

    @Override
    public boolean validMoveRules(ArrayList<Piece> pieces, int destinyX, int destinyY, int countValidRounds) {
        // means the piece didn't move horizontally,
        // or moved in Y which isn't allowed
        if(this.positionX == destinyX || this.positionY != destinyY){
            return false;
        }

        /* uncomment if this piece has movement limit
        if(this.movementLimit < Math.abs(currentX - x)){
            return false;
        }*/

        return !new MovementHorizontal().isOverlapping(pieces, this.positionX, this.positionY, destinyX, destinyY);
    }
}
