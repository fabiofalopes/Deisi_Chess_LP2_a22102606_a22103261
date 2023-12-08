package refactor;

import pt.ulusofona.lp2.deisichess.ChessPiece;
import refactor.pieces.BasePiece;
import java.util.ArrayList;

public class Team {
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
            if(!piece.isDefeated()){
                count++;
            }
        }
        return count;
    }

    public int getCountDefeated(){
        int count = 0;

        for (BasePiece piece : pieces) {
            if(piece.isDefeated()){
                count++;
            }
        }

        return count;
    }

    public boolean isDefeated(){
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
