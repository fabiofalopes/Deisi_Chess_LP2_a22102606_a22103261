package pt.ulusofona.lp2.deisichess;

import java.util.ArrayList;

public class PieceMagicLittleHorse extends Piece{
    public static final int PIECE_TYPE_ID = 2;
    public static final String PIECE_FILE_IMAGE = "magic-little-horse-#.png";

    PieceMagicLittleHorse(int id, String nickname, int teamId) {
        super(id, PIECE_TYPE_ID, nickname, teamId, PIECE_FILE_IMAGE);
        this.value = 5;
        this.movementLimit = 2;
        this.typeName = "Ponei Mágico";
    }

    @Override
    public boolean validMoveDeltas(int destinyX, int destinyY) {
        int deltaX = Math.abs(this.positionX - destinyX),
            deltaY = Math.abs(this.positionY - destinyY);

        return deltaX == this.movementLimit && deltaY == this.movementLimit;
    }

    @Override
    public boolean validMoveRules(ArrayList<Piece> pieces, int destinyX, int destinyY, int countValidRounds) {
        if (!this.validMoveDeltas(destinyX, destinyY)){
            return false;
        }

        return !(new MovementLShape().isOverlapping(pieces, this.positionX, this.positionY, destinyX, destinyY));
    }
}
