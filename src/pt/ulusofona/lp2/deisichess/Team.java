package pt.ulusofona.lp2.deisichess;

import java.util.HashMap;

public class Team {
    private int ID;
    private String name;
    private int invalidMoves;
    private int validMoves;
    private int countKills;
    private int countDeads;
    private HashMap<Integer, ChessPiece> pieces;

    public Team(int ID, String name){
        if(name == null || name.isEmpty()){
            throw new IllegalArgumentException("name is required to create a team");
        }

        this.ID = ID;
        this.name = name;
        this.invalidMoves = 0;
        this.validMoves = 0;
        this.countKills = 0;
        this.countDeads = 0;
        this.pieces = new HashMap<>();
    }

    // region Methods
    public void addInvalidMove(){
        this.invalidMoves += 1;
    }

    public void addValidMove(){
        this.validMoves += 1;
    }

    public void addKill(){
        this.countKills += 1;
    }

    public void addDead(){ this.countDeads += 1;}

    public boolean addPiece(int pieceID, ChessPiece piece){
        if(piece == null){
            return false;
        }

        if(this.pieces.get(pieceID) != null){
            return false;
        }

        this.pieces.put(pieceID, piece);
        return true;
    }

    public String[] getResult(){
        return new String[] {
            "Equipa das " + this.name,
            this.countKills + "",
            this.validMoves + "",
            this.invalidMoves + ""
        };
    }
    // endregion
}
