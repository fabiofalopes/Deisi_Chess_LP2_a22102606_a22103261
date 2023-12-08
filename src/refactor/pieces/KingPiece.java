package refactor.pieces;

import refactor.Square;
import refactor.Team;
import refactor.movements.DiagonalMovement;
import refactor.movements.HorizontalMovement;
import refactor.movements.VerticalMovement;

import java.util.List;

public class KingPiece extends BasePiece{
    public static final int ID = 1;
    public KingPiece(String nickname, Team team) {
        super(nickname, team);
        this.id = ID;
        this.typeName = "Rei";
        this.value = 1000;
        this.movementLimit = 1;
    }

    @Override
    public boolean validMoveRules(List<List<Square>> board, int x, int y) {
        int currentX = this.square.getX();
        int currentY = this.square.getY();

        boolean validMove = (Math.abs(currentX - x) <= this.movementLimit && Math.abs(currentY - y) <= this.movementLimit);

        if(!validMove){
            return false;
        }

        return !new VerticalMovement().isOverlapping(board, currentX, currentY, x, y) ||
               !new HorizontalMovement().isOverlapping(board,currentX, currentY, x, y) ||
               !new DiagonalMovement().isOverlapping(board,currentX, currentY, x, y);
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
