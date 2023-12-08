package refactor.pieces;

import refactor.Square;
import refactor.Team;
import java.util.List;

public class KingPiece extends BasePiece{
    public static final int ID = 1;
    public KingPiece(String nickname, Team team) {
        super(nickname, team);
        this.id = ID;
        this.typeName = "Rei";
        this.value = 1000;
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

    @Override
    public String printInfo(){
        return this.id + " | " +
                this.typeName + " | " +
                "(infinito) | " +
                this.team.getId() + " | " +
                this.nickname + " @ (" +
                this.square.getX() + ", " +
                this.square.getY() + ")";
    }
}
