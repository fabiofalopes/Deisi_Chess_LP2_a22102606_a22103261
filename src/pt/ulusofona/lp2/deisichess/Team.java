package pt.ulusofona.lp2.deisichess;

import org.testng.internal.collections.Pair;

import java.util.ArrayList;

public class Team {
    private int id;
    private String name;
    private int validMoves;
    private int invalidMoves;
    private int kills;
    private ArrayList<ChessPiece> pieces;

    public Team(int id, String name){
        this.id = id;
        this.name = name;
        this.validMoves = 0;
        this.invalidMoves = 0;
        this.kills = 0;
        this.pieces = new ArrayList<>();
    }

    void addPiece(ChessPiece piece){
        this.pieces.add(piece);
    }
    String getName(){
        return this.name;
    }
    ChessPiece getPieceById(int id){
        for (ChessPiece piece : pieces) {
            if(piece.getId() == id){
                return piece;
            }
        }

        return null;
    }
    ChessPiece getPieceByPosition(int x, int y){
        for (ChessPiece piece : pieces) {
            Pair<Integer, Integer> position = piece.getPosition();

            if(position == null){
                continue;
            }

            if(position.first().equals(x) && position.second().equals(y)){
                return piece;
            }
        }

        return null;
    }
    int getCountNonDefeated(){
        int count = 0;

        for (ChessPiece piece : pieces) {
            if(!piece.isDefeated()){
                count++;
            }
        }

        return count;
    }
    int getCountDefeated(){
        int count = 0;

        for (ChessPiece piece : pieces) {
            if(piece.isDefeated()){
                count++;
            }
        }

        return count;
    }
    boolean isDefeated(){
        return this.getCountDefeated() == pieces.size();
    }
    void incrementValidMove(){
        this.validMoves++;
    }
    void incrementInvalidMove(){
        this.invalidMoves++;
    }
    void incrementKills(){
        this.kills++;
    }
    String[] getScore(){
        return new String[] {
            GameStaticData.RESULT_TEAM_MESSAGE + this.name,
            this.kills + "",
            this.validMoves + "",
            this.invalidMoves + ""
        };
    }
}
