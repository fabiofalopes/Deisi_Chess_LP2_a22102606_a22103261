package pt.ulusofona.lp2.deisichess;

import java.io.File;

public class TestGameManager {
    //Test loadGame by printing the squares and pieces values
    public static void main(String[] args) {
        GameManager gameManager = new GameManager();

        if (gameManager.loadGame(new File("test-files/4x4.txt"))) {
            gameManager.printPieces();
        }
        else {
            System.out.println("Error loading game");
        }
    }
}
