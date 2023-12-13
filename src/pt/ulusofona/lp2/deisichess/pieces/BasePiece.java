package pt.ulusofona.lp2.deisichess.pieces;

import pt.ulusofona.lp2.deisichess.Square;
import pt.ulusofona.lp2.deisichess.Team;
import java.util.ArrayList;
import java.util.List;

public abstract class BasePiece {
    protected int id;
    protected String typeName;
    protected int value;
    protected String nickname;
    protected boolean defeated = true;
    protected String image = null;
    protected boolean canSleep = false;
    protected Square square;
    protected Team team;
    protected Integer movementLimit = null;
    protected boolean queenType = false;
    protected boolean jokerType = false;
    protected  boolean homerSimpsonType = false;

    public BasePiece(int id, String nickname, Team team){
        this.id = id;
        this.nickname = nickname;
        this.team = team;
    }
    public BasePiece(int id, String nickname, Team team, String image){
        this(id, nickname, team);
        this.image = image.replace("#", team.getName());
    }

    public int getTeamId(){
        return this.team.getId();
    }
    public int getId(){
        return this.id;
    }
    public String getTypeName () { return this.typeName; }
    public int getValue(){
        return this.value;
    }
    public Integer getMovementLimit(){ return this.movementLimit; }
    public String getNickname(){
        return this.nickname;
    }
    public String getImage(){
        return this.image;
    }
    public boolean getIsDefeated(){
        return this.defeated;
    }
    public boolean getIsQueen() { return this.queenType; }
    public boolean getIsJoker() { return this.jokerType; }
    public boolean getIsHomerSimpson() { return this.homerSimpsonType; }
    public List<String> getHints(List<List<Square>> board){
        List<String> hints = new ArrayList<>();

        for (int row = 0; row < board.size(); row++) {
            for (int column = 0; column < board.get(0).size(); column++) {
                boolean validMove = this.validMoveRules(board, column, row);
                if(validMove){
                    hints.add(row + "," + column);
                }
            }
        }

        return hints;
    }  // [TODO] move to be comparable
    public Square getSquare() { return square; }

    public void setSquare(Square square){ this.square = square; }

    public static BasePiece create(int id, int typeId, String nickname, Team team){
        return switch (typeId){
            case KingPiece.PIECE_TYPE_ID -> new KingPiece(id, nickname, team);
            case QueenPiece.PIECE_TYPE_ID -> new QueenPiece(id, nickname, team);
            case MagicLittleHorsePiece.PIECE_TYPE_ID -> new MagicLittleHorsePiece(id, nickname, team);
            case VillagePriestPiece.PIECE_TYPE_ID -> new VillagePriestPiece(id, nickname, team);
            case HorizontalTowerPiece.PIECE_TYPE_ID -> new HorizontalTowerPiece(id, nickname, team);
            case VerticalTowerPiece.PIECE_TYPE_ID -> new VerticalTowerPiece(id, nickname, team);
            case HomerSimpsonPiece.PIECE_TYPE_ID -> new HomerSimpsonPiece(id, nickname, team);
            case JokerPiece.PIECE_TYPE_ID -> new JokerPiece(id, nickname, team);
            default -> null;
        };
    }
    public void revive(){
        this.defeated = false;
    }
    protected boolean hasSquare() { return this.square != null; }
    public void removeSquare() { this.square = null; }
    public void defeatMe() { this.defeated = true; }
    public abstract boolean validMoveRules(List<List<Square>> board, int destinyX, int destinyY);
    public String printInfo(){
        return this.id + " | " +
               this.typeName + " | " +
              (this.value == 1000 ? "(infinito)" : this.value) + " | " +
               this.team.getId() + " | " +
               this.nickname + " @ (" +
              (this.square == null ? "n/a" : this.square.getX() + ", " + this.square.getY()) + ")";
    }
}
