package pt.ulusofona.lp2.deisichess;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class GameManager {
    private boolean tieFromFile;
    private int dimension;
    private int movesWithoutDefeats;
    private Team blackTeam;
    private Team whiteTeam;
    private boolean blackTeamRound;
    //private ArrayList<Square> board;
    //private ArrayList<String> boardPositions;
    private Board board;
    private JPanel authorsPanel;

    public GameManager(){
        this.reset();
    }
    private void reset(){
        this.tieFromFile = false;
        this.dimension = 0;
        this.movesWithoutDefeats = 0;
        this.blackTeam = new Team(GameStaticData.BLACK_TEAM_ID, GameStaticData.BLACK_TEAM_NAME);
        this.whiteTeam = new Team(GameStaticData.WHITE_TEAM_ID, GameStaticData.WHITE_TEAM_NAME);
        this.blackTeamRound = true;
        this.board = new Board();
    }

    private Square getSquareByPosition(int x, int y){
        for (Square square : squares) {
            if(square.equals(x, y)){
                return square;
            }
        }

        return null; // never happens
    }
    private boolean validBoardPosition(int x, int y){
        return this.boardPositions.contains(x + "" + y);
    }
    private boolean isTie(){
        // change getCountNonDefeatedPieces to countNonDefeatedPieces
        return (this.movesWithoutDefeats >= GameStaticData.TIE_RULE && // Game over from TIE
                (this.blackTeam.getCountDefeated() > 0 || this.whiteTeam.getCountDefeated() > 0)) ||
                 (this.blackTeam.getCountNonDefeated() == 1 && this.whiteTeam.getCountNonDefeated() == 1);
    }
    private void evaluateTieFromFile(){
        this.tieFromFile = !this.blackTeam.isDefeated() &&
                           !this.whiteTeam.isDefeated() &&
                           this.blackTeam.getCountNonDefeated() == 1 &&
                           this.whiteTeam.getCountNonDefeated() == 1;
    }

    public boolean loadGame(File file){
        if(file == null){
            return false;
        }

        this.reset();

        try{
            BufferedReader reader = new BufferedReader(new FileReader(file));

            this.dimension = Integer.parseInt(reader.readLine().trim());
            final int nrPieces = Integer.parseInt(reader.readLine().trim());

            // read pieces data
            for(int i = 0; i < nrPieces; i++){
                String pieceRow = reader.readLine();
                String[] pieceData = pieceRow.split(GameStaticData.FILE_CONTENT_SEPARATOR);

                int pieceId = Integer.parseInt(pieceData[0].trim()),
                    pieceType = Integer.parseInt(pieceData[1].trim()),
                    pieceTeamId = Integer.parseInt(pieceData[2].trim());
                String pieceNickname = pieceData[3].trim();

                ChessPiece piece = new ChessPiece(pieceId, pieceType, pieceNickname);
                if(pieceTeamId == GameStaticData.BLACK_TEAM_ID){
                    piece.updateImage(GameStaticData.KING_BLACK_TEAM);
                    this.blackTeam.addPiece(piece);
                } else if(pieceTeamId == GameStaticData.WHITE_TEAM_ID){
                    piece.updateImage(GameStaticData.KING_WHITE_TEAM);
                    this.whiteTeam.addPiece(piece);
                } else { // wrong team id
                    return false;
                }
            }

            // read map data
            for(int rowY = 0; rowY < this.dimension; rowY++) {
                String row = reader.readLine().trim();
                String[] rowData = row.split(GameStaticData.FILE_CONTENT_SEPARATOR);

                for(int colX = 0; colX < this.dimension; colX++) {
                    // create squares
                    // set pieces on their positions
                    // record allowed positions
                    Square square = new Square(colX, rowY);
                    this.squares.add(square);
                    this.boardPositions.add(colX + "" + rowY);

                    int pieceId = Integer.parseInt(rowData[colX]);
                    if(pieceId != 0){
                        ChessPiece piece = this.blackTeam.getPieceById(pieceId);
                        if(piece == null){
                            piece = this.whiteTeam.getPieceById(pieceId);
                        }
                        piece.revive();
                        piece.move(square);
                    }
                }
            }

            this.evaluateTieFromFile();
            return true;
        } catch (IOException e) {
            return false;
        }
    }
    public int getBoardSize(){ return this.dimension; }
    public boolean move(int x0, int y0, int x1, int y1){
        if(!this.validBoardPosition(x0, y0) || // doesn't count as an invalid position
           !this.validBoardPosition(x1, y1) || // because there's no way from the UI to do so
           this.gameOver()){
                return false;
        }

        // TODO: refactor the next 2 blocks, and extract to a new function
        // invalid move: same position
        if(x0 == x1 && y0 == y1){
            if(this.blackTeamRound){
                this.blackTeam.incrementInvalidMove();
            }  else {
               this.whiteTeam.incrementInvalidMove();
            }
            return false;
        }

        // TODO remove on part 2
        // invalid move: move King restriction
        if(!(Math.abs(x0 - x1) <= 1 && Math.abs(y0 - y1) <= 1)){
            if(this.blackTeamRound){
                this.blackTeam.incrementInvalidMove();
            }  else {
                this.whiteTeam.incrementInvalidMove();
            }
            return false;
        }

        ChessPiece pieceToMove;

        if(this.blackTeamRound){
            pieceToMove = this.blackTeam.getPieceByPosition(x0, y0);
            // invalid move: this piece doesn't belong to Black team
            if(pieceToMove == null){
                this.blackTeam.incrementInvalidMove();
                return false;
            }
        }
        else {
            pieceToMove = this.whiteTeam.getPieceByPosition(x0, y0);
            // invalid move: this piece doesn't belong to White team
            if(pieceToMove == null){
                this.whiteTeam.incrementInvalidMove();
                return false;
            }
        }

        ChessPiece pieceOnDestiny;

        if(this.blackTeamRound){ // fetch destiny to see if it has enemy piece, if so kill it !
            pieceOnDestiny = this.whiteTeam.getPieceByPosition(x1, y1);
            if(pieceOnDestiny != null){
                pieceOnDestiny.defeatMe();
                this.blackTeam.incrementKills();
                this.movesWithoutDefeats = 0;
            } else {
                this.movesWithoutDefeats++;
            }
        } else {
            pieceOnDestiny = this.blackTeam.getPieceByPosition(x1, y1);
            if(pieceOnDestiny != null){
                pieceOnDestiny.defeatMe();
                this.whiteTeam.incrementKills();
                this.movesWithoutDefeats = 0;
            } else {
                this.movesWithoutDefeats++;
            }
        }

        // update square position
        Square destinySquare = this.getSquareByPosition(x1, y1);
        pieceToMove.move(destinySquare);

        // increment valid moves
        if(this.blackTeamRound){
            this.blackTeam.incrementValidMove();
        } else {
            this.whiteTeam.incrementValidMove();
        }

        // toggle team round
        this.blackTeamRound = !this.blackTeamRound;
        return true;
    }
    public String[] getSquareInfo(int x, int y) {
        if(!validBoardPosition(x, y)){
            return null;
        }

        int teamId = GameStaticData.BLACK_TEAM_ID;
        ChessPiece piece = this.blackTeam.getPieceByPosition(x,y);

        if(piece == null){
            teamId = GameStaticData.WHITE_TEAM_ID;
            piece = this.whiteTeam.getPieceByPosition(x, y);
        }

        if(piece == null) {
            return new String[]{};
        }

        return new String[]{
            piece.getId() + "",
            piece.getType() + "",
            teamId + "",
            piece.getNickname(),
            piece.getImage()
        };
    }
    public String[] getPieceInfo(int id){
        int teamId = GameStaticData.BLACK_TEAM_ID;
        ChessPiece piece = this.blackTeam.getPieceById(id);

        if(piece == null){
            teamId = GameStaticData.WHITE_TEAM_ID;
            piece = this.whiteTeam.getPieceById(id);
        }

        if(piece == null){
            return null;
        }

        Square position = piece.getPosition();

        return new String[]{
            piece.getId() + "",
            piece.getType() + "",
            teamId + "",
            piece.getNickname(),
            piece.isDefeated() ? GameStaticData.DEFEATED_LABEL : GameStaticData.NOT_DEFEATED_LABEL,
            position != null ? position.getX() + "" : "",
            position != null ? position.getY() + "" : ""
        };
    }
    public String getPieceInfoAsString(int id) {
        int teamId = GameStaticData.BLACK_TEAM_ID;
        ChessPiece piece = this.blackTeam.getPieceById(id);

        if(piece == null){
            teamId = GameStaticData.WHITE_TEAM_ID;
            piece = this.whiteTeam.getPieceById(id);
        }

        if(piece == null){
            return "";
        }

        Square position = piece.getPosition();

        return piece.getId() + " | " +
               piece.getType() + " | " +
               teamId + " | " +
               piece.getNickname() + " @ (" +
                (position == null ?
                    GameStaticData.NO_POSITION_LABEL :
                        position.getX() + ", " + position.getY()) + ")";
    }
    public int getCurrentTeamID() {
        return this.blackTeamRound ? GameStaticData.BLACK_TEAM_ID : GameStaticData.WHITE_TEAM_ID;
    }
    public boolean gameOver() {
        if(this.tieFromFile || this.isTie()){
            return true;
        }

        return this.blackTeam.isDefeated() || this.whiteTeam.isDefeated();
    }
    public JPanel getAuthorsPanel(){
        this.authorsPanel = new JPanel();
        this.authorsPanel.setLayout(new BoxLayout(this.authorsPanel,BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Créditos");
        title.setFont(new Font("Arial", Font.BOLD,24));
        title.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        this.authorsPanel.add(title);

        String[] authors = {"a22102606 - Marcos Gil", "a22103261 - Fábio Lopes"};
        for (String author : authors){
            JLabel label = new JLabel(author);
            label.setFont(new Font("Arial", Font.PLAIN,18));
            label.setHorizontalAlignment(JLabel.CENTER);
            label.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
            this.authorsPanel.add(label);
        }

        return this.authorsPanel;
    }
    public ArrayList<String> getGameResults(){
        String resultMessage = "";

        if(this.tieFromFile || this.isTie()){
            resultMessage = GameStaticData.RESULT_TIE_MESSAGE;
        } else {
            resultMessage = GameStaticData.RESULT_WIN_MESSAGE +
                (this.blackTeam.isDefeated() ?
                    this.whiteTeam.getName().toUpperCase() :
                        this.blackTeam.getName().toUpperCase());
        }

        ArrayList<String> message = new ArrayList<>();
        message.add(GameStaticData.RESULT_GAME_TITLE);
        message.add(GameStaticData.RESULT_LABEL_TITLE + resultMessage);
        message.add("---");
        message.addAll(Arrays.asList(this.blackTeam.getScore()));
        message.addAll(Arrays.asList(this.whiteTeam.getScore()));

        return message;
    }
}
