package pt.ulusofona.lp2.deisichess;

import java.util.ArrayList;

public class PieceVillagePriest extends Piece{
    public static final int PIECE_TYPE_ID = 3;
    public static final String PIECE_FILE_IMAGE = "village-priest-#.png";

    PieceVillagePriest(int id, String nickname, int teamId) {
        super(id, nickname, teamId, PIECE_FILE_IMAGE);
        this.value = 3;
        this.movementLimit = 3;
        this.typeName = "Padre da Vila";
    }

    @Override
    public boolean validMoveRules(ArrayList<Piece> pieces, int destinyX, int destinyY, int countValidRounds) {
        int deltaX = Math.abs(this.positionX - destinyX),
            deltaY = Math.abs(this.positionY - destinyY);

        // means the piece didn't move diagonally
        if(deltaX != deltaY && deltaX < this.movementLimit && deltaY < this.movementLimit ) {
            return false;
        }

        return !new MovementDiagonal().isOverlapping(pieces, this.positionX, this.positionY, destinyX, destinyY);
    }
}
