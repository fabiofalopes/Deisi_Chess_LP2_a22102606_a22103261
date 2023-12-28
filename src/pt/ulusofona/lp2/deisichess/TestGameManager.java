package pt.ulusofona.lp2.deisichess;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import java.util.List;

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

    @Test
    public void moveKingVerticalTop() throws IOException, InvalidGameInputException {
        this.gameManager.loadGame(this.gameScenario);
        Game currentGame = this.gameManager.getGame();

        // Black plays first
        Piece blackKing = currentGame.getPieceByPosition(0, 0);
        this.gameManager.move(blackKing.getPositionX(), blackKing.getPositionY(), 0, 1);

        // then white plays
        Piece whiteKing = currentGame.getPieceByPosition(0, 7);
        this.gameManager.move(whiteKing.getPositionX(), whiteKing.getPositionY(), 0, 6);

        Assertions.assertEquals(0, blackKing.getPositionX());
        Assertions.assertEquals(1, blackKing.getPositionY());

        Assertions.assertEquals(0, whiteKing.getPositionX());
        Assertions.assertEquals(6, whiteKing.getPositionY());

    }

    @Test
    public void undoMoveRevertsGameState() throws IOException, InvalidGameInputException {
        this.gameManager.loadGame(this.gameScenario);
        Game initialGame = this.gameManager.getGame().clone();

        // Faz um move com o rei preto de (0,0) -> (0,1)
        this.gameManager.move(0, 0, 0, 1);

        // Aplica o undo
        this.gameManager.undo();
        Game revertedGame = this.gameManager.getGame();

        // Após undo tem que ficar no mesmo estado incial

        Assertions.assertEquals(initialGame.getPlayingTeamId(), revertedGame.getPlayingTeamId());

    }

    @Test
    public void getSquareInfoReturnsCorrectInfo() throws IOException, InvalidGameInputException {
        this.gameManager.loadGame(this.gameScenario);

        //Inicio, o rei preto em (0,0)
        String[] squareInfo = this.gameManager.getSquareInfo(0, 0);

        Assertions.assertNotNull(squareInfo);
        Assertions.assertEquals("1", squareInfo[0]); // KingPieceID
        Assertions.assertTrue(squareInfo[1].contains("Rei"));
    }

    @Test
    public void saveGameSavesCorrectly() throws IOException, InvalidGameInputException {
        File savedGameFile = new File("test-files/saved-game.txt");
        this.gameManager.loadGame(this.gameScenario);
        this.gameManager.saveGame(savedGameFile);
        Assertions.assertTrue(savedGameFile.exists());
    }

    @Test
    public void getHintsProvidesCorrectHints() throws IOException, InvalidGameInputException {
        this.gameManager.loadGame(this.gameScenario);
        // Recebe hints to rei preto em (0,0)
        List<Comparable> hints = this.gameManager.getHints(0, 0);
        Assertions.assertNotNull(hints);
        Assertions.assertFalse(hints.isEmpty());
    }

    @Test
    public void getPieceInfoReturnsCorrectWhiteMagicLittleHorseInfo() throws IOException, InvalidGameInputException {
        this.gameManager.loadGame(this.gameScenario);
        // Procura rainha branca no inicio do jogo e devolve a informação pelo getPieceInfo
        Piece piece = this.gameManager.getGame().getPieceById(11); // MagicLittleHorse com id 11 e tipo de peça 2
        String[] pieceInfo = this.gameManager.getPieceInfo(piece.getId()); // Procura a informação da peça com id 1
        Assertions.assertNotNull(pieceInfo);
        Assertions.assertEquals("11", pieceInfo[0]); // PieceID - id do ficheiro
        Assertions.assertEquals("2", pieceInfo[1]); // PieceTypeID - id do tipo de peça
        Assertions.assertEquals("20", pieceInfo[2]); // TeamID - black: 10, white: 20
        Assertions.assertEquals("My Little Pony", pieceInfo[3]); // Nickname - nome no ficheiro
        Assertions.assertEquals("em jogo", pieceInfo[4]); // Status - updated, em jogo, "capturada" ou "em jogo"
        Assertions.assertEquals("2", pieceInfo[5]); // PositionX - coordenada x
        Assertions.assertEquals("7", pieceInfo[6]); // PositionY - coordenada y
    }

    @Test
    public void testPieceInfoReturnsCorrectString() throws IOException, InvalidGameInputException {
        this.gameManager.loadGame(this.gameScenario);
        // Correct string: "11 | 2 | 20 | My Little Pony @ (2, 7)"
        Piece piece = this.gameManager.getGame().getPieceById(11); // MagicLittleHorse com id 11 e tipo de peça 2
        String pieceInfo = this.gameManager.getPieceInfoAsString(piece.getId()); // Procura a informação da peça com id 1

        Assertions.assertEquals("11 | Ponei Mágico | 5 | 20 | My Little Pony @ (2, 7)", pieceInfo);
    }

    @Test
    public void testGetCurrentTeamID() throws IOException, InvalidGameInputException {
        this.gameManager.loadGame(this.gameScenario);
        Assertions.assertEquals(10, this.gameManager.getCurrentTeamID());
        this.gameManager.move(0, 0, 0, 1);
        Assertions.assertEquals(20, this.gameManager.getCurrentTeamID());
    }

    @Test
    public void testGameOverPlayGameUntilEnd() throws IOException, InvalidGameInputException{

        this.gameManager.loadGame(this.gameScenario);
        Game currentGame = this.gameManager.getGame();

        // Black plays: King from (0,0) -> (0,1)
        Piece blackKing = currentGame.getPieceByPosition(0, 0);
        this.gameManager.move(blackKing.getPositionX(), blackKing.getPositionY(), 0, 1);

        // White plays: King from (0,7) -> (0,6)
        Piece whiteKing = currentGame.getPieceByPosition(0, 7);
        this.gameManager.move(whiteKing.getPositionX(), whiteKing.getPositionY(), 0, 6);

        // Black plays: Queen from (1,0) -> (1,5)
        Piece blackQueen = currentGame.getPieceByPosition(1, 0);
        this.gameManager.move(blackQueen.getPositionX(), blackQueen.getPositionY(), 1, 5);

        // White plays: MagicLittleHorse from (2,7) -> (4,5)
        Piece whiteMagicLittleHorse = currentGame.getPieceByPosition(2, 7);
        this.gameManager.move(whiteMagicLittleHorse.getPositionX(), whiteMagicLittleHorse.getPositionY(), 4, 5);

        // Black plays: Queen takes King from (1,5) -> (0,6)
        this.gameManager.move(blackQueen.getPositionX(), blackQueen.getPositionY(), 0, 6);

        Assertions.assertTrue(this.gameManager.gameOver());


    }
}
