package pt.ulusofona.lp2.deisichess;

import java.awt.*;
import java.io.*;
import javax.swing.*;
import java.util.*;

public class GameManager {
    private ArrayList<Square> board;
    private HashSet<String> validBoardPositions;
    private int boardDimension;
    private HashMap<Integer, ChessPiece> pieces;
    private Team blackTeam;
    private Team whiteTeam;
    private boolean blackTeamIsPlaying;
    private int playsWithoutCaptures;
    private JPanel authorsPanel;
    private boolean notPlayable;
    private boolean initTie;
    private boolean initBlackTeamWin;
    private boolean initWhiteTeamWin;


    public GameManager(){
        this.initGame();
    }
    private void initGame(){
        this.board = new ArrayList<>();
        this.validBoardPositions = new HashSet<>();
        this.boardDimension = 0;
        this.pieces = new HashMap<>();
        this.blackTeam = new Team(GameProperties.blackTeamID, GameProperties.blackTeamName);
        this.whiteTeam = new Team(GameProperties.whiteTeamID, GameProperties.whiteTeamName);
        this.blackTeamIsPlaying = true;
        this.playsWithoutCaptures = 0;
        this.authorsPanel = new JPanel();
        this.notPlayable = false;
        this.initTie = false;
        this.initBlackTeamWin = false;
        this.initWhiteTeamWin = false;
    }
    private void makeItUnplayable(){
        this.notPlayable = true;
    }
    private boolean isValidBoardPosition(int x, int y){
        return this.validBoardPositions.contains(x + "" + y);
    }
    private int getBoardDimension(){
        return this.boardDimension;
    }
    private boolean setBoardDimension(int dimension){
        if(dimension < GameProperties.minBoardDimension) {
            return false;
        }

        this.boardDimension = dimension;
        return true;
    }
    private ChessPiece getPieceAtPosition(int x, int y) {
        for (Square square : board) {
            if (square.equals(x, y)) { // return piece with this coordinates
                return square.getPiece();
            }
        }
        // no match
        return null;
    }
    private boolean validMoveAndPositions(int x0, int y0, int x1, int y1){
        if(!this.isValidBoardPosition(x0, y0) || !this.isValidBoardPosition(x1, y1)){
            return false;
        }

        if(x0 == x1 && y0 == y1){ // same position
            return false;
        }

        ChessPiece currentPiece = getPieceAtPosition(x0, y0);
        ChessPiece targetPiece = getPieceAtPosition(x1, y1);

        if(currentPiece == null){
            return false;
        }

        // |x1 - x0| and |y1 - y0| has to be <= 1
        return Math.abs(x0 - x1) <= 1 &&
               Math.abs(y0 - y1) <= 1 &&
               (targetPiece == null || (currentPiece.getTeamID() != targetPiece.getTeamID()));
    }
    private Square getSquareAtPosition(int x, int y) {
        for (Square square : board) {
            if (square.equals(x, y)) {
                return square;
            }
        }
        return null;
    }
    private boolean isGameTie(){
        return (this.playsWithoutCaptures >= GameProperties.tieMoveRule &&
                    (this.blackTeam.hasDeadPieces() || this.whiteTeam.hasDeadPieces())
        );
    }


    public boolean loadGame(File file) {
        this.initGame();

        if(file == null){
            this.makeItUnplayable();
            return false;
        }

        try{
            BufferedReader reader = new BufferedReader(new FileReader(file));

            boolean validBoardDimension = this.setBoardDimension(Integer.parseInt(reader.readLine().trim()));
            int readNrPieces = Integer.parseInt(reader.readLine().trim());

            if(!validBoardDimension || readNrPieces < GameProperties.minChessPieces){
                this.makeItUnplayable();
                return false;
            }

            // read pieces info
            for(int i = 0; i < readNrPieces; i++){
                String row = reader.readLine();
                String[] columns = row.split(GameProperties.fileColumnSeparator);

                if(columns.length != GameProperties.fileInfoNrColumns){
                    this.makeItUnplayable();
                    return false;
                }

                int readPieceID = Integer.parseInt(columns[0].trim()),
                    readPieceType = Integer.parseInt(columns[1].trim()),
                    readPieceTeamID = Integer.parseInt(columns[2].trim());

                String readPieceNickname = columns[3].trim();

                if(!GameProperties.readPieceValidation(readPieceID, readPieceType, readPieceTeamID) ||
                    this.pieces.get(readPieceID) != null){ // to avoid duplicates
                        this.makeItUnplayable();
                        return false;
                }

                ChessPiece piece = new ChessPiece(
                        readPieceID,
                        readPieceType,
                        readPieceTeamID,
                        readPieceNickname);

                if(readPieceTeamID == GameProperties.blackTeamID){
                    this.blackTeam.addPiece(readPieceID, piece);
                }
                else {
                    this.whiteTeam.addPiece(readPieceID, piece);
                }

                this.pieces.put(readPieceID, piece);
            }

            // to ensure we don't place repeated pieces
            HashSet<Integer> piecesPlacedOnBoard = new HashSet<>();
            // read board squares info
            for(int colY = 0; colY < this.boardDimension; colY++){
                String row = reader.readLine().trim();
                String[] columns = row.split(GameProperties.fileColumnSeparator);

                if(columns.length != this.boardDimension){
                    this.makeItUnplayable();
                    return false;
                }

                for(int colX = 0; colX < boardDimension; colX++){
                    Square square = new Square(colX, colY);

                    int readPieceID = Integer.parseInt(columns[colX]);
                    ChessPiece piece = null;

                    // break if it has repeated pieceID
                    if(piecesPlacedOnBoard.contains(readPieceID)){
                        this.makeItUnplayable();
                        return false;
                    }

                    if(readPieceID != 0){ // has squad init position
                        piece = this.pieces.get(readPieceID);
                        // to ensure pieceID has a ChessPiece object
                        /*if(piece == null){
                            this.makeItUnplayable();
                            return false;
                        }*/

                        piecesPlacedOnBoard.add(readPieceID);
                        piece.updatePosition(colX, colY);
                        square.updatePiece(piece);
                    }

                    this.board.add(square);
                    this.validBoardPositions.add(colX + "" + colY);
                }
            }

            this.initTie = piecesPlacedOnBoard.isEmpty() || (this.blackTeam.getCountPieces() == 1 && this.whiteTeam.getCountPieces() == 1);

            int whiteTeamDeadPieces = 0;
            int blackTeamDeadPices = 0;
            // to capture pieces that aren't on the board
            for (Map.Entry<Integer, ChessPiece> piece : pieces.entrySet()) {
                if(!piecesPlacedOnBoard.contains(piece.getKey())){
                    ChessPiece capturedPiece = piece.getValue();
                    capturedPiece.capture();
                    capturedPiece.cleanPosition();

                    /*int teamID = capturedPiece.getTeamID();
                    if(teamID == GameProperties.blackTeamID){
                        blackTeamDeadPices++;
                    } else {
                        whiteTeamDeadPieces++;
                    }*/
                }
            }

            /*if(blackTeamDeadPices == this.blackTeam.getCountPieces() &&
              whiteTeamDeadPieces != this.whiteTeam.getCountPieces()){
                this.initWhiteTeamWin = true;
            }
            else if (whiteTeamDeadPieces == this.whiteTeam.getCountPieces() &&
                    blackTeamDeadPices != this.blackTeam.getCountPieces()){
                this.initBlackTeamWin = true;
            }
            else if (!this.initTie){
                if(blackTeamDeadPices == whiteTeamDeadPieces){
                    this.initTie = true;
                }
            }*/

            reader.close();
            return true;
        } catch (IOException e) {
            //System.err.println("Error reading file: " + e.getMessage());
        }

        this.makeItUnplayable();
        return false;
    }
    public int getBoardSize() {
        return this.notPlayable ? 0 : this.getBoardDimension();
    }
    public boolean move(int x0, int y0, int x1, int y1) {
        if(!this.blackTeam.isAlive() || !this.whiteTeam.isAlive()){
            return false;
        }

        if(this.blackTeam.getCountPieces() == 0 || this.whiteTeam.getCountPieces() == 0){
            return false;
        }

        if(this.notPlayable || this.gameOver()) {
            return false;
        }

        if(!this.validMoveAndPositions(x0, y0, x1, y1)){
            if (this.blackTeamIsPlaying) {
                this.blackTeam.incrementInvalidMove();
            } else {
                this.whiteTeam.incrementInvalidMove();
            }
            return false;
        }
        else {
            Square square = this.getSquareAtPosition(x0, y0);
            if(square.getPiece() == null){
                return false;
            }
            else {
                if(this.blackTeamIsPlaying && square.getPiece().getTeamID() != GameProperties.blackTeamID){
                    this.blackTeam.incrementInvalidMove();
                    return false;
                }
                else if (!this.blackTeamIsPlaying && square.getPiece().getTeamID() != GameProperties.whiteTeamID){
                    this.whiteTeam.incrementInvalidMove();
                    return false;
                }
            }
        }

        if (blackTeamIsPlaying) {
            this.blackTeam.incrementValidMove();
        }
        else {
            this.whiteTeam.incrementValidMove();
        }

        Square playerSquare = getSquareAtPosition(x0, y0);
        ChessPiece playerPiece = playerSquare.getPiece();
        playerSquare.updatePiece(null);

        Square enemySquare = getSquareAtPosition(x1, y1);
        ChessPiece enemyPiece = enemySquare.getPiece();
        enemySquare.updatePiece(playerPiece);

        playerPiece.updatePosition(x1, y1);

        if(enemyPiece == null) {
            this.playsWithoutCaptures += 1;
        } else {
            this.playsWithoutCaptures = 0;
            enemyPiece.cleanPosition();
            enemyPiece.capture();
            playerPiece.addCaptureLog(enemyPiece.getID());

            if(this.blackTeamIsPlaying){
                this.blackTeam.incrementCapture();
                this.whiteTeam.incrementSelfCapture();
            }
            else {
                this.blackTeam.incrementSelfCapture();
                this.whiteTeam.incrementCapture();
            }
        }

        this.blackTeamIsPlaying = !this.blackTeamIsPlaying;
        return true;
    }
    // expected format: { id, type, team, nickname, image }
    public String[] getSquareInfo(int x, int y) {
        if(this.notPlayable || !this.isValidBoardPosition(x, y)) {
            return null;
        }

        for (Square square : this.board) {
            if(square.equals(x, y)) {
                ChessPiece piece = square.getPiece();
                if(piece == null){ // means that has no piece on the requested square
                   return new String[]{ };
                }
               return piece.getInfo();
            }
        }

        return null;
    }
    // expected format: { id, type, team, nickname, status("capturado", "em jogo"), coordX, coordY }
    public String[] getPieceInfo(int ID) {
        if(this.notPlayable){
            return null;
        }

        ChessPiece piece = this.pieces.get(ID);
        return piece == null ? null : piece.getInfoWithLifeStatusAndPosition();
    }
    // expected string: "id | type | team | nickname | @(coordX, coordY)
    public String getPieceInfoAsString(int ID) {
        if(this.notPlayable){
            return null;
        }

        ChessPiece piece = this.pieces.get(ID);
        return piece == null ? "" : piece.toString();
    }
    public int getCurrentTeamID() {
        return this.blackTeamIsPlaying ? GameProperties.blackTeamID : GameProperties.whiteTeamID;
    }
    public boolean gameOver() {
        return this.initTie ||
                this.initWhiteTeamWin ||
                this.initBlackTeamWin ||
                !this.blackTeam.isAlive() ||
                !this.whiteTeam.isAlive() ||
                isGameTie() ||
                this.blackTeam.getCountPieces() == 0 ||
                this.whiteTeam.getCountPieces() == 0 ||
                this.pieces.isEmpty() ||
                this.board.isEmpty() ||
                (this.board.size() < 4 || this.board.size() > 8) ||
                this.boardDimension < 4;
    }
    public JPanel getAuthorsPanel() {
        // Return a JPanel with information about the authors of the game.
        JPanel panel = new JPanel();

        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        JLabel title = new JLabel("Créditos");
        title.setFont(new Font("Arial", Font.BOLD,24));
        title.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        panel.add(title);
        String[] authors = {"a22102606 - Marcos Gil", "a22103261 - Fábio Lopes"};
        for (String author : authors){
            JLabel label = new JLabel(author);
            label.setFont(new Font("Arial", Font.PLAIN,18));
            label.setHorizontalAlignment(JLabel.CENTER);
            label.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
            panel.add(label);
        }
        //return this.authorsPanel;
        return panel;
    }
    public ArrayList<String> getGameResults() {
        if(this.notPlayable || !this.gameOver()){
            return null;
        }

        String gameResultMessage = "";
        if(this.isGameTie() || this.initTie){
            gameResultMessage = GameProperties.tieMessage;
        } else if (this.blackTeam.isAlive() || this.initBlackTeamWin){
            gameResultMessage = GameProperties.winMessage + blackTeam.getName().toUpperCase();
        } else {
            gameResultMessage = GameProperties.winMessage + whiteTeam.getName().toUpperCase();
        }

        ArrayList<String> message = new ArrayList<>();
        message.add(GameProperties.gameTitle);
        message.add(GameProperties.resultMessage + gameResultMessage);
        message.add("---");
        message.addAll(Arrays.asList(this.blackTeam.getResult()));
        message.addAll(Arrays.asList(this.whiteTeam.getResult()));

        return message;
    }

    public void printPieces() {
        for (ChessPiece piece : this.pieces.values()) {
            System.out.println(Arrays.toString(piece.getInfo()));
        }
    }
}
