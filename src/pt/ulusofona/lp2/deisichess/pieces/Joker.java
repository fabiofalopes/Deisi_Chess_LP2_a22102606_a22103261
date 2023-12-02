package pt.ulusofona.lp2.deisichess.pieces;

import pt.ulusofona.lp2.deisichess.ChessPiece;
import pt.ulusofona.lp2.deisichess.Square;
import java.util.ArrayList;

public class Joker extends ChessPiece {
    private int cloneID;
    private ChessPiece clone;

    Joker(int id, String nickame) {
        super(id, nickame);
        this.typeName = "Joker";
        this.value = 4;
        this.cloneID = 2;
        this.clone = new Queen(id, nickame);
    }

    public void cloneNext(){
        this.cloneID++;

        switch (cloneID) {
            case 3 -> this.clone = new MagicLittleHorse(0, "");
            case 4 -> this.clone = new VillagePriest(0, "");
            case 5 -> this.clone = new HorizontalTower(0, "");
            case 6 -> this.clone = new VerticalTower(0, "");
            case 7 -> this.clone = new HomerSimpson(0, "");
            default -> {
                this.cloneID = 2;
                this.clone = new Queen(0, "");
            }
        }
    }

    @Override
    protected boolean isJoker(){ return true; }
    @Override
    public boolean tryMove(ArrayList<Square> board, int x, int y) {
        return this.clone.tryMove(board, x, y);
    }
}
