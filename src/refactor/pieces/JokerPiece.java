package refactor.pieces;

import refactor.Square;
import refactor.Team;
import java.util.List;

public class JokerPiece extends BasePiece{
    private int cloneCounter;
    private BasePiece clone;
    public static final int ID = 8;

    public JokerPiece(String nickname, Team team) {
        super(nickname, team);
        this.id = ID;
        this.typeName = "Joker";
        this.value = 4;
        this.cloneCounter = QueenPiece.ID;
        this.clone = new QueenPiece("", team);
    }

    @Override
    public boolean validMoveRules(List<List<Square>> board, int x, int y) {
        return false;
    }
}
