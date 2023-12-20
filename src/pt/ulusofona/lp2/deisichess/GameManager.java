package pt.ulusofona.lp2.deisichess;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

public class GameManager {
    private Game game;

    public GameManager(){
        this.init();
    }

    void init(){
        this.game = new Game();
    }

    public void loadGame(File file) throws InvalidGameInputException {
        if(file == null){
            return;
        }

        try {
            final int COUNT_COLUMNS_COUNT = 4;
            int countRead = 2;
            HashMap<Integer, Piece> loadedPieces = new HashMap<>();

            this.init();

            BufferedReader reader = new BufferedReader(new FileReader(file));

            int boardDimension = Integer.parseInt(reader.readLine().trim());
            int numberOfPieces = Integer.parseInt(reader.readLine().trim());

            // read and load pieces info
            for (int i = 0; i < numberOfPieces; i++){
                countRead++;

                String pieceInfo = reader.readLine();
                String[] pieceData = pieceInfo.split(":");

                // handle invalid data format exceptions
                if(pieceData.length < COUNT_COLUMNS_COUNT)
                {
                    throw new InvalidGameInputException(countRead, InvalidGameInputException.getLessDataErrorDescription(
                            COUNT_COLUMNS_COUNT, pieceData.length));
                }
                else if (pieceData.length > COUNT_COLUMNS_COUNT)
                {
                    throw new InvalidGameInputException(countRead, InvalidGameInputException.getMoreDataErrorDescription(
                            COUNT_COLUMNS_COUNT, pieceData.length));
                }

                // create piece
                int id = Integer.parseInt(pieceData[0].trim());
                int typeId = Integer.parseInt(pieceData[1].trim());
                int teamId = Integer.parseInt(pieceData[2].trim());
                String nickname = pieceData[3].trim();

                Piece piece = Piece.create(id, typeId, teamId, nickname);
                this.game.addPiece(piece);
                loadedPieces.put(id, piece);
            }

            // read and load board info
            for(int row = 0; row < boardDimension; row++){
                countRead++;

                String squareInfo = reader.readLine();
                String[] squareData = squareInfo.split(":");

                // handle invalid data format exceptions
                if(squareData.length < boardDimension)
                {
                    throw new InvalidGameInputException(countRead, InvalidGameInputException.getLessDataErrorDescription(
                            boardDimension, squareData.length));
                }
                else if (squareData.length > boardDimension)
                {
                    throw new InvalidGameInputException(countRead, InvalidGameInputException.getMoreDataErrorDescription(
                            boardDimension, squareData.length));
                }

                // create square
                for(int column = 0; column < boardDimension; column++){
                    int pieceId = Integer.parseInt(squareData[column].trim());

                    this.game.addSquare(new Square(column, row));

                    if(pieceId != 0){
                        Piece piece = loadedPieces.get(pieceId);
                        piece.setPosition(column, row);
                    }
                }
            }

            this.game.addBackup(this.game.clone());
        }
        catch (Exception e){
        }
    }

    public int getBoardSize(){
        return (int) Math.sqrt(this.game.getSquares().size());
    }

    public boolean move(int x0, int y0, int x1, int y1){
        // doesn't count as an invalid position,
        // because there's no way from the UI to do so
        ArrayList<Square> squares = this.game.getSquares();
        if(!Movement.isWithinBounds(squares, x0, y0) ||
           !Movement.isWithinBounds(squares, x1, y1)) {
            return false;
        }

        Team playingTeam = this.game.getPlayingTeam();
        Piece playingPiece = this.game.getPlayingPiece(x0, y0, playingTeam.getId());

        // (invalid move): i)   same position on both coordinates
        //                 ii)  no current piece on (x,y)
        if((x0 == x1 && y0 == y1) || playingPiece == null) {
            playingTeam.incrementInvalidMoves();
            //this.game.addBackup(this.game.clone());
            return false;
        }

        ArrayList<Piece> pieces = this.game.getPieces();

        boolean validMove = playingPiece.validMoveRules(pieces, x1, y1, this.game.getCountValidRounds());
        // (invalid move): invalid move rule
        if(!validMove){
            playingTeam.incrementInvalidMoves();
            //this.game.addBackup(this.game.clone());
            return false;
        }

        Piece destinyPiece = this.game.getDestinyPiece(x1, y1);
        if(destinyPiece != null){

            // (invalid move) destiny piece from the same team
            if(destinyPiece.getTeamId() == playingPiece.teamId){
                playingTeam.incrementInvalidMoves();
                //this.game.addBackup(this.game.clone());
                return false;
            }

            destinyPiece.killPosition();
            playingTeam.incrementKillsAndScore(destinyPiece.getValue());
            this.game.resetRoundsWithoutKills();
        } else { this.game.incrementRoundsWithoutKills(); }

        // update playing piece position
        playingPiece.setPosition(x1, y1);

        // ensure joker's clone next piece
        for (Piece piece : pieces) {
            if(piece.isJoker()){
                ((PieceJoker)piece).nextImpersonate();
            }
        }

        this.game.togglePlayingTeamId();
        this.game.incrementValidRounds();
        this.game.addBackup(this.game.clone());

        return true;
    }

    public String[] getSquareInfo(int x, int y){
        boolean coordinatesWithinBounds = Movement.isWithinBounds(this.game.getSquares(), x, y);
        if(!coordinatesWithinBounds){
            return null;
        }

        for (Piece piece : this.game.getPieces()) {
            if(piece.isOnPosition(x, y)){
                return new String[]{
                    String.valueOf(piece.getId()),
                    piece.getTypeName(),
                    String.valueOf(piece.getTeamId()),
                    piece.getNickname(),
                    piece.getImage()
                };
            }
        }

        return new String[]{};
    }

    public String[] getPieceInfo(int id){
        for (Piece piece : this.game.getPieces()) {
            if(piece.getId() == id){
                boolean isDead = piece.isDead();

                return new String[]{
                    String.valueOf(piece.getId()),
                    piece.getTypeName(),
                    String.valueOf(piece.getTeamId()),
                    piece.getNickname(),
                    isDead ? "capturado" : "em jogo",
                    String.valueOf(isDead ? "" : piece.getPositionX()),
                    String.valueOf(isDead ? "" : piece.getPositionY())
                };
            }
        }

        return null;
    }

    public String getPieceInfoAsString(int id){
        for (Piece piece : this.game.getPieces()) {
            if(piece.getId() == id){
                return piece.printInfo(this.game.getCountValidRounds());
            }
        }

        return "";
    }

    public int getCurrentTeamID(){
        return this.game.getPlayingTeamId();
    }

    public boolean gameOver(){
        return new GameResult(this.game.getPieces(), this.game.getCountRoundsWithoutKills()).getGameOver();
    }

    public ArrayList<String> getGameResults(){
        GameResult gameResult = new GameResult(this.game.getPieces(), this.game.getCountRoundsWithoutKills());

        String resultMessage = "";

        if(gameResult.getIsTie())
        {
            resultMessage = "EMPATE";
        }
        else
        {
            resultMessage = "VENCERAM AS ";
            resultMessage += gameResult.getBlackTeamWins() ?
                                Team.BLACK_TEAM_NAME.toUpperCase() :
                                    Team.WHITE_TEAM_NAME.toUpperCase();
        }

        ArrayList<String> message = new ArrayList<>();
        message.add("JOGO DE CRAZY CHESS");
        message.add("Resultado: " + resultMessage);
        message.add("---");
        message.addAll(Arrays.asList(this.game.getBlackTeam().getScore()));
        message.addAll(Arrays.asList(this.game.getWhiteTeam().getScore()));

        return message;
    }

    public JPanel getAuthorsPanel(){
        JPanel authorsPanel = new JPanel();
        authorsPanel.setLayout(new BoxLayout(authorsPanel, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Créditos");
        title.setFont(new Font("Arial", Font.BOLD,24));
        title.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        authorsPanel.add(title);

        String[] authors = {"a22102606 - Marcos Gil", "a22103261 - Fábio Lopes"};
        for (String author : authors){
            JLabel label = new JLabel(author);
            label.setFont(new Font("Arial", Font.PLAIN,18));
            label.setHorizontalAlignment(JLabel.CENTER);
            label.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
            authorsPanel.add(label);
        }

        return authorsPanel;
    }

    public void saveGame(File file){
        return;
    }

    public void undo(){
        Game lastBackup = this.game.getAndRemoveLastBackup();

        if (lastBackup != null) {
            this.game = lastBackup;
        }
    }

    List<Comparable> getHints(int x, int y){
        return null;
    }

    public Map<String, String> customizeBoard() {
        return new HashMap<>();
    }
}
