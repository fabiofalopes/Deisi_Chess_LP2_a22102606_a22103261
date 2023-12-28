package pt.ulusofona.lp2.deisichess;

import java.util.ArrayList;

public class PieceVillagePriest extends Piece{
    public static final int PIECE_TYPE_ID = 3;
    public static final String PIECE_FILE_IMAGE = "village-priest-#.png";

    PieceVillagePriest(int id, String nickname, int teamId) {
        super(id, PIECE_TYPE_ID, nickname, teamId, PIECE_FILE_IMAGE);
        this.value = 3;
        this.movementLimit = 3;
        this.typeName = "Padre da Vila";
    }

    @Override
    public boolean validMoveDeltas(int destinyX, int destinyY) {
        int deltaX = Math.abs(this.positionX - destinyX),
            deltaY = Math.abs(this.positionY - destinyY);

        return deltaX == deltaY && deltaX <= this.movementLimit && deltaY <= this.movementLimit;
    }

    @Override
    public boolean validMoveRules(ArrayList<Piece> pieces, int destinyX, int destinyY, int countValidRounds) {
        if(!this.validMoveDeltas(destinyX, destinyY)){
            return false;
        }

        return !new MovementDiagonal().isOverlapping(pieces, this.positionX, this.positionY, destinyX, destinyY);
    }

    @Override
    public ArrayList<Hint> getHints(Game game){
        ArrayList<Hint> results = new ArrayList<>();
        ArrayList<Square> squares = game.getSquares();

        for (int row = this.positionY - this.movementLimit; row <= this.positionY + this.movementLimit; row++) {
            if(row >= 0){
                for (int column = this.positionX - this.movementLimit; column <= this.positionX + this.movementLimit; column++) {
                    if(column >= 0){
                        if(!(row == this.positionY && column == this.positionX)){ // ignore same position
                            if(Movement.isWithinBounds(squares, column, row) && this.validMoveDeltas(column, row)) {
                                Piece piece = game.getPieceByPosition(column, row);
                                if(piece == null){
                                    results.add(new Hint(column, row, 0));
                                }
                                else if (piece != null && piece.getTeamId() != this.getTeamId()){
                                    results.add(new Hint(column, row, piece.getValue()));
                                }
                            }
                        }
                    }
                }
            }
        }

        return results;
    }
}
