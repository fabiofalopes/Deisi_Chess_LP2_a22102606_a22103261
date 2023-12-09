package refactor;

import refactor.movements.BaseMovement;
import refactor.pieces.BasePiece;
import java.util.ArrayList;
import java.util.List;

public class GameManager {
    private List<List<Square>> board;
    private Team blackTeam;
    private Team whiteTeam;

    public GameManager() {
        this.init();
    }

    public void init(){
        this.board = new ArrayList<>();
        this.blackTeam = new Team(Team.BLACK_TEAM_ID, Team.BLACK_TEAM_NAME);
        this.whiteTeam = new Team(Team.WHITE_TEAM_ID, Team.WHITE_TEAM_NAME);
    }

    public boolean move(int x0, int y0, int x1, int y1){
        /* doesn't count as an invalid position,
           because there's no way from the UI to do so */
        if(!BaseMovement.isWithinBounds(board, x0, y0) || //
           !BaseMovement.isWithinBounds(board, x1, y1)) { //
            return false;
        }

        // [TODO] validate if (x0, y0) is from the TEAM that is playing

        /* validate/simulate piece move rules (WITHOUT MOVING)
           i)  validate piece move range and type of move
           ii) validate overlapping */
        Square currentSquare = this.board.get(y0).get(x0);
        BasePiece currentPiece = currentSquare.getPiece();
        boolean validMoveRule = currentPiece.validMoveRules(board, x1, y1);
        if(validMoveRule){
            // [TODO] increment invalid moves (...)
            return false;
        }

        Square destinySquare = this.board.get(y1).get(x1);
        BasePiece destinyPiece = destinySquare.getPiece();
        if(destinyPiece != null){
            if(currentPiece.getTeamId() == destinyPiece.getTeamId()){
                // [TODO] increment invalid moves (...)
                return false;
            }

            // hunt and feed the statistics
            destinyPiece.defeatMe();
            destinyPiece.removeSquare();
            destinySquare.removePiece();
            // [TODO] this.blackTeam?
            //        this.whiteTeam?
            //              .incrementKillsAndScore(destinyPiece.getValue());
        }

       /* update piece position
          i)  set current piece with destiny square
          ii) set destiny square with current piece*/
        currentPiece.setSquare(destinySquare);
        destinySquare.setPiece(currentPiece);

        // [TODO] ... finish with previous things from the first part

        return true;
    }
}
