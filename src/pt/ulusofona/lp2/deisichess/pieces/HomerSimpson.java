package pt.ulusofona.lp2.deisichess.pieces;

import pt.ulusofona.lp2.deisichess.Board;
import pt.ulusofona.lp2.deisichess.ChessPiece;
import pt.ulusofona.lp2.deisichess.GameStaticData;

public class HomerSimpson extends ChessPiece {
    private boolean sleeping;

    public HomerSimpson(int teamID){ super(teamID); }

    public HomerSimpson(String nickame, int teamID) {
        super(nickame,teamID);
        this.id = GameStaticData.HOMER_SIMPSON_PIECE_ID;
        this.typeName = GameStaticData.HOMER_SIMPSON_NAME;
        this.value = GameStaticData.HOMER_SIMPSON_PIECE_VALUE;
        this.isSleepy = true;
    }

    @Override
    public boolean isSleeping(int count){ return this.sleeping; }
    @Override
    public boolean tryMove(Board board, int x, int y) {
        if(this.sleeping || !board.isValidPosition(x, y)){
            return false;
        }

        return false;
    }

    @Override
    protected String printInfo(){
        return this.sleeping ? "Doh! zzzzzz" : super.printInfo();
    }
}
