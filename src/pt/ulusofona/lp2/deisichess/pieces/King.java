package pt.ulusofona.lp2.deisichess.pieces;

import pt.ulusofona.lp2.deisichess.Board;
import pt.ulusofona.lp2.deisichess.ChessPiece;
import pt.ulusofona.lp2.deisichess.GameStaticData;

public class King extends ChessPiece {

    public King(int teamID){ super(teamID); }
    public King(String nickame, int teamID) {
        super(nickame, teamID);
        this.id = GameStaticData.KING_PIECE_ID;
        this.typeName = GameStaticData.KING_NAME;
        this.value = GameStaticData.KING_PIECE_VALUE;
    }

    @Override
    public boolean tryMove(Board board, int x, int y) {
        if(!board.isValidPosition(x, y)){
            return false;
        }

        return false;
    }

    @Override
    protected String printInfo(){
        return id + " | " +
                typeName + " | " +
                "(infinito) | " +
                teamID + " | " +
                nickname + " @ " + "(" +
                square.getX() + ", " + square.getY() + ")";
    }
}
