package pt.ulusofona.lp2.deisichess.pieces;

import pt.ulusofona.lp2.deisichess.ChessPiece;
import pt.ulusofona.lp2.deisichess.Square;
import java.util.ArrayList;

public class VillagePriest extends ChessPiece {

    VillagePriest(int id, String nickame) {
        super(id, nickame);
        this.typeName = "Padre da Vila";
        this.value = 3;
    }

    @Override
    public boolean tryMove(ArrayList<Square> board, int x, int y) {
        return false;
    }
}
