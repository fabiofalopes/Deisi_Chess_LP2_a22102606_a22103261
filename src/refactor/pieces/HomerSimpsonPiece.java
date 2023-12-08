package refactor.pieces;

import refactor.Square;
import refactor.Team;
import refactor.movements.DiagonalMovement;
import refactor.movements.HorizontalMovement;
import refactor.movements.VerticalMovement;

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

    public void setSleeping(int count){
        this.sleeping = count % 3 == 0;
    }

    public boolean isSleeping(){
        return this.sleeping;
    }

    @Override
    public boolean validMoveRules(List<List<Square>> board, int x, int y) {
        if(this.sleeping){
            return false;
        }

        int currentX = this.square.getX();
        int currentY = this.square.getY();

        boolean validMove = (Math.abs(currentX - x) == this.movementLimit && Math.abs(currentY - y) == this.movementLimit);

        if(!validMove){
            return false;
        }

        return !new DiagonalMovement().isOverlapping(board,currentX, currentY, x, y);
    }

    @Override
    public String printInfo(){
        return this.sleeping ? "Doh! zzzzzz" : super.printInfo();
    }
}
