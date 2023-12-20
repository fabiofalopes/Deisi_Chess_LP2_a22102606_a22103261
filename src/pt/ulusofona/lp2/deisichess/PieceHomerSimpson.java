package pt.ulusofona.lp2.deisichess;

import java.util.ArrayList;

public class PieceHomerSimpson extends Piece {
    public static final int PIECE_TYPE_ID = 6;
    public static final String PIECE_FILE_IMAGE = "homer-#.png";

    PieceHomerSimpson(int id, String nickname, int teamId) {
        super(id, PIECE_TYPE_ID, nickname, teamId, PIECE_FILE_IMAGE);
        this.value = 2;
        this.movementLimit = 1;
        this.typeName = "Homer Simpson";
    }

    @Override
    public boolean isHomerSimpson(){
        return true;
    }

    @Override
    public String printInfo(int countValidRounds){
        return this.isSleeping(countValidRounds) ? "Doh! zzzzzz" : super.printInfo(countValidRounds);
    }

    @Override
    public boolean validMoveRules(ArrayList<Piece> pieces, int destinyX, int destinyY, int countValidRounds) {
        if(this.isSleeping(countValidRounds)){
            return false;
        }

        boolean validMove = Math.abs(this.positionX - destinyX) == this.movementLimit &&
                            Math.abs(this.positionY - destinyY) == this.movementLimit;

        if(!validMove){
            return false;
        }

        return !new MovementDiagonal().isOverlapping(pieces, this.positionX, this.positionY, destinyX, destinyY);
    }

    boolean isSleeping(int countValidRounds) {
        return countValidRounds % 3 == 0;
    }
}