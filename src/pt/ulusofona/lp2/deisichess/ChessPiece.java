package pt.ulusofona.lp2.deisichess;

import org.testng.internal.collections.Pair;

public class ChessPiece {
    private int id;
    private int type; // TODO: temp, 0 == king
    private String nickname;
    private String image = null; // TODO
    private boolean defeated;
    private Square square;

    ChessPiece(int id, int type, String nickame){
        this.id = id;
        this.type = type;
        this.nickname = nickame;
        this.defeated = true;
    }

    int getId(){
        return this.id;
    }
    int getType(){
        return this.type;
    }
    String getNickname(){
        return this.nickname;
    }
    String getImage(){
        return this.image;
    }
    boolean isDefeated(){
        return this.defeated;
    }
    void revive(){
        this.defeated = false;
    }
    void defeatMe(){
        this.defeated = true;
        this.square = null;
    }
    void move(Square square){
        this.square = square;
    }
    Pair<Integer, Integer> getPosition(){
        return this.square != null ? this.square.getPosition() : null;
    }
}
