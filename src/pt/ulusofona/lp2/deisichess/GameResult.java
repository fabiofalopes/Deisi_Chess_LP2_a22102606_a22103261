package pt.ulusofona.lp2.deisichess;

import java.util.ArrayList;

public class GameResult {
    private final int tieGameRule = 10;
    private boolean isTie;
    private boolean blackTeamWins;
    private boolean whiteTeamWins;
    private boolean gameOver;
    private int blackTeamLivePieces;
    private int blackTeamDeadPieces;
    private int whiteTeamLivePieces;
    private int whiteTeamDeadPieces;

    GameResult(ArrayList<Piece> pieces, int countRoundsWithoutKills){
        for (Piece piece : pieces) {
            switch (piece.getTeamId()){
                case Team.BLACK_TEAM_ID -> {
                    if(piece.isDead()){ this.blackTeamDeadPieces += 1; }
                    else { this.blackTeamLivePieces += 1; }
                }
                case Team.WHITE_TEAM_ID -> {
                    if(piece.isDead()){ this.whiteTeamDeadPieces += 1; }
                    else { this.whiteTeamLivePieces += 1; }
                }
            }
        }

        this.isTie = (this.blackTeamLivePieces == 1 && this.whiteTeamLivePieces == 1) || countRoundsWithoutKills >= this.tieGameRule;

        this.blackTeamWins = this.whiteTeamLivePieces == 0 && this.blackTeamLivePieces > 0;

        this.whiteTeamWins = this.blackTeamLivePieces == 0 && this.whiteTeamLivePieces > 0;

        this.gameOver = this.isTie || this.blackTeamWins || this.whiteTeamWins;
    }

    boolean getIsTie(){
        return this.isTie;
    }

    boolean getBlackTeamWins(){
        return this.blackTeamWins;
    }

    /*boolean getWhiteTeamWins(){
        return this.whiteTeamWins;
    }*/

    boolean getGameOver(){
        return this.gameOver;
    }
}
