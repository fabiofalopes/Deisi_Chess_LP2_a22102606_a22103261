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
    public void addSquare(Square square) {
        this.squares.add(square);
    }
    public void addPosition(String position){
        this.positions.add(position);
    }
    public boolean isValidPosition(int x, int y){
        String position = x + "" + y;
        return this.positions.contains(position);
    }

}
