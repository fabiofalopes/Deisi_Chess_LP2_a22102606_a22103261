package pt.ulusofona.lp2.deisichess;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;

public class TestGameManager {
    @Test
    public void loadGameWithoutReadingFile(){
        GameManager game = new GameManager();
        Assertions.assertTrue(game.gameOver());
    }
    @Test
    public void loadGameWithVictoryForBlackTeam(){
        GameManager game = new GameManager();
        File file = new File("test-files/board-complete-black-team-wins.txt");
        game.loadGame(file);
        Assertions.assertTrue(game.gameOver());
    }
    @Test
    public void loadGameWithoutPositions(){
        GameManager game = new GameManager();
        File file = new File("test-files/board-no-positions.txt");
        game.loadGame(file);
        Assertions.assertTrue(game.gameOver());
    }
    @Test
    public void loadGameWithoutPositionsAndPieces(){
        GameManager game = new GameManager();
        File file = new File("test-files/board-no-positions-no-pieces.txt");
        game.loadGame(file);
        Assertions.assertTrue(game.gameOver());
    }
    @Test
    public void loadGameWithoutPieces(){
        GameManager game = new GameManager();
        File file = new File("test-files/board-no-pieces.txt");
        game.loadGame(file);
        Assertions.assertTrue(game.gameOver());
    }
    @Test
    public void getBoardSize(){
        GameManager game = new GameManager();
        File file = new File("test-files/4x4.txt");
        game.loadGame(file);
        Assertions.assertEquals(4, game.getBoardSize());
    }
    @Test
    public void loadGameGetFirstTeamPlaying(){
        GameManager game = new GameManager();
        File file = new File("test-files/4x4.txt");
        game.loadGame(file);
        Assertions.assertEquals(0, game.getCurrentTeamID());
    }
    @Test
    public void getPieceInfoWrongPieceId(){
        GameManager game = new GameManager();
        File file = new File("test-files/4x4.txt");
        game.loadGame(file);
        Assertions.assertNull(game.getPieceInfo(-1));
    }
}
