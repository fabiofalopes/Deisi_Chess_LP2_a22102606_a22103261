package pt.ulusofona.lp2.deisichess.pieces;

import pt.ulusofona.lp2.deisichess.Square;
import pt.ulusofona.lp2.deisichess.Team;
import java.util.List;

public class JokerPiece extends BasePiece {
    public static final int PIECE_TYPE_ID = 7;
    public static final String PIECE_FILE_IMAGE = "joker-#.png";
    private int cloneCounter;
    private BasePiece clone;

    public JokerPiece(int id, String nickname, Team team) {
        super(id, nickname, team, PIECE_FILE_IMAGE);
        this.jokerType = true;
        this.typeName = "Joker";
        this.value = 4;
        this.initJokerClone();
    }

    public void initJokerClone(){
        this.cloneCounter = QueenPiece.PIECE_TYPE_ID;
        this.clone = new QueenPiece(-1,"", team);
    }

    public void cloneNext() {
        this.cloneCounter++;

        switch (cloneCounter){
            case MagicLittleHorsePiece.PIECE_TYPE_ID -> this.clone = new MagicLittleHorsePiece(-1, "", this.team);
            case VillagePriestPiece.PIECE_TYPE_ID -> this.clone = new VillagePriestPiece(-1,"", this.team);
            case HorizontalTowerPiece.PIECE_TYPE_ID -> this.clone = new HorizontalTowerPiece(-1,"", this.team);
            case VerticalTowerPiece.PIECE_TYPE_ID -> this.clone = new VerticalTowerPiece(-1,"", this.team);
            case HomerSimpsonPiece.PIECE_TYPE_ID -> {
                HomerSimpsonPiece homerSimpson = this.team.getHomer();
                this.clone = (BasePiece) homerSimpson.clone();
            }
            default -> this.initJokerClone();
        }
    }

    @Override
    public boolean validMoveRules(List<List<Square>> board, int destinyX, int destinyY) {
        return this.clone.validMoveRules(board, destinyX, destinyY);
    }
    @Override
    public String printInfo(){
        return this.id + " | " +
               this.typeName + "/" +
               this.clone.getTypeName() + " | " +
               this.value + " | " +
               this.team.getId() + " | " +
               this.nickname + " @ (" +
              (this.square == null ? "n/a" : this.square.getX() + ", " + this.square.getY()) + ")";
    }
}
