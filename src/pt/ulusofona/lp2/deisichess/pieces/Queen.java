package pt.ulusofona.lp2.deisichess.pieces;

import pt.ulusofona.lp2.deisichess.Board;
import pt.ulusofona.lp2.deisichess.ChessPiece;
import pt.ulusofona.lp2.deisichess.GameStaticData;
import pt.ulusofona.lp2.deisichess.Square;
import java.util.ArrayList;

public class Queen extends ChessPiece {

    public Queen(int teamID){ super(teamID); }

    public Queen(String nickame, int teamID) {
        super(nickame, teamID);
        this.id = GameStaticData.QUEEN_PIECE_ID;
        this.typeName = GameStaticData.QUEEN_NAME;
        this.value = GameStaticData.QUEEN_PIECE_VALUE;
    }

    @Override
    public boolean tryMove(Board board, int x, int y) {
        return false;
    }
}
