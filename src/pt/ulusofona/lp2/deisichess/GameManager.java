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

    public Game getGame(){
        return this.game;
    }

    void init(){
        this.game = new Game();
    }

    public void loadGame(File file) throws InvalidGameInputException, IOException {
        if(file == null){
            throw new IOException();
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

            // load statistics
            if(reader.ready()){
                // [GAME]
                String gameInfo = reader.readLine().trim();
                String[] gameData = gameInfo.split(":");
                this.game.updateFromSavedFile(
                    Integer.parseInt(gameData[0]),
                    Integer.parseInt(gameData[1]),
                    Integer.parseInt(gameData[2])
                );
                // [BLACK-TEAM]
                String blackTeamInfo = reader.readLine().trim();
                String[] blackTeamData = blackTeamInfo.split(":");
                this.game.getBlackTeam().updateFromSavedFile(
                    Integer.parseInt(blackTeamData[0]),
                    Integer.parseInt(blackTeamData[1]),
                    Integer.parseInt(blackTeamData[2]),
                    Integer.parseInt(blackTeamData[3])
                );
                // [WHITE-TEAM]
                String whiteTeamInfo = reader.readLine().trim();
                String[] whiteTeamData = whiteTeamInfo.split(":");
                this.game.getWhiteTeam().updateFromSavedFile(
                        Integer.parseInt(whiteTeamData[0]),
                        Integer.parseInt(whiteTeamData[1]),
                        Integer.parseInt(whiteTeamData[2]),
                        Integer.parseInt(whiteTeamData[3])
                );
                // [PIECES]
                for (int i = 0; i < this.game.getPieces().size(); i++) {
                    String pieceInfo = reader.readLine().trim();
                    String[] pieceData = pieceInfo.split(":");

                    Piece piece = this.game.getPieceById(Integer.parseInt(pieceData[0]));
                    if(piece != null){
                        piece.updateFromSavedFile(
                            Integer.parseInt(pieceData[1]),
                            Integer.parseInt(pieceData[2]),
                            Integer.parseInt(pieceData[3]),
                            Integer.parseInt(pieceData[4]),
                            Integer.parseInt(pieceData[5]),
                            Integer.parseInt(pieceData[6])
                        );
                    }
                }
            }

            this.game.addBackup(this.game.clone());
        } catch (IOException e){
            throw new IOException();
        }
    }

    public int getBoardSize(){
        return (int) Math.sqrt(this.game.getSquares().size());
    }

    public boolean move(int x0, int y0, int x1, int y1){
        this.game.addBackup(this.game.clone());

        Team playingTeam = this.game.getPlayingTeam();
        Piece playingPiece = this.game.getPlayingPiece(x0, y0, playingTeam.getId());
        ArrayList<Square> squares = this.game.getSquares();

        // (invalid move): i)   piece is trying to move outside the board
        //                 ii)  same position on both coordinates
        //                 iii) no current piece on (x,y)
        if(!Movement.isWithinBounds(squares, x0, y0) ||
           !Movement.isWithinBounds(squares, x1, y1) ||
           (x0 == x1 && y0 == y1) || playingPiece == null) {
                playingTeam.incrementInvalidMoves();

                if(playingPiece != null)
                {
                    playingPiece.incrementCountInvalidMoves();
                }
                return false;
        }

        ArrayList<Piece> pieces = this.game.getPieces();

        boolean validMove = playingPiece.validMoveRules(pieces, x1, y1, this.game.getCountValidRounds());
        // (invalid move): invalid move rule
        if(!validMove){
            playingTeam.incrementInvalidMoves();
            playingPiece.incrementCountInvalidMoves();
            return false;
        }

        Piece destinyPiece = this.game.getDestinyPiece(x1, y1);
        if(destinyPiece != null){

            // (invalid move) destiny piece from the same team
            if(destinyPiece.getTeamId() == playingPiece.teamId){
                playingTeam.incrementInvalidMoves();
                playingPiece.incrementCountInvalidMoves();
                return false;
            }

            // (invalid move) playing piece is trying to kill joker,
            //                that is impersonating a queen at the moment
            if(playingPiece.isQueen() && destinyPiece.isJoker() && ((PieceJoker)destinyPiece).impersonateIsQueen()){
                playingTeam.incrementInvalidMoves();
                playingPiece.incrementCountInvalidMoves();
                return false;
            }

            int destinyPieceValue = destinyPiece.getValue();

            destinyPiece.killPosition();
            playingPiece.incrementCountKills();
            playingPiece.addKillsScore(destinyPieceValue);
            playingTeam.incrementKillsAndScore(destinyPieceValue);
            this.game.resetMovesWithoutKills();
        } else { this.game.incrementMovesWithoutKills(); }

        // update playing piece position
        playingPiece.setPosition(x1, y1);

        // ensure joker's clone next piece
        for (Piece piece : pieces) {
            if(piece.isJoker()){
                ((PieceJoker)piece).nextImpersonate();
            }
        }

        playingPiece.incrementCountValidMoves();
        playingTeam.incrementValidMoves();
        this.game.togglePlayingTeamId();
        this.game.incrementValidRounds();

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
                    String.valueOf(piece.getTypeId()),
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
        return new GameResult(this.game.getPieces(), this.game.getCountMovesWithoutKills()).getGameOver();
    }

    public ArrayList<String> getGameResults(){
        GameResult gameResult = new GameResult(this.game.getPieces(), this.game.getCountMovesWithoutKills());

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

    public void saveGame(File file) throws IOException {
        if(file == null){
            throw new IOException();
        }

        int boardSize = this.getBoardSize();
        ArrayList<Piece> pieces = this.game.getPieces();
        int piecesSize = pieces.size();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(boardSize + "\n" + piecesSize + "\n");

            for (int id = 1; id <= piecesSize; id++) {
                for (Piece piece : pieces) {
                    if(piece.getId() == id){
                        writer.write(piece.getId() + ":" + piece.getTypeId() + ":" + piece.getTeamId() + ":" + piece.getNickname() + "\n");
                        break;
                    }
                }
            }

            for (int row = 0; row < boardSize; row++) {
                String result = "";

                for (int column = 0; column < boardSize; column++){
                    boolean hasPieceOnPosition = false;

                    for (Piece piece : pieces) {
                        if(piece.isOnPosition(column, row)){
                            hasPieceOnPosition = true;
                            result += piece.getId() + ":";
                            break;
                        }
                    }

                    if(!hasPieceOnPosition)
                    {
                        result += "0:";
                    }

                    // to remove the ":" from the last column
                    if(column == boardSize -1)
                    {
                        result = result.substring(0, result.length() - 1);
                        writer.write(result );

                        writer.newLine();
                    }
                }
            }

            // write statistics
            // [GAME]
            writer.write(this.game.getPlayingTeamId() + ":");
            writer.write(this.game.getCountValidRounds() + ":");
            writer.write(this.game.getCountMovesWithoutKills() + "\n");
            // [BLACK-TEAM]
            Team blackTeam = this.game.getBlackTeam();
            writer.write(blackTeam.getCountValidMoves() + ":");
            writer.write(blackTeam.getCountInvalidMoves() + ":");
            writer.write(blackTeam.getKills() + ":");
            writer.write(blackTeam.getScoreValue() + "\n");
            // [WHITE-TEAM]
            Team whiteTeam = this.game.getWhiteTeam();
            writer.write(whiteTeam.getCountValidMoves() + ":");
            writer.write(whiteTeam.getCountInvalidMoves() + ":");
            writer.write(whiteTeam.getKills() + ":");
            writer.write(whiteTeam.getScoreValue() + "\n");
            // [PIECES]
            for (Piece piece : pieces) {
                writer.write(piece.getId() + ":");
                writer.write(piece.getPositionX() + ":");
                writer.write(piece.getPositionY() + ":");
                writer.write(piece.getCountKills() + ":");
                writer.write(piece.getKillsScore() + ":");
                writer.write(piece.getCountValidMoves() + ":");
                writer.write(piece.getCountInvalidMoves() + "");
                writer.newLine();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
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
