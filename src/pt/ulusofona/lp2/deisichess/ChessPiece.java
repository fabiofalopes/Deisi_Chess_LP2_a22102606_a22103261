package pt.ulusofona.lp2.deisichess;

import java.util.ArrayList;

public class ChessPiece {
    private int ID;
    private int teamID; // Black team: 0 | White team: 1
    private int type; // King : 0
    private String nickname;
    private String image;
    private int currentCoordX;
    private int currentCoordY;
    private boolean captured;
    private ArrayList<Integer> captureLog;

    public ChessPiece(int ID, int type, int teamID, String nickname) {
        this.ID = ID;
        this.type = type;
        this.teamID = teamID;
        this.nickname = nickname;
    }

    public void updatePosition(int x, int y) {
        this.currentCoordX = x;
        this.currentCoordY = y;
    }
    private String getLifeStatus(){
        return this.captured ? "capturado" : "em jogo";
    }
    public String[] getInfo(){
        return new String[] {
                this.ID + "",
                this.type + "",
                this.teamID + "",
                this.nickname,
                this.image
        };
    }
    public String[] getInfoWithLifeStatusAndPosition(){
        return new String[] {
                this.ID + "",
                this.type + "",
                this.teamID + "",
                this.getLifeStatus(),
                this.currentCoordX + "",
                this.currentCoordY + ""
        };
    }
    public void capture(){
        this.captured = true;
    }
    public int getTeamID(){
        return this.teamID;
    }

    @Override
    public String toString() {
        return this.ID + " | " +
                this.type + " | " +
                this.teamID + " | " +
                this.nickname + " | @(" +
                this.currentCoordX + ", " +
                this.currentCoordY + ")";
    }
}
