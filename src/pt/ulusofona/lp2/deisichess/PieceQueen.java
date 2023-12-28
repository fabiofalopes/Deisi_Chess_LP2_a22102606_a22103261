package pt.ulusofona.lp2.deisichess;

import java.util.ArrayList;

public class PieceQueen extends Piece {
    public static final int PIECE_TYPE_ID = 1;
    public static final String PIECE_FILE_IMAGE = "queen-#.png";

    PieceQueen(int id, String nickname, int teamId) {
        super(id, PIECE_TYPE_ID, nickname, teamId, PIECE_FILE_IMAGE);
        this.value = 8;
        this.movementLimit = 5;
        this.typeName = "Rainha";
    }

    @Override
    boolean isQueen() {
        return true;
    }

    @Override
    public boolean validMoveDeltas(int destinyX, int destinyY) {
        int deltaX = Math.abs(this.positionX - destinyX),
            deltaY = Math.abs(this.positionY - destinyY);

        return this.movementLimit >= deltaX && this.movementLimit >= deltaY;
    }

    @Override
    public ArrayList<Hint> getHints(Game game){
        ArrayList<Hint> results = new ArrayList<>();
        ArrayList<Square> squares = game.getSquares();

        for (int row = this.positionY - this.movementLimit; row <= this.positionY + this.movementLimit; row++) {
            if (row >= 0) {
                // vertical
                if(Movement.isWithinBounds(squares, this.positionX, row)) {
                    Piece piece = game.getPieceByPosition(this.positionX, row);
                    if(piece == null){
                        results.add(new Hint(this.positionX, row, 0));
                    }
                    else if (piece != null && piece.getTeamId() != this.getTeamId()){
                        results.add(new Hint(this.positionX, row, piece.getValue()));
                    }
                }
                // diagonal
                int leftDiagonalX = this.positionX - row,
                    rightDiagonalX = this.positionX + row;

                if(Movement.isWithinBounds(squares, leftDiagonalX, row)) {
                    Piece piece = game.getPieceByPosition(leftDiagonalX, row);
                    if(piece == null){
                        results.add(new Hint(leftDiagonalX, row, 0));
                    }
                    else if (piece != null && piece.getTeamId() != this.getTeamId()){
                        results.add(new Hint(leftDiagonalX, row, piece.getValue()));
                    }
                }

                if(Movement.isWithinBounds(squares, rightDiagonalX, row)) {
                    Piece piece = game.getPieceByPosition(rightDiagonalX, row);
                    if(piece == null){
                        results.add(new Hint(rightDiagonalX, row, 0));
                    }
                    else if (piece != null && piece.getTeamId() != this.getTeamId()){
                        results.add(new Hint(rightDiagonalX, row, piece.getValue()));
                    }
                }
            }
        }

        return results;
    }

    @Override
    public boolean validMoveRules(ArrayList<Piece> pieces, int destinyX, int destinyY, int countValidRounds) {
        if(!this.validMoveDeltas(destinyX, destinyY)){
            return false;
        }

        for (Piece piece : pieces) {
            if(piece.isQueen() && piece.getTeamId() != this.teamId && piece.isOnPosition(destinyX, destinyY)){
                return false;
            }
        }

        return !new MovementVertical().isOverlapping(pieces, this.positionX, this.positionY, destinyX, destinyY) ||
               !new MovementHorizontal().isOverlapping(pieces,this.positionX, this.positionY, destinyX, destinyY) ||
               !new MovementDiagonal().isOverlapping(pieces,this.positionX, this.positionY, destinyX, destinyY);
    }
}
