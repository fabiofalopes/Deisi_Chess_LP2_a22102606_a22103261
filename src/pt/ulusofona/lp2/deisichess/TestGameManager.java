package pt.ulusofona.lp2.deisichess;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;

public class TestGameManager {
    private GameManager gameManager;
    private File gameScenario;
    public TestGameManager(){
        this.gameManager = new GameManager();
        this.gameScenario = new File("test-files/part-ii-8x8.txt");
    }

    @Test
    public void loadGame() throws IOException, InvalidGameInputException {
        this.gameManager.loadGame(this.gameScenario);
        Game currentGame = this.gameManager.getGame();

        Assertions.assertEquals(8, currentGame.getBoardSize());
        Assertions.assertEquals(16, currentGame.getPieces().size());
        Assertions.assertEquals(10, currentGame.getPlayingTeamId());
    }

    @Test
    public void moveKingVerticalBottom() throws IOException, InvalidGameInputException {
        this.gameManager.loadGame(this.gameScenario);
        Game currentGame = this.gameManager.getGame();

        Piece piece = currentGame.getPieceByPosition(0, 0);
        this.gameManager.move(piece.getPositionX(), piece.getPositionY(), 0, 1);
        Assertions.assertEquals(0, piece.getPositionX());
        Assertions.assertEquals(1, piece.getPositionY());
    }
}
