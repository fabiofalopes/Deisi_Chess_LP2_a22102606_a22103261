package pt.ulusofona.lp2.deisichess;

import java.util.ArrayList;

public class PieceVerticalTower extends Piece{
    public static final int PIECE_TYPE_ID = 5;
    public static final String PIECE_FILE_IMAGE = "vertical-tower-#.png";

    PieceVerticalTower(int id, String nickname, int teamId) {
        super(id, PIECE_TYPE_ID, nickname, teamId, PIECE_FILE_IMAGE);
        this.value = 3;
        //this.movementLimit = ;
        this.typeName = "TorreVert";
    }

    @Override
    public boolean validMoveRules(ArrayList<Piece> pieces, int destinyX, int destinyY, int countValidRounds) {
        // means the piece didn't move vertically,
        // or moved in X which isn't allowed
        if(this.positionY == destinyY || this.positionX != destinyX){
            return false;
        }

        /* uncomment if this piece has movement limit
        if(this.movementLimit < Math.abs(currentY - y)){
            return false;
        }*/

        return !new MovementVertical().isOverlapping(pieces, this.positionX, this.positionY, destinyX, destinyY);
    }

    @Override
    public ArrayList<Hint> getHints(Game game){
        ArrayList<Hint> results = new ArrayList<>();
        ArrayList<Square> squares = game.getSquares();

        for (int row = 0; row < game.getBoardSize(); row++) {
            if(!(row == this.positionY)){ // ignore same position
                if(Movement.isWithinBounds(squares, this.positionX, row)) {

                    boolean isOverlapping = new MovementVertical().isOverlapping(game.getPieces(), this.positionX, this.positionY, this.positionX, row);
                    if(!isOverlapping){
                        Piece piece = game.getPieceByPosition(this.positionX, row);
                        if(piece == null){
                            results.add(new Hint(this.positionX, row, 0));
                        }
                        else if (piece != null && piece.getTeamId() != this.getTeamId()){
                            results.add(new Hint(this.positionX, row, piece.getValue()));
                        }
                    }
                }
            }
        }

        return results;
    }
}
