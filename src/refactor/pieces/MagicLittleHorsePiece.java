package refactor.pieces;

import refactor.Square;
import refactor.Team;
import java.util.List;

public class MagicLittleHorsePiece extends BasePiece{
    public static final int ID = 3;
    public MagicLittleHorsePiece(String nickname, Team team) {
        super(nickname, team);
        this.id = ID;
        this.typeName = "Ponei Mágico";
        this.value = 5;
        this.movementLimit = 2;
    }

    @Override
    public boolean validMoveRules(List<List<Square>> board, int x, int y) {

        int currentX = this.square.getX();
        int currentY = this.square.getY();

        int deltaX = Math.abs(currentX - x);
        int deltaY = Math.abs(currentY - y);

        if (deltaX != this.movementLimit && deltaY != this.movementLimit){
            return false;
        }
        
        int minX = Math.min(currentX, x);
        int maxX = Math.max(currentX, x);
        int minY = Math.min(currentY, y);
        int maxY = Math.max(currentY, y);

        int middleX = (currentX + x) / 2;
        int middleY = (currentY + y) / 2;


        /*

        class HelloWorld {
    public static void main(String[] args) {

        int minY = 0;
        int maxY = 3;
        int minX = 0;
        int maxX = 3;

        // section A (boolean)
            for (int column = minX + 1; column <= maxX; column++) {
                 System.out.println("(" + minY + "," + column + ")\n");
                 // check if has piece; return true;
            }
            //}
             for (int row = minY + 1; row < maxY; row++) {
                 System.out.println("(" + row + "," + maxX + ")\n");
                 // check if has piece; return true;
                }

                // section B

                for (int row = maxY - 1; row > minY; row--) {
            System.out.println("(" + row + "," + minX + ")");
            // Check if has piece; return true; (Add your piece-checking logic here)
        }

        for (int column = minX; column <= maxX - 1; column++) {
            System.out.println("(" + maxY + "," + column + ")");
            // Check if has piece; return true; (Add your piece-checking logic here)
        }
         // section B (boolean)
         //for (int row = minY; row < maxY; row++) {


        }
    }


        */

        for (int row = minY; row < maxY; row++) {
            for (int column = row + 1 ; column < maxX; column++) {
                 if(!(row == column)){
                     ;
                 }
            }
        }
            

        return false;
    }
}
