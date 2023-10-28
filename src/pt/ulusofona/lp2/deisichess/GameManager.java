package pt.ulusofona.lp2.deisichess;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import javax.swing.*;

public class GameManager {
    private final int BLACK_TEAM_ID = 0;
    private final String BLACK_TEAM_NAME = "Pretas";
    private final int WHITE_TEAM_ID = 1;
    private final String WHITE_TEAM_NAME = "Brancas";


    private ArrayList<Square> board;
    private HashMap<Integer, ChessPiece> chessPieces;
    private Team blackTeam;
    private Team whiteTeam;
    private HashSet<String> validBoardPositions;
    private int moveCount;
    private int moveCountWithoutDeads;
    private boolean gameOver;
    JPanel authorsPanel;
    private int boardDimension;
    private int numberOfChessPieces;

    public GameManager(){
        this.init();
    }

    // region Methods
    private void init(){
        this.board = new ArrayList<>();
        this.chessPieces = new HashMap<>();
        this.blackTeam = new Team(BLACK_TEAM_ID, BLACK_TEAM_NAME);
        this.whiteTeam = new Team(WHITE_TEAM_ID, WHITE_TEAM_NAME);
        this.validBoardPositions = new HashSet<>();
        this.moveCount = 0;
        this.moveCountWithoutDeads = 0;
        this.gameOver = false;
        this.authorsPanel = new JPanel();
    }

    private boolean isValidBoardPosition(int coordX, int coordY){
        String coord = coordX + "" + coordY;
        return this.validBoardPositions.contains(coord);
    }

    private boolean addPieceTeam(int teamID, int pieceID, ChessPiece piece){
        switch (teamID){
            case BLACK_TEAM_ID:
                return this.blackTeam.addPiece(pieceID, piece);
            case WHITE_TEAM_ID:
                return this.whiteTeam.addPiece(pieceID, piece);
            default:
                return false;
        }
    }

    // TODO: Review if no logic errors
    private boolean validMove(int coordX0, int coordY0, int coordX1, int coordY1){
        // No caso do Rei, este pode-se mover 1 square em todas as direções horizontal, vertical e diagonal
        // Logo o valor absoluto da subtração entre os |x1 - x0| e |y1 - y0| tem que ser menor ou igual a 1
        //Também não se pode mover para um square em que esteja presente algum elemento da proproa
        ChessPiece evalCurrentSquarePiece = getPieceAtPosition(coordX0,coordY0);

        if (evalCurrentSquarePiece == null){
            //Nem existe nenhuma peca do square atual retorna logo false
            return false;
        }
        return Math.abs(coordX0 - coordX1) <= 1 &&
                Math.abs(coordY0 - coordY1) <= 1 &&
                getPieceAtPosition(coordX1,coordY1).getTeamID() != evalCurrentSquarePiece.getTeamID()
                ;
    }

    public ChessPiece getPieceAtPosition(int x, int y) {
        for (Square square : board) {
            if (square.equals(x, y)) {
                // Retorna peça presente ou null caso nao exista nenhuma no square
                return square.getPiece();
            }
        }
        // null se nao houver match com o square
        return null;
    }
    public Square getSquareAtPosition(int x, int y) {
        for (Square square : board) {
            if (square.equals(x, y)) {
                return square;
            }
        }
        return null;
    }

    public int getBoardDimension() {
        return boardDimension;
    }

    public int getNumberOfChessPieces() {
        return numberOfChessPieces;
    }

    // endregion

    // region API
    // TODO: validate board and number pieces
    public boolean loadGame(File file) {
        this.init();

        if(file == null){
            return false;
        }

        final int PIECE_ROW_INFO_COLUMNS = 4;
        final String COLUMNS_SEPARATOR = ":";

        try{
            BufferedReader reader = new BufferedReader(new FileReader(file));

            this.boardDimension = Integer.parseInt(reader.readLine().trim());
            this.numberOfChessPieces = Integer.parseInt(reader.readLine().trim());

            // read pieces info
            for(int i = 0; i < numberOfChessPieces; i++){
                String row = reader.readLine();
                String[] columns = row.split(COLUMNS_SEPARATOR);

                if(columns.length != PIECE_ROW_INFO_COLUMNS){
                    return false;
                }

                // TODO VALIDA DATA
                int chessPieceID = Integer.parseInt(columns[0].trim()),
                    chessPieceType = Integer.parseInt(columns[1].trim()),
                    chessPieceTeam = Integer.parseInt(columns[2].trim());
                String chessPieceNickname = columns[3].trim();

                ChessPiece piece = new ChessPiece(chessPieceID, chessPieceType, chessPieceTeam, chessPieceNickname);
                boolean result = this.addPieceTeam(chessPieceTeam, chessPieceID, piece);
                if(!result){
                    return false;
                }
                this.chessPieces.put(chessPieceID, piece);
            }

            // to ensure we don't load repeated pieces
            HashSet<Integer> readPieceIDS = new HashSet<>();

            // read board squares info
            for(int rowIndex = 0; rowIndex < boardDimension; rowIndex++){
                String row = reader.readLine().trim();
                String[] columns = row.split(COLUMNS_SEPARATOR);

                if(columns.length != boardDimension){
                    return false;
                }

                for(int columnIndex = 0; columnIndex < boardDimension; columnIndex++){
                    // even -> white
                    // odd -> black
                    boolean isWhiteSquare = columnIndex % 2 == 0;

                    int pieceID = Integer.parseInt(columns[columnIndex]);
                    ChessPiece chessPiece = null;

                    // break if has repeated pieceID
                    if(readPieceIDS.contains(pieceID)){
                        return false;
                    }

                    if(pieceID != 0){
                        chessPiece = this.chessPieces.get(pieceID);

                        // to ensure pieceID has a ChessPiece object
                        if(chessPiece == null){
                            return false;
                        }

                        readPieceIDS.add(pieceID);
                        chessPiece.updateCoordinates(rowIndex, columnIndex);
                    }

                    this.board.add(new Square(rowIndex, columnIndex, isWhiteSquare, chessPiece));
                    this.validBoardPositions.add(rowIndex + "" + columnIndex);
                }
            }

            reader.close();
            return true;
        } catch (IOException e) {
            //System.err.println("Error reading file: " + e.getMessage());
        }

        return false;
    }

    public int getBoardSize() {
        return this.board.size();
    }

    // TODO: review and finish
    // INCOMPLETE !!!! updateBoard() nao existe.
    // TODO: Necessario fazer updateBoard() talvez nao necessite de ser um metodo
    // TODO: Rever ultima proposta
    public boolean move(int coordX0, int coordY0, int coordX1, int coordY1) {
        if(this.gameOver || !this.isValidBoardPosition(coordX0, coordY0) || !this.isValidBoardPosition(coordX1, coordY1)){
            return false;
        }
        if(!this.validMove(coordX0, coordY0, coordX1, coordY1)){
            return false;
        }

        //A interface gráfica carrega contem elementos:
        //  - De: (a,b)
        //  - Para: (c,d)
        //onde quando clicamos o botão **mover**, o programa chama a nossa classe
        // GameManager.move(a,b,c,d)

        ChessPiece piece = getPieceAtPosition(coordX0, coordY0);

        if(piece == null){
            return false;
        }

        // Get the destination square
        Square destinationSquare = getSquareAtPosition(coordX1, coordY1);

        // Check if the destination square is occupied
        if (destinationSquare.getPiece() != null){

            ChessPiece pieceAtDestinationSquare = destinationSquare.getPiece();

            // If the destination square is occupied by a piece from the same team, the move is not valid
            if (pieceAtDestinationSquare.getTeamID() == piece.getTeamID()) {
                return false;
            }
            // If the destination square is occupied by a piece from the opposing team, capture it
            else {
                moveCountWithoutDeads = 0; // Reset counter for amout of rounds with no captures/kills
                pieceAtDestinationSquare.kill();
                if(pieceAtDestinationSquare.getTeamID() == BLACK_TEAM_ID){
                    blackTeam.addDead();
                    whiteTeam.addKill();
                } else {
                    whiteTeam.addDead();
                    blackTeam.addKill();
                }
            }
        }
        else {
            if (blackTeam.getCountKills() != 0 || whiteTeam.getCountKills() != 0){
                //Ele diz no video que a regra dos 10 moves (empate) so é valida se já houve uma captura durante o jogo
                //Que foi sucedida por 10 moves sem capturas
                moveCountWithoutDeads++;
            }
        }

        // Move the piece to the new position
        piece.updateCoordinates(coordX1, coordY1);

        // Update the board
        //updateBoard();

        // if move válido incremente nr de jogadas
        this.moveCount++;

        return true;
        //return false;
    }

    // expected format: { id, type, team, nickname, image }
    public String[] getSquareInfo(int coordX, int coordY) {
        if(this.board.isEmpty() || !this.isValidBoardPosition(coordX, coordY)) {
            return null;
        }

        for (Square square : this.board) {
            if(square.equals(coordX, coordY)) {
                    ChessPiece piece = square.getPiece();
                if(piece == null){ // means that has no piece on the requested square
                    break;
                }
               return piece.getInfo();
            }
        }

        return null;
    }

    // expected format: { id, type, team, status("capturado", "em jogo"), coordX, coordY }
    public String[] getPieceInfo(int ID) {
        ChessPiece piece = this.chessPieces.get(ID);
        return piece == null ? null : piece.getInfoWithStatus();
    }

    // expected string: "id | type | team | nickname | @(coordX, coordY)
    public String getPieceInfoAsString(int ID) {
        ChessPiece piece = this.chessPieces.get(ID);
        return piece == null ? "" : piece.toString();
    }

    public int getCurrentTeamID() {
        return this.moveCount % 2 == 0 ? 0 : 1;
    }

    public boolean gameOver() {
        return this.gameOver || this.moveCountWithoutDeads > 10;
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

    // TODO: only the winner is missing
    public ArrayList<String> getGameResults() {
        ArrayList<String> resultMessage = new ArrayList<>();
        resultMessage.add("JOGO DE CRAZY CHESS");
        resultMessage.add("Resultado: ");
        resultMessage.add("---");

        for (int teamID = 0; teamID <= 1; teamID++){
            String[] teamResult =
                    teamID == BLACK_TEAM_ID ?
                                this.blackTeam.getResult() :
                                    this.whiteTeam.getResult();

            for(int resultLine = 0; resultLine < 4; resultLine++){
                resultMessage.add(teamResult[resultLine]);
            }
        }

        return resultMessage;
    }
    // endregion

    public void printPieces() {
        for (ChessPiece piece : this.chessPieces.values()) {
            System.out.println(Arrays.toString(piece.getInfo()));
        }
    }
}
