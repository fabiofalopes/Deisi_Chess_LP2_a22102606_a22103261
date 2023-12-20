package pt.ulusofona.lp2.deisichess;

public class Square implements Cloneable {
    private int x;
    private int y;

    Square(int x, int y){
        this.x = x;
        this.y = y;
    }

    public boolean equals(int x, int y){
        return this.x == x && this.y == y;
    }

    @Override
    public Square clone() {
        try {
            return (Square) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }
}
