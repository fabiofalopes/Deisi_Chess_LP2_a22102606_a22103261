package refactor.pieces;

import refactor.Square;
import refactor.Team;
import refactor.movements.DiagonalMovement;
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
        this.movementLimit = 1;
    }

    public boolean getIsSleeping(){
        return this.sleeping;
    }

    public void setSleeping(int count){
        this.sleeping = count % 3 == 0;
    }

    @Override
    public boolean validMoveRules(List<List<Square>> board, int destinyX, int destinyY) {
        if(this.sleeping){
            return false;
        }

        int currentX = this.square.getX(),
            currentY = this.square.getY();

        boolean validMove = Math.abs(currentX - destinyX) == this.movementLimit &&
                            Math.abs(currentY - destinyY) == this.movementLimit;

        if(!validMove){
            return false;
        }

        return !new DiagonalMovement().isOverlapping(board, currentX, currentY, destinyX, destinyY);
    }
    @Override
    public String printInfo(){
        return this.sleeping ? "Doh! zzzzzz" : super.printInfo();
    }
}
