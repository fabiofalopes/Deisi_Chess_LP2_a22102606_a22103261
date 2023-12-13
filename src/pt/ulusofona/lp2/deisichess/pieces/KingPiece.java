package pt.ulusofona.lp2.deisichess.pieces;

import pt.ulusofona.lp2.deisichess.Square;
import pt.ulusofona.lp2.deisichess.Team;
import pt.ulusofona.lp2.deisichess.movements.DiagonalMovement;
import pt.ulusofona.lp2.deisichess.movements.HorizontalMovement;
import pt.ulusofona.lp2.deisichess.movements.VerticalMovement;
import java.util.List;

public class KingPiece extends BasePiece{
    public static final int PIECE_TYPE_ID = 0;
    public static final String PIECE_FILE_IMAGE = "king-#.png";

    public KingPiece(int id, String nickname, Team team) {
        super(id, nickname, team, PIECE_FILE_IMAGE);
        this.typeName = "Rei";
        this.value = 1000;
        this.movementLimit = 1;
    }

    @Override
    public boolean validMoveRules(List<List<Square>> board, int destinyX, int destinyY) {
        int currentX = this.square.getX(),
            currentY = this.square.getY();

        boolean validMove = Math.abs(currentX - destinyX) <= this.movementLimit &&
                            Math.abs(currentY - destinyY) <= this.movementLimit;

        if(!validMove){
            return false;
        }

        return !new VerticalMovement().isOverlapping(board, currentX, currentY, destinyX, destinyY) ||
               !new HorizontalMovement().isOverlapping(board,currentX, currentY, destinyX, destinyY) ||
               !new DiagonalMovement().isOverlapping(board,currentX, currentY, destinyX, destinyY);
    }
}
