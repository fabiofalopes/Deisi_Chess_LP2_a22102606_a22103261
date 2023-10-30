package pt.ulusofona.lp2.deisichess;

public class ChessPiece {
    private int id;
    private int type; // TODO: temp, 0 == king
    private String nickname;
    private String image = null;
    private boolean defeated;
    private Square square;

    ChessPiece(int id, int type, String nickame){
        this.id = id;
        this.type = type;
        this.nickname = nickame;
        this.defeated = true;
    }

    void updateImage(String image){
        this.image = image;
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
    Square getPosition(){
        return this.square;
    }
}
