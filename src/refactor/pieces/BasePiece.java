package refactor.pieces;

import refactor.Square;
import refactor.Team;
import java.util.List;

public abstract class BasePiece {
    protected int id;
    protected String typeName;
    protected int value;
    protected String nickname;
    protected boolean defeated;
    protected String image = null;
    protected boolean canSleep = false;
    protected Square square;
    protected Team team;
    protected Integer movementLimit = null;
    protected boolean queenType = false;

    public BasePiece(String nickname, Team team){
        this.nickname = nickname;
        this.team = team;
        this.defeated = true;
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
    protected String getImage(){
        return this.image;
    }
    public boolean getIsDefeated(){
        return this.defeated;
    }
    public boolean getIsQueen() { return this.queenType; }

    protected void setImage(String image){
        this.image = image;
    }
    public void setSquare(Square square){ this.square = square; }

    public static BasePiece create(int id, String nickname, Team team){
        return switch (id){
            case KingPiece.ID -> new KingPiece(nickname, team);
            case QueenPiece.ID -> new QueenPiece(nickname, team);
            case MagicLittleHorsePiece.ID -> new MagicLittleHorsePiece(nickname, team);
            case VillagePriestPiece.ID -> new VillagePriestPiece(nickname, team);
            case HorizontalTowerPiece.ID -> new HorizontalTowerPiece(nickname, team);
            case VerticalTowerPiece.ID -> new VerticalTowerPiece(nickname, team);
            case HomerSimpsonPiece.ID -> new HomerSimpsonPiece(nickname, team);
            case JokerPiece.ID -> new JokerPiece(nickname, team);
            default -> null;
        };
    }
    protected void revive(){
        this.defeated = false;
    }
    protected boolean hasSquare() { return this.square != null; }
    public void removeSquare() { this.square = null; }
    public void defeatMe() { this.defeated = true; }
    public abstract boolean validMoveRules(List<List<Square>> board, int destinyX, int destinyY);
    public String printInfo(){
        return this.id + " | " +
                this.typeName + " | " +
                this.value + " | " +
                this.team.getId() + " | " +
                this.nickname + " @ (" +
                this.square.getX() + ", " +
                this.square.getY() + ")";
    }
}
