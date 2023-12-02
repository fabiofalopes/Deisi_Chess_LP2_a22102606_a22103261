package pt.ulusofona.lp2.deisichess.pieces;

import pt.ulusofona.lp2.deisichess.ChessPiece;
import pt.ulusofona.lp2.deisichess.Square;
import java.util.ArrayList;

public class King extends ChessPiece {

    King(int id, String nickame) {
        super(id, nickame);
        this.typeName = "Rei";
        this.value = 1000;
    }

    @Override
    public boolean tryMove(ArrayList<Square> board, int x, int y) {
        return false;
    }
}
