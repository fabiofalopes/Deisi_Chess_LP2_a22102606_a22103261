package pt.ulusofona.lp2.deisichess;

import java.util.ArrayList;

public class PieceKing extends Piece {
    public static final int PIECE_TYPE_ID = 0;
    public static final String PIECE_FILE_IMAGE = "king-#.png";

    PieceKing(int id, String nickname, int teamId) {
        super(id, PIECE_TYPE_ID, nickname, teamId, PIECE_FILE_IMAGE);
        this.value = 1000;
        this.movementLimit = 1;
        this.typeName = "Rei";
    }

    @Override
    public boolean isKing(){ return true; }

    @Override
    public boolean validMoveDeltas(int destinyX, int destinyY) {
        return Math.abs(this.positionX - destinyX) <= this.movementLimit &&
               Math.abs(this.positionY - destinyY) <= this.movementLimit;
    }

    @Override
    public boolean validMoveRules(ArrayList<Piece> pieces, int destinyX, int destinyY, int countValidRounds) {
        if(!this.validMoveDeltas(destinyX, destinyY)){
            return false;
        }

        return !new MovementVertical().isOverlapping(pieces, this.positionX, this.positionY, destinyX, destinyY) ||
               !new MovementHorizontal().isOverlapping(pieces,this.positionX, this.positionY, destinyX, destinyY) ||
               !new MovementDiagonal().isOverlapping(pieces,this.positionX, this.positionY, destinyX, destinyY);
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
                            if(Movement.isWithinBounds(squares, column, row)) {
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

    @Override
    public String printInfo(int countValidRounds) {
        StringBuilder result = new StringBuilder();
        result.append(this.id + " | ");
        result.append(this.typeName + " | ");
        result.append("(infinito) | ");
        result.append(this.teamId + " | ");
        result.append(this.nickname + " @ (");
        result.append((this.isDead() ? "n/a" : (this.positionX + ", " + this.positionY)) + ")");
        return result.toString();
    }
}
