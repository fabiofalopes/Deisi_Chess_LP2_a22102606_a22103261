package pt.ulusofona.lp2.deisichess.pieces;

import pt.ulusofona.lp2.deisichess.Board;
import pt.ulusofona.lp2.deisichess.ChessPiece;
import pt.ulusofona.lp2.deisichess.GameStaticData;
import pt.ulusofona.lp2.deisichess.Square;
import java.util.ArrayList;

public class MagicLittleHorse extends ChessPiece {

    public MagicLittleHorse(int teamID){ super(teamID); }

    public MagicLittleHorse(String nickame, int teamID) {
        super(nickame, teamID);
        this.id = GameStaticData.MAGIC_LITTLE_HORSE_PIECE_ID;
        this.typeName = GameStaticData.MAGIC_LITTLE_HORSE_NAME;
        this.value = GameStaticData.MAGIC_LITTLE_HORSE_PIECE_VALUE;
    }

    @Override
    public boolean tryMove(Board board, int x, int y) {
        return false;
    }
}
