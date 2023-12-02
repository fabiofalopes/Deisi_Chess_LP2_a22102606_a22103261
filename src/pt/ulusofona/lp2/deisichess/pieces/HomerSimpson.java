package pt.ulusofona.lp2.deisichess.pieces;

import pt.ulusofona.lp2.deisichess.ChessPiece;
import pt.ulusofona.lp2.deisichess.Square;
import java.util.ArrayList;

public class HomerSimpson extends ChessPiece {

    HomerSimpson(int id, String nickame) {
        super(id, nickame);
        this.typeName = "Homer Simpson";
        this.value = 2;
    }

    @Override
    public boolean isSleeping(int count){
        return count % 3 == 0;
    }
    @Override
    public boolean tryMove(ArrayList<Square> board, int x, int y) {
        return false;
    }
}
