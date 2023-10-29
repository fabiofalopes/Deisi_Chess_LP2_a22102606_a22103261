package pt.ulusofona.lp2.deisichess;

import java.util.HashMap;

public class Team {
    private int ID;
    private String name;
    private int countInvalidMoves;
    private int countValidMoves;
    private int countCaptures;
    private int countSelfCaptures;
    private HashMap<Integer, ChessPiece> pieces;

    public Team(int ID, String name){
        this.ID = ID;
        this.name = name;
        this.pieces = new HashMap<>();
    }
    public String getName(){
        return this.name;
    }
    public void incrementInvalidMove(){
        this.countInvalidMoves += 1;
    }
    public void incrementValidMove(){ this.countValidMoves += 1; }
    public void incrementCapture() { this.countCaptures += 1; }
    public void incrementSelfCapture(){ this.countSelfCaptures += 1; }
    public void addPiece(int pieceID, ChessPiece piece){
        this.pieces.put(pieceID, piece);
    }
    public String[] getResult(){
        return new String[] {
            GameProperties.teamMessage + this.name,
            this.countCaptures + "",
            this.countValidMoves + "",
            this.countInvalidMoves + ""
        };
    }
    public int getCountCaptures(){ return this.countCaptures; }
    public int getCountSelfCaptures(){
        return this.countSelfCaptures;
    }
    public boolean hasCaptures(){
        return this.getCountCaptures() > 1;
    }
    public boolean isAlive(){ return this.pieces.size() > this.countSelfCaptures; }
}
