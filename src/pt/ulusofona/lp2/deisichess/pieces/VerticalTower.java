package pt.ulusofona.lp2.deisichess.pieces;

import pt.ulusofona.lp2.deisichess.Board;
import pt.ulusofona.lp2.deisichess.ChessPiece;
import pt.ulusofona.lp2.deisichess.GameStaticData;

public class VerticalTower extends ChessPiece {

    public VerticalTower(int teamID){ super(teamID); }

    public VerticalTower(String nickame, int teamID) {
        super(nickame, teamID);
        this.id = GameStaticData.VERTICAL_TOWER_PIECE_ID;
        this.typeName = GameStaticData.VERTICAL_TOWER_NAME;
        this.value = GameStaticData.VERTICAL_TOWER_VALUE;
    }

    @Override
    public boolean tryMove(Board board, int x, int y) {
        return false;
    }
}
