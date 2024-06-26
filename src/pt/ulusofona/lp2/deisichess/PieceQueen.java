package pt.ulusofona.lp2.deisichess;

import java.util.ArrayList;

public class PieceQueen extends Piece {
    public static final int PIECE_TYPE_ID = 1;
    public static final String PIECE_FILE_IMAGE = "queen-#.png";

    PieceQueen(int id, String nickname, int teamId) {
        super(id, PIECE_TYPE_ID, nickname, teamId, PIECE_FILE_IMAGE);
        this.value = 8;
        this.movementLimit = 5;
        this.typeName = "Rainha";
    }

    @Override
    boolean isQueen() {
        return true;
    }

    @Override
    public boolean validMoveDeltas(int destinyX, int destinyY) {
        int deltaX = Math.abs(this.positionX - destinyX),
            deltaY = Math.abs(this.positionY - destinyY);

        return this.movementLimit >= deltaX && this.movementLimit >= deltaY;
    }

    @Override
    public boolean validMoveRules(ArrayList<Piece> pieces, int destinyX, int destinyY, int countValidRounds) {
        if(!this.validMoveDeltas(destinyX, destinyY)){
            return false;
        }

        for (Piece piece : pieces) {
            if(piece.isQueen() && piece.getTeamId() != this.teamId && piece.isOnPosition(destinyX, destinyY)){
                return false;
            }
        }

        return !new MovementVertical().isOverlapping(pieces, this.positionX, this.positionY, destinyX, destinyY) ||
               !new MovementHorizontal().isOverlapping(pieces,this.positionX, this.positionY, destinyX, destinyY) ||
               !new MovementDiagonal().isOverlapping(pieces,this.positionX, this.positionY, destinyX, destinyY);
    }
}
