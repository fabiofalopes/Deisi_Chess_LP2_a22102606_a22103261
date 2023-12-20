package pt.ulusofona.lp2.deisichess;

import java.util.ArrayList;

public class PieceKing extends Piece {
    public static final int PIECE_TYPE_ID = 0;
    public static final String PIECE_FILE_IMAGE = "king-#.png";

    PieceKing(int id, String nickname, int teamId) {
        super(id, PIECE_TYPE_ID, nickname, teamId, PIECE_FILE_IMAGE);
        this.value = 1000;
        this.movementLimit = 1;
        this.typeName = "Rei";
    }

    @Override
    public boolean isKing(){ return true; }

    @Override
    public boolean validMoveRules(ArrayList<Piece> pieces, int destinyX, int destinyY, int countValidRounds) {
        boolean validMove = Math.abs(this.positionX - destinyX) <= this.movementLimit &&
                            Math.abs(this.positionY - destinyY) <= this.movementLimit;

        if(!validMove){
            return false;
        }

        return !new MovementVertical().isOverlapping(pieces, this.positionX, this.positionY, destinyX, destinyY) ||
               !new MovementHorizontal().isOverlapping(pieces,this.positionX, this.positionY, destinyX, destinyY) ||
               !new MovementDiagonal().isOverlapping(pieces,this.positionX, this.positionY, destinyX, destinyY);
    }

    @Override
    public String printInfo(int countValidRounds) {
        StringBuilder result = new StringBuilder();
        result.append(this.id + " | ");
        result.append(this.typeName + " | ");
        result.append("(infinito) | ");
        result.append(this.teamId + " | ");
        result.append(this.nickname + " @ (");
        result.append((this.isDead() ? "n/a" : (this.positionX + ", " + this.positionY)) + ")");
        return result.toString();
    }
}
