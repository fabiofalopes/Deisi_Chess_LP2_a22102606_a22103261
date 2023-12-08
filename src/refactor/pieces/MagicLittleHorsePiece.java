package refactor.pieces;

import refactor.Square;
import refactor.Team;
import java.util.List;

public class MagicLittleHorsePiece extends BasePiece{
    public static final int ID = 3;
    public MagicLittleHorsePiece(String nickname, Team team) {
        super(nickname, team);
        this.id = ID;
        this.typeName = "Ponei Mágico";
        this.value = 5;
    }

    @Override
    public boolean validMoveRules(List<List<Square>> board, int x, int y) {
        return false;
    }
}
