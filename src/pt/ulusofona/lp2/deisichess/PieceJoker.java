package pt.ulusofona.lp2.deisichess;

import java.util.ArrayList;

public class PieceJoker extends Piece{
    public static final int PIECE_TYPE_ID = 7;
    public static final String PIECE_FILE_IMAGE = "joker-#.png";

    private int countImpersonate;
    private Piece impersonate;

    PieceJoker(int id, String nickname, int teamId) {
        super(id, PIECE_TYPE_ID, nickname, teamId, PIECE_FILE_IMAGE);
        this.value = 4;
        this.typeName = "Joker";
        this.initImpersonate();
    }

    public void initImpersonate(){
        this.countImpersonate = PieceQueen.PIECE_TYPE_ID;
        this.impersonate = new PieceQueen(-1, "", this.teamId);
        this.impersonate.setPosition(this.positionX, this.positionY);
    }

    public void nextImpersonate(){
        this.countImpersonate++;

        switch (this.countImpersonate){
            case PieceMagicLittleHorse.PIECE_TYPE_ID ->
                this.impersonate = new PieceMagicLittleHorse(-1, "", this.teamId);
            case PieceVillagePriest.PIECE_TYPE_ID ->
                this.impersonate = new PieceVillagePriest(-1, "", this.teamId);
            case PieceHorizontalTower.PIECE_TYPE_ID ->
                this.impersonate = new PieceHorizontalTower(-1, "", this.teamId);
            case PieceVerticalTower.PIECE_TYPE_ID ->
                this.impersonate = new PieceVerticalTower(-1, "", this.teamId);
            case PieceHomerSimpson.PIECE_TYPE_ID ->
                this.impersonate = new PieceHomerSimpson(-1, "", this.teamId);
            default -> {
                this.initImpersonate();
                return;
            }
        }

        this.impersonate.setPosition(this.positionX, this.positionY);
    }

    @Override
    public boolean isJoker(){
        return true;
    }

    @Override
    public boolean validMoveRules(ArrayList<Piece> pieces, int destinyX, int destinyY, int countValidRounds) {
        return this.impersonate.validMoveRules(pieces, destinyX, destinyY, countValidRounds);
    }

    @Override
    public String printInfo(int countValidRounds) {
        StringBuilder result = new StringBuilder();
        result.append(this.id + " | ");
        result.append(this.typeName + "/");
        result.append(this.impersonate.typeName + " | ");
        result.append(this.value + " | ");
        result.append(this.teamId + " | ");
        result.append(this.nickname + " @ (");
        result.append((this.isDead() ? "n/a" : (this.positionX + ", " + this.positionY)) + ")");
        return result.toString();
    }
}
