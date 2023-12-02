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

        // invalid move: move King restriction
        //if(!(Math.abs(x0 - x1) <= 1 && Math.abs(y0 - y1) <= 1)){
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
