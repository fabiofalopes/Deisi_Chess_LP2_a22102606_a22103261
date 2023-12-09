package refactor;

import refactor.pieces.BasePiece;
import java.util.ArrayList;

public class Team {
    public static final int BLACK_TEAM_ID = 10;
    public static final String BLACK_TEAM_NAME = "Pretas";
    public static final int WHITE_TEAM_ID = 20;
    public static final String WHITE_TEAM_NAME = "Brancas";

    private int id;
    private String name;
    private int validMoves = 0;
    private int invalidMoves = 0;
    private int kills = 0;
    private  int score = 0;
    private ArrayList<BasePiece> pieces;

    public Team(int id, String name){
        this.id = id;
        this.name = name;
        this.pieces = new ArrayList<>();
    }

    public int getId(){
        return this.id;
    }
    public String getName(){
        return this.name;
    }
    public BasePiece getPieceById(int id){
        for (BasePiece piece : pieces) {
            if(piece.getId() == id){
                return piece;
            }
        }

        return null;
    }
    public int getCountNonDefeated(){
        int count = 0;

        for (BasePiece piece : pieces) {
            if(!piece.getIsDefeated()){
                count++;
            }
        }
        return count;
    }
    public int getCountDefeated(){
        int count = 0;

        for (BasePiece piece : pieces) {
            if(piece.getIsDefeated()){
                count++;
            }
        }

        return count;
    }
    public boolean getIsDefeated(){
        return this.getCountDefeated() == pieces.size();
    }

    public void incrementValidMove(){
        this.validMoves++;
    }
    public void incrementInvalidMove(){
        this.invalidMoves++;
    }
    public void incrementKillsAndScore(int pieceScore){
        this.score += score;
        this.kills++;
    }
}
