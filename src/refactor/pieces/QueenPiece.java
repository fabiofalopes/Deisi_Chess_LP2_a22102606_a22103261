package refactor.pieces;

import refactor.Square;
import refactor.Team;
import refactor.movements.DiagonalMovement;
import refactor.movements.HorizontalMovement;
import refactor.movements.VerticalMovement;
import java.util.List;

public class QueenPiece extends BasePiece{
    public static final int PIECE_TYPE_ID = 2;
    public static final String PIECE_FILE_IMAGE = "queen-#.png";

    public QueenPiece(int id, String nickname, Team team) {
        super(id, nickname, team, this.PIECE_FILE_IMAGE);
        this.typeName = "Rainha";
        this.queenType = true;
        this.value = 8;
        this.movementLimit = 5;
    }

    @Override
    public boolean validMoveRules(List<List<Square>> board, int destinyX, int destinyY) {
        int currentX = this.square.getX(),
            currentY = this.square.getY();
        int deltaX = Math.abs(currentX - destinyX),
            deltaY = Math.abs(currentY - destinyY);

        boolean validMove = this.movementLimit < deltaX &&
                            this.movementLimit < deltaY;

        if(!validMove || board.get(destinyY).get(destinyX).getPiece().getIsQueen()){
            return false;
        }

        return !new VerticalMovement().isOverlapping(board, currentX, currentY, destinyX, destinyY) ||
               !new HorizontalMovement().isOverlapping(board,currentX, currentY, destinyX, destinyY) ||
               !new DiagonalMovement().isOverlapping(board,currentX, currentY, destinyX, destinyY);
    }
}
