package pt.ulusofona.lp2.deisichess;

import java.util.ArrayList;

public class Game implements Cloneable{
    private ArrayList<Square> squares;
    private ArrayList<Piece> pieces;
    private ArrayList<Game> backup;
    private Team blackTeam;
    private Team whiteTeam;
    private int countValidRounds;
    private int countMovesWithoutKills;
    private int playingTeamId;

    Game(){
        this.squares = new ArrayList<>();
        this.pieces = new ArrayList<>();
        this.backup = new ArrayList<>();
        this.blackTeam = new Team(Team.BLACK_TEAM_ID, Team.BLACK_TEAM_NAME);
        this.whiteTeam = new Team(Team.WHITE_TEAM_ID, Team.WHITE_TEAM_NAME);
        this.countValidRounds = 0;
        this.countMovesWithoutKills = 0;
        this.playingTeamId = Team.BLACK_TEAM_ID;
    }

    public void updateFromSavedFile(int playingTeamId, int countValidRounds, int countMovesWithoutKills){
        this.playingTeamId = playingTeamId;
        this.countValidRounds = countValidRounds;
        this.countMovesWithoutKills = countMovesWithoutKills;
    }

    ArrayList<Square> getSquares(){
        return this.squares;
    }

    ArrayList<Piece> getPieces(){
        return this.pieces;
    }

    Piece getPieceById(int id){
        for (Piece piece : this.pieces) {
            if(piece.getId() == id){
                return piece;
            }
        }

        return null;
    }

    Piece getPieceByPosition(int x, int y){
        for (Piece piece : this.pieces) {
            if(piece.isOnPosition(x, y)){
                return piece;
            }
        }

        return null;
    }

    Piece getPlayingPiece(int x, int y, int teamId){
        for (Piece piece : this.pieces) {
            if(piece.getTeamId() == teamId && piece.isOnPosition(x, y)){
                return piece;
            }
        }

        return null;
    }

    Piece getDestinyPiece(int x, int y){
        for (Piece piece : this.pieces) {
            if(piece.isOnPosition(x, y)){
                return piece;
            }
        }

        return null;
    }

    int getCountValidRounds(){
        return this.countValidRounds;
    }

    int getCountMovesWithoutKills(){ return this.countMovesWithoutKills; }

    int getPlayingTeamId(){
        return this.playingTeamId;
    }

    Team getPlayingTeam(){
        return this.getPlayingTeamId() == blackTeam.getId() ? this.blackTeam : this.whiteTeam;
    }

    Team getBlackTeam(){ return this.blackTeam; }

    Team getWhiteTeam(){ return this.whiteTeam; }

    Game getAndRemoveLastBackup(){
        ArrayList<Game> backup = this.backup;
        int backupSize = backup.size();

        if (backupSize > 0) {
            // get the last backup (current state)
            Game lastBackup = backup.get(backupSize - 1);
            // remove the last backup from the list
            backup.remove(backupSize - 1);
            return lastBackup;
        }

        return null; // return null if there are no backups
    }

    int getBoardSize(){
        return (int) Math.sqrt(this.squares.size());
    }

    @Override
    public Game clone() {
        try {
            Game clonedGame = (Game) super.clone();

            clonedGame.pieces = new ArrayList<>();
            for (Piece piece : this.pieces) {
                clonedGame.addPiece(piece.clone());
            }

            clonedGame.blackTeam = this.blackTeam.clone();
            clonedGame.whiteTeam = this.whiteTeam.clone();

            return clonedGame;
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

    void addSquare(Square square){
        this.squares.add(square);
    }

    void addPiece(Piece piece){
        this.pieces.add(piece);
    }

    void addBackup(Game game){
        this.backup.add(game);
    }

    void incrementValidRounds(){
        this.countValidRounds += 1;
    }

    void incrementMovesWithoutKills() { this.countMovesWithoutKills += 1; }

    void resetMovesWithoutKills() { this.countMovesWithoutKills = 0; }

    void togglePlayingTeamId(){
        this.playingTeamId = this.playingTeamId == Team.BLACK_TEAM_ID ? Team.WHITE_TEAM_ID : Team.BLACK_TEAM_ID;
    }
}
