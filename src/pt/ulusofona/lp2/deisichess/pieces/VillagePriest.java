package pt.ulusofona.lp2.deisichess.pieces;

import pt.ulusofona.lp2.deisichess.Board;
import pt.ulusofona.lp2.deisichess.ChessPiece;
import pt.ulusofona.lp2.deisichess.GameStaticData;

public class VillagePriest extends ChessPiece {

    public VillagePriest(int teamID){ super(teamID); }

    public VillagePriest(String nickame, int teamID) {
        super(nickame, teamID);
        this.id = GameStaticData.VILLAGE_PRIEST_PIECE_ID;
        this.typeName = GameStaticData.VILLAGE_PRIEST_NAME;
        this.value = GameStaticData.VILLAGE_PRIEST_PIECE_VALUE;
    }

    @Override
    public boolean tryMove(Board board, int x, int y) {
        if(!board.isValidPosition(x, y)){
            return false;
        }

        return false;
    }
}
