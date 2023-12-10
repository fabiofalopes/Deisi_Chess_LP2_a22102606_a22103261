package refactor.pieces;

import refactor.Square;
import refactor.Team;
import refactor.movements.LShapeMovement;
import java.util.List;

public class MagicLittleHorsePiece extends BasePiece{
    public static final int PIECE_TYPE_ID = 3;
    public static final String PIECE_FILE_IMAGE = "magic-little-horse-#.png";

    public MagicLittleHorsePiece(int id, String nickname, Team team) {
        super(id, nickname, team, this.PIECE_FILE_IMAGE);
        this.typeName = "Ponei Mágico";
        this.value = 5;
        this.movementLimit = 2;
    }

    @Override
    public boolean validMoveRules(List<List<Square>> board, int destinyX, int destinyY) {
        int currentX = this.square.getX(),
            currentY = this.square.getY();
        int deltaX = Math.abs(currentX - destinyX),
            deltaY = Math.abs(currentY - destinyY);

        if (deltaX != this.movementLimit && deltaY != this.movementLimit){
            return false;
        }

        return !new LShapeMovement().isOverlapping(board, currentX, currentY, destinyX, destinyY);
    }
}
