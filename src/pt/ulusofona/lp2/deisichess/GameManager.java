package pt.ulusofona.lp2.deisichess;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import javax.swing.JPanel;

public class GameManager {
    private ArrayList<Square> board;
    private HashMap<Integer, ChessPiece> chessPieces;
    private HashSet<String> validBoardPositions;
    private int moveCount = 0;
    private int moveCountWithoutDeads = 0;
    private boolean gameOver;
    JPanel authorsPanel;

    public GameManager(){
        this.board = new ArrayList<>();
        this.chessPieces = new HashMap<>();
        this.validBoardPositions = new HashSet<>();
        this.authorsPanel = new JPanel();
    }

    // region Methods
    public boolean isValidBoardPosition(int coordX, int coordY){
        String coord = coordX + "" + coordY;
        return this.validBoardPositions.contains(coord);
    }

    // TODO: implement
    public boolean validMove(int coordX0, int coordY0, int coordX1, int coordY1){
        return true;
    }
    // endregion

    // region API
    public boolean loadGame(File file) {
        if(file == null){
            return false;
        }

        final int PIECE_ROW_INFO_COLUMNS = 4;
        final String COLUMNS_SEPARATOR = ":";

        try{
            BufferedReader reader = new BufferedReader(new FileReader(file));

            int boardDimension = Integer.parseInt(reader.readLine().trim());
            int numberOfChessPieces = Integer.parseInt(reader.readLine().trim());

            // read pieces info
            for(int i = 0; i < numberOfChessPieces; i++){
                String row = reader.readLine();
                String[] columns = row.split(COLUMNS_SEPARATOR);

                if(columns.length != PIECE_ROW_INFO_COLUMNS){
                    return false;
                }

                int chessPieceID = Integer.parseInt(columns[0].trim()),
                    chessPieceType = Integer.parseInt(columns[1].trim()),
                    chessPieceTeam = Integer.parseInt(columns[2].trim());
                String chessPieceNickname = columns[3].trim();

                ChessPiece piece = new ChessPiece(chessPieceID, chessPieceType, chessPieceTeam, chessPieceNickname);
                this.chessPieces.put(chessPieceID, piece);
            }

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

                    if(pieceID != 0){
                        chessPiece = this.chessPieces.get(pieceID);
                        if(chessPiece != null) {
                            chessPiece.updateCoordinates(rowIndex, columnIndex);
                        }
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
        return board.size();
    }

    // TODO: review and finish
    public boolean move(int coordX0, int coordY0, int coordX1, int coordY1) {
        if(!this.isValidBoardPosition(coordX0, coordY0) || !this.isValidBoardPosition(coordX1, coordY1)){
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

        // if move válido incremente nr de jogadas
        this.moveCount++;
        
        return false;
    }

    // expected format: { id, type, team, nickname, image }
    public String[] getSquareInfo(int coordX, int coordy) {
        if(this.board.isEmpty() || !this.isValidBoardPosition(coordX, coordy)) {
            return null;
        }

        for (Square square : board) {
            if(square.equals(coordX, coordy)) {
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

    // TODO
    public JPanel getAuthorsPanel() {
        // Return a JPanel with information about the authors of the game.
        return authorsPanel;
    }

    // TODO
    public ArrayList<String> getGameResults() {
        /*
            A ArrayList de Strings contem
            [0] - "JOGO DE CRAZY CHESS"
            [1] - "Resultado: < RES >"
            ...
            Vareaveís para acessar:
            - RES
                - "VENCERAM AS BRANCAS"
                - "VENCERAM AS PRETAS"
                - "EMPATE"
            - NR CAPTURAS (de cada)
            - NR JOGADAS VALIDAS (de cada)
            - NR TENTATIVAS INVALIDAS (de cada)

            ```
            JOGO DE CRAZY CHESS
            Resultado: <RES>
            ---
            Equipa das Pretas
            <NR CAPTURAS>
            <NR JOGADAS VALIDAS>
            <NR TENTATIVAS INVALIDAS>
            Equipa das Brancas
            <NR CAPTURAS>
            <NR JOGADAS VALIDAS>
            <NR TENTATIVAS INVALIDAS>
            ```
        */


        ArrayList<String> results = new ArrayList<>();
         /*
        results.add("JOGO DE CRAZY CHESS");
        results.add("Resultado: " + RES);
        results.add("---");
        results.add("Equipa das Pretas");
        results.add(NR CAPTURAS);
        results.add(NR JOGADAS VALIDAS);
        results.add(NR TENTATIVAS INVALIDAS);
        results.add("Equipa das Brancas");
        results.add(NR CAPTURAS);
        results.add(NR JOGADAS VALIDAS);
        results.add(NR TENTATIVAS INVALIDAS);
        */

        return results;
    }
    // endregion

    public void printPieces() {
        for (ChessPiece piece : this.chessPieces.values()) {
            System.out.println(Arrays.toString(piece.getInfo()));
        }
    }
}
