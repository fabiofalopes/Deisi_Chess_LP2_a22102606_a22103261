package pt.ulusofona.lp2.deisichess;

import java.util.ArrayList;

public abstract class Piece implements Cloneable {
    protected int id;
    protected int typeId;
    protected int teamId;
    protected int positionX;
    protected int positionY;
    protected int value;
    protected int countKills;
    protected int killsScore;
    protected int countValidMoves;
    protected int countInvalidMoves;
    protected Integer movementLimit;
    protected String typeName;
    protected String nickname;
    protected String image;

    Piece(int id, int typeId, String nickname, int teamId) {
        this.id = id;
        this.typeId = typeId;
        this.nickname = nickname;
        this.teamId = teamId;
        this.positionX = -1;
        this.positionY = -1;
    }
    Piece(int id, int typeId, String nickname, int teamId, String image) {
        this(id, typeId, nickname, teamId);

        String teamName =
                this.teamId == Team.BLACK_TEAM_ID ? Team.BLACK_TEAM_NAME : Team.WHITE_TEAM_NAME;

        this.image = image.replace("#", teamName);
    }

    int getId(){
        return this.id;
    }

    int getTypeId(){ return this.typeId; }

    int getTeamId(){
        return this.teamId;
    }

    int getPositionX(){
        return this.positionX;
    }

    int getPositionY(){
        return this.positionY;
    }

    int getValue() { return this.value; }

    int getCountKills(){ return this.countKills; }

    int getKillsScore() { return this.killsScore; }

    int getCountValidMoves(){ return this.countValidMoves; }

    int getCountInvalidMoves(){ return this.countInvalidMoves; }

    String getTypeName(){
        return this.typeName;
    }

    String getNickname(){
        return this.nickname;
    }

    String getImage(){
        return this.image;
    }

    @Override
    public Piece clone() {
        try {
            return (Piece) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

    boolean isQueen() {
        return false;
    }

    boolean isJoker() {
        return false;
    }

    boolean isHomerSimpson() {
        return false;
    }

    boolean isDead(){
        return this.positionX == -1 && this.positionY == -1;
    }

    boolean isOnPosition(int x, int y){
        return this.positionX == x && this.positionY == y;
    }

    void setPosition(int x, int y){
        this.positionX = x;
        this.positionY = y;
    }

    void killPosition() {
        this.positionX = -1;
        this.positionY = -1;
    }

    void addKillsScore(int value){
        this.killsScore += value;
    }

    void incrementCountKills(){
        this.countKills += 1;
    }

    void incrementCountValidMoves(){
        this.countValidMoves += 1;
    }

    void incrementCountInvalidMoves(){
        this.countInvalidMoves += 1;
    }

    static Piece create(int id, int typeId, int teamId, String nickname){
        return switch (typeId){
            case PieceKing.PIECE_TYPE_ID -> new PieceKing(id, nickname, teamId);
            case PieceQueen.PIECE_TYPE_ID -> new PieceQueen(id, nickname, teamId);
            case PieceMagicLittleHorse.PIECE_TYPE_ID -> new PieceMagicLittleHorse(id, nickname, teamId);
            case PieceVillagePriest.PIECE_TYPE_ID -> new PieceVillagePriest(id, nickname, teamId);
            case PieceHorizontalTower.PIECE_TYPE_ID -> new PieceHorizontalTower(id, nickname, teamId);
            case PieceVerticalTower.PIECE_TYPE_ID -> new PieceVerticalTower(id, nickname, teamId);
            case PieceHomerSimpson.PIECE_TYPE_ID -> new PieceHomerSimpson(id, nickname, teamId);
            case PieceJoker.PIECE_TYPE_ID -> new PieceJoker(id, nickname, teamId);
            default -> null;
        };
    }

    public String printInfo(int countValidRounds) {
        StringBuilder result = new StringBuilder();
        result.append(this.id + " | ");
        result.append(this.getTypeName() + " | ");
        result.append(this.value + " | ");
        result.append(this.teamId + " | ");
        result.append(this.nickname + " @ (");
        result.append((this.isDead() ? "n/a" : (this.positionX + ", " + this.positionY)) + ")");
        return result.toString();
    }

    public abstract boolean validMoveRules(ArrayList<Piece> pieces, int destinyX, int destinyY, int countValidRounds);
}
