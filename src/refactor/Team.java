package refactor;

import refactor.pieces.BasePiece;
import refactor.pieces.HomerSimpsonPiece;
import refactor.pieces.JokerPiece;

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
    private boolean isPlaying;
    private ArrayList<BasePiece> pieces;

    public Team(int id, String name, boolean isPlaying){
        this.id = id;
        this.name = name;
        this.isPlaying = isPlaying;
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
    public boolean getIsPlaying(){
        return this.isPlaying;
    }
    public HomerSimpsonPiece getHomer(){
        for (BasePiece piece : pieces) {
            if(piece.getIsHomerSimpson()){
                return (HomerSimpsonPiece) piece;
            }
        }

        return null;
    }
    public JokerPiece getJoker(){
        for (BasePiece piece : pieces) {
            if(piece.getIsJoker() && !piece.getIsDefeated()){
                return (JokerPiece) piece;
            }
        }

        return null;
    }

    public void addPiece(BasePiece piece){
        this.pieces.add(piece);
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
    public void toggleIsPlaying(){
        this.isPlaying = !this.isPlaying;
    }
}
