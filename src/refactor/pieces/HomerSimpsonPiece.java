package refactor.pieces;

import refactor.Square;
import refactor.Team;
import java.util.List;

public class HomerSimpsonPiece extends BasePiece{
    public static final int ID = 7;
    private boolean sleeping;

    public HomerSimpsonPiece(String nickname, Team team) {
        super(nickname, team);
        this.id = ID;
        this.typeName = "Homer Simpson";
        this.value = 2;
        this.canSleep = true;
    }

    public void setSleeping(int count){
        this.sleeping = count % 3 == 0;
    }

    public boolean isSleeping(){
        return this.sleeping;
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
        return this.sleeping ? "Doh! zzzzzz" : super.printInfo();
    }
}
