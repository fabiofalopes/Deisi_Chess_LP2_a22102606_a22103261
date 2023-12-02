package pt.ulusofona.lp2.deisichess;

import java.util.ArrayList;

public class Board {
    private ArrayList<Square> squares;
    private ArrayList<String> positions;

    public Board(){
        this.squares = new ArrayList<>();
        this.positions = new ArrayList<>();
    }

    public ArrayList<Square> getSquares() {
        return squares;
    }
    public ArrayList<String> getPositions() {
        return positions;
    }

    public void addSquare(Square square) {
        this.squares.add(square);
    }

    public void addPosition(String position){
        this.positions.add(position);
    }

}
