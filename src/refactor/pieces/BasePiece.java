package refactor.pieces;

import refactor.Square;
import refactor.Team;
import refactor.movements.BaseMovement;

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

    public BasePiece(String nickname, Team team){
        this.nickname = nickname;
        this.team = team;
        this.defeated = true;
    }

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

    protected int getTeamId(){
        return this.team.getId();
    }

    public int getId(){
        return this.id;
    }

    public int getValue(){
        return this.value;
    }

    protected String getNickname(){
        return this.nickname;
    }

    protected String getImage(){
        return this.image;
    }

    protected void setImage(String image){
        this.image = image;
    }

    protected void setSquare(Square square){
        this.square = square;
        this.square.setPiece(this);
    }

    public boolean isDefeated(){
        return this.defeated;
    }

    protected void revive(){
        this.defeated = false;
    }

    protected  void defeatMe(){
        this.defeated = true;
        this.square.removePiece();
        this.removeSquare();
    }

    protected boolean hasSquare(){
        return this.square != null;
    }

    protected void removeSquare(){
        this.square = null;
    }

    private boolean huntOrMove(Square destinySquare) {
        if(destinySquare.hasPiece()){
            if(destinySquare.getPiece().getTeamId() == this.getTeamId()){ // same team constraint
                return false;
            }

            BasePiece piece = destinySquare.getPiece();
            piece.defeatMe();
            this.team.incrementKillsAndScore(piece.getValue());
        }

        this.setSquare(destinySquare);
        destinySquare.setPiece(this);

        return true;
    }

    protected boolean move(List<List<Square>> board, int x, int y){
        boolean valid = BaseMovement.isWithinBounds(board, x ,y) &&
                        this.validMoveRules(board, x, y) &&
                        this.huntOrMove(board.get(y).get(x));

        if(valid){
            this.team.incrementValidMove();
        }

        return valid;
    }

    protected abstract boolean validMoveRules(List<List<Square>> board, int x, int y);

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
