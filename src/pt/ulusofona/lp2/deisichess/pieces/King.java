package pt.ulusofona.lp2.deisichess.pieces;

import pt.ulusofona.lp2.deisichess.Board;
import pt.ulusofona.lp2.deisichess.ChessPiece;
import pt.ulusofona.lp2.deisichess.GameStaticData;
import pt.ulusofona.lp2.deisichess.Square;
import java.util.ArrayList;

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
        return false;
    }
}
