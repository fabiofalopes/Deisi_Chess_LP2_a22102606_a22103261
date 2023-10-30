package pt.ulusofona.lp2.deisichess;

import org.testng.internal.collections.Pair;

public class Square {
    private int x;
    private int y;

    public Square(int x, int y){
        this.x = x;
        this.y = y;
    }

    Pair<Integer, Integer> getPosition(){
        return new Pair<>(this.x, this.y);
    }
    boolean equals(int x, int y){
        return this.x == x && this.y == y;
    }
}
