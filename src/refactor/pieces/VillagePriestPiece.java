package refactor.pieces;

import refactor.Square;
import refactor.Team;
import java.util.List;

public class VillagePriestPiece extends BasePiece{
    public static final int ID = 4;
    public VillagePriestPiece(String nickname, Team team) {
        super(nickname, team);
        this.id = ID;
        this.typeName = "Padre da Vila";
        this.value = 3;
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
