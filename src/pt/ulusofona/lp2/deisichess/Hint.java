package pt.ulusofona.lp2.deisichess;

public class Hint implements Comparable<Hint>{
    private int value;
    private int x;
    private int y;

    Hint(int x, int y){
        this.x = x;
        this.y = y;
        value = 0;
    }

    Hint(int x, int y, int value){
        this(x, y);
        this.value = value;
    }

    public int getValue(){
        return this.value;
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    @Override
    public int compareTo(Hint o) {
        return Integer.compare(o.getValue(), this.value);
    }

    @Override
    public String toString(){
        return "(" + this.x + "," + this.y +") -> " + this.value;
    }
}
