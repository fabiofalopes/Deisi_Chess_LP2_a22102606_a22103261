package pt.ulusofona.lp2.deisichess;

import java.util.ArrayList;

public abstract class ChessPiece {
    protected int id;
    protected String typeName;
    protected int value;
    protected String nickname;
    protected String image = null;
    protected boolean defeated;
    protected Square square;

    protected ChessPiece(int id, String nickame){
        this.id = id;
        this.nickname = nickame;
        this.defeated = true;
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

    public abstract boolean tryMove(ArrayList<Square> board, int x, int y);
}
