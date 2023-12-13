package pt.ulusofona.lp2.deisichess.pieces;

import pt.ulusofona.lp2.deisichess.Square;
import pt.ulusofona.lp2.deisichess.Team;
import pt.ulusofona.lp2.deisichess.movements.DiagonalMovement;
import java.util.List;

public class VillagePriestPiece extends BasePiece{
    public static final int PIECE_TYPE_ID = 3;
    public static final String PIECE_FILE_IMAGE = "village-priest-#.png";

    public VillagePriestPiece(int id, String nickname, Team team) {
        super(id, nickname, team, PIECE_FILE_IMAGE);
        this.typeName = "Padre da Vila";
        this.value = 3;
        this.movementLimit = 3;
    }

    @Override
    public boolean validMoveRules(List<List<Square>> board, int destinyX, int destinyY) {
        int currentX = this.square.getX(),
            currentY = this.square.getY();
        int deltaX = Math.abs(currentX - destinyX),
            deltaY = Math.abs(currentY - destinyY);

        // means the piece didn't move diagonally
        if(deltaX != deltaY &&
           deltaX < this.movementLimit &&
           deltaY < this.movementLimit ) {
            return false;
        }

        return !new DiagonalMovement().isOverlapping(board,currentX, currentY, destinyX, destinyY);
    }
}
