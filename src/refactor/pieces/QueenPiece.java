package refactor.pieces;

import refactor.Square;
import refactor.Team;
import java.util.List;

public class QueenPiece extends BasePiece{
    public static final int ID = 2;
    public QueenPiece(String nickname, Team team) {
        super(nickname, team);
        this.id = ID;
        this.typeName = "Rainha";
        this.value = 8;
    }

    @Override
    public boolean validMoveRules(List<List<Square>> board, int x, int y) {
        /*int deltaX = Math.abs(destinyX - currentX);
int deltaY = Math.abs(destinyY - currentY);

if(deltaX != deltaY) { // means the piece didn't move diagonally
    return false;
}*/

        return false;
    }
}
