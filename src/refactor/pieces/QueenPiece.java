package refactor.pieces;

import refactor.Square;
import refactor.Team;
import refactor.movements.DiagonalMovement;
import refactor.movements.HorizontalMovement;
import refactor.movements.VerticalMovement;

import java.util.List;

public class QueenPiece extends BasePiece{
    public static final int ID = 2;
    public QueenPiece(String nickname, Team team) {
        super(nickname, team);
        this.id = ID;
        this.typeName = "Rainha";
        this.value = 8;
        this.movementLimit = 5;
        this.queenType = true;
    }

    @Override
    public boolean validMoveRules(List<List<Square>> board, int x, int y) {
        int currentX = this.square.getX();
        int currentY = this.square.getY();

        int deltaX = Math.abs(currentX - x);
        int deltaY = Math.abs(currentY - y);

        boolean validMove = this.movementLimit != null && this.movementLimit < deltaX && this.movementLimit < deltaY;

        if(!validMove){
            return false;
        }

        if(board.get(y).get(x).getPiece().isQueen()){
            return false;
        }

        return !new VerticalMovement().isOverlapping(board, currentX, currentY, x, y) ||
                !new HorizontalMovement().isOverlapping(board,currentX, currentY, x, y) ||
                !new DiagonalMovement().isOverlapping(board,currentX, currentY, x, y);
    }
}
