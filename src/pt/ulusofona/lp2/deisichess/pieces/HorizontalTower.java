package pt.ulusofona.lp2.deisichess.pieces;

import pt.ulusofona.lp2.deisichess.ChessPiece;
import pt.ulusofona.lp2.deisichess.Square;
import java.util.ArrayList;

public class HorizontalTower extends ChessPiece {

    HorizontalTower(int id, String nickame) {
        super(id, nickame);
        this.typeName = "TorreHor";
        this.value = 3;
    }

    @Override
    public boolean tryMove(ArrayList<Square> board, int x, int y) {
        return false;
    }
}
