package pt.ulusofona.lp2.deisichess.pieces;

import pt.ulusofona.lp2.deisichess.Board;
import pt.ulusofona.lp2.deisichess.ChessPiece;
import pt.ulusofona.lp2.deisichess.GameStaticData;

public class HorizontalTower extends ChessPiece {

    public HorizontalTower(int teamID){ super(teamID); }

    public HorizontalTower(String nickame, int teamID) {
        super(nickame, teamID);
        this.id = GameStaticData.HORIZONTAL_TOWER_PIECE_ID;
        this.typeName = GameStaticData.HORIZONTAL_TOWER_NAME;
        this.value = GameStaticData.HORIZONTAL_TOWER_VALUE;
    }

    @Override
    public boolean tryMove(Board board, int x, int y) {
        return false;
    }
}
