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

    @Override
    void setPosition(int x, int y){
        this.positionX = x;
        this.positionY = y;
        this.impersonate.setPosition(this.positionX, this.positionY);
    }

    @Override
    String getTypeName(){
        return this.typeName + "/" + this.impersonate.getTypeName();
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

    public boolean impersonateIsQueen(){
        return this.impersonate.isQueen();
    }

    @Override
    public boolean validMoveRules(ArrayList<Piece> pieces, int destinyX, int destinyY, int countValidRounds) {
        return this.impersonate.validMoveRules(pieces, destinyX, destinyY, countValidRounds);
    }
}
