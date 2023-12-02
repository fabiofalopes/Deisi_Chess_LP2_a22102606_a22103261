package pt.ulusofona.lp2.deisichess.pieces;

import pt.ulusofona.lp2.deisichess.ChessPiece;
import pt.ulusofona.lp2.deisichess.Square;
import java.util.ArrayList;

public class Queen extends ChessPiece {

    Queen(int id, String nickame) {
        super(id, nickame);
        this.typeName = "Rainha";
        this.value = 8;
    }

    @Override
    public boolean tryMove(ArrayList<Square> board, int x, int y) {
        return false;
    }
}
