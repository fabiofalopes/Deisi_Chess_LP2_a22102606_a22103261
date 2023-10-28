package pt.ulusofona.lp2.deisichess;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.*;

public class ChessPiece {
    private int ID;
    private int teamID; // Black Pieces - team : 0 | White Pieces team : 1
    private int type; // King : 0
    private String nickname;
    private String image = null;
    private int coordX;
    private int coordY;
    private boolean dead = false;

    public ChessPiece(int ID, int type, int teamID, String nickname) {
        this.ID = ID;
        this.type = type;
        this.teamID = teamID;
        this.nickname = nickname;
        //BufferedImage imageFile;
        /*try {
            imageFile = ImageIO.read(new File("./src/images/king-" + this.ID + "-team.png"));
            this.image = imageFile.toString();
        }  catch (IOException e) {
            this.image = "";
        }*/
    }

    public ChessPiece(int ID, int type, int teamID, String nickname, String image){
        this.ID = ID;
        this.type = type;
        this.teamID = teamID;
        this.nickname = nickname;
        this.image = image;
    }

    // region Methods
    public void updateCoordinates(int coordX, int coordY) {
        this.coordX = coordX;
        this.coordY = coordY;
    }

    private String getStatus(){
        return this.dead ? "capturado" : "em jogo";
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

    public String[] getInfoWithStatus(){
        return new String[] {
                this.ID + "",
                this.type + "",
                this.teamID + "",
                this.getStatus(),
                this.coordX + "",
                this.coordY + ""
        };
    }

    public void kill(){
        this.dead = true;
    }

    @Override
    public String toString() {
        return this.ID + " | " +
               this.type + " | " +
               this.teamID + " | " +
               this.nickname + " | @(" +
               this.coordX + ", " +
               this.coordY + ")";
    }
    public int getTeamID(){
        return this.teamID;
    }
    // endregion
}
