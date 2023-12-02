package pt.ulusofona.lp2.deisichess;

import pt.ulusofona.lp2.deisichess.pieces.King;
import pt.ulusofona.lp2.deisichess.pieces.Queen;
import pt.ulusofona.lp2.deisichess.pieces.MagicLittleHorse;
import pt.ulusofona.lp2.deisichess.pieces.VillagePriest;
import pt.ulusofona.lp2.deisichess.pieces.HorizontalTower;
import pt.ulusofona.lp2.deisichess.pieces.VerticalTower;
import pt.ulusofona.lp2.deisichess.pieces.HomerSimpson;
import pt.ulusofona.lp2.deisichess.pieces.Joker;

public abstract class ChessPiece {
    protected int id;
    protected String typeName;
    protected int value;
    protected String nickname;
    protected String image = null;
    protected boolean defeated;
    public Square square;
    protected boolean isSleepy = false;
    protected int teamID;
    protected ChessPiece(int teamID){
        this.teamID = teamID;
        this.defeated = false;
    }
    protected ChessPiece(String nickame, int teamID){
        this.nickname = nickame;
        this.teamID = teamID;
        this.defeated = true;
    }

    public static ChessPiece generate(int id , String nickname, int teamID){
        return switch (id) {
            case GameStaticData.KING_PIECE_ID -> new King(nickname, teamID);
            case GameStaticData.QUEEN_PIECE_ID -> new Queen(nickname, teamID);
            case GameStaticData.MAGIC_LITTLE_HORSE_PIECE_ID -> new MagicLittleHorse(nickname, teamID);
            case GameStaticData.VILLAGE_PRIEST_PIECE_ID -> new VillagePriest(nickname, teamID);
            case GameStaticData.HORIZONTAL_TOWER_PIECE_ID -> new HorizontalTower(nickname, teamID);
            case GameStaticData.VERTICAL_TOWER_PIECE_ID -> new VerticalTower(nickname, teamID);
            case GameStaticData.HOMER_SIMPSON_PIECE_ID -> new HomerSimpson(nickname, teamID);
            case GameStaticData.JOKER_PIECE_ID -> new Joker(nickname, teamID);
            default -> null;
        };
    }

    protected void updateImage(String image){
        this.image = image;
    }
    protected int getId(){
        return this.id;
    }
    protected String getNickname(){
        return this.nickname;
    }
    protected String getImage(){
        return this.image;
    }
    protected boolean isDefeated(){
        return this.defeated;
    }
    protected void revive(){
        this.defeated = false;
    }
    protected void defeatMe(){
        this.defeated = true;
        this.square = null;
    }
    protected Square getPosition(){
        return this.square;
    }
    protected boolean isSleeping(int count){ return false; }
    protected boolean isJoker() { return false; }
    protected String printInfo(){
        return id + " | " +
               typeName + " | " +
               value + " | " +
               teamID + " | " +
               nickname + " @ " + "(" +
               square.getX() + ", " + square.getY() + ")";
    }

    public abstract boolean tryMove(Board board, int x, int y);
}
