package pt.ulusofona.lp2.deisichess;

import pt.ulusofona.lp2.deisichess.movements.BaseMovement;
import pt.ulusofona.lp2.deisichess.pieces.BasePiece;
import pt.ulusofona.lp2.deisichess.pieces.HomerSimpsonPiece;
import pt.ulusofona.lp2.deisichess.pieces.JokerPiece;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

public class GameManager {
    private List<List<Square>> board;
    private Team blackTeam;
    private Team whiteTeam;
    private int countValidRounds;
    private int movesWithoutDefeats;
    private List<GameManager> backup;
    private JPanel authorsPanel;
    private boolean tieFromFile;

    public GameManager() {
        this.init();
    }
    public void init(){
        this.board = new ArrayList<>();
        this.backup = new ArrayList<>();
        this.blackTeam = new Team(Team.BLACK_TEAM_ID, Team.BLACK_TEAM_NAME, true);
        this.whiteTeam = new Team(Team.WHITE_TEAM_ID, Team.WHITE_TEAM_NAME, false);
        this.countValidRounds = 0;
        this.movesWithoutDefeats = 0;
    }
    public void reInit(GameManager backup){
        this.board = backup.board;
        this.blackTeam = backup.blackTeam;
        this.whiteTeam = backup.whiteTeam;
        this.countValidRounds = backup.countValidRounds;
        this.movesWithoutDefeats = backup.movesWithoutDefeats;
        this.backup = backup.backup;
        this.authorsPanel = backup.authorsPanel;
        this.tieFromFile = backup.tieFromFile;
    }
    public void buildBoard(int dimension){
        for (int rowIndex = 0; rowIndex < dimension; rowIndex++) {
            List<Square> row = new ArrayList<>();
            for(int columnIndex = 0; columnIndex < dimension; columnIndex++) {
                row.add(new Square(columnIndex, rowIndex));
            }

            this.board.add(row);
        }
    }

    @Override
    protected GameManager clone() {
        GameManager clone = new GameManager();
        clone.board = this.board;
        clone.blackTeam = this.whiteTeam;
        clone.whiteTeam = this.whiteTeam;
        clone.countValidRounds = this.countValidRounds;
        clone.authorsPanel = this.authorsPanel;
        clone.tieFromFile = this.tieFromFile;
        return clone;
    }
    private boolean isTie(){
        return (this.movesWithoutDefeats >= 10 && // Game over from TIE
               (this.blackTeam.getCountDefeated() > 0 || this.whiteTeam.getCountDefeated() > 0)) ||
               (this.blackTeam.getCountNonDefeated() == 1 && this.whiteTeam.getCountNonDefeated() == 1);
    }
    private void evaluateTieFromFile(){
        this.tieFromFile = !this.blackTeam.getIsDefeated() &&
                           !this.whiteTeam.getIsDefeated() &&
                            this.blackTeam.getCountNonDefeated() == 1 &&
                            this.whiteTeam.getCountNonDefeated() == 1;
    }

    public void loadGame(File file) throws InvalidGameInputException, IOException {
        if(file == null){
            return;
        }

        this.init();

        BufferedReader reader = new BufferedReader(new FileReader(file));

        /* set board */
        int boardDimension = Integer.parseInt(reader.readLine().trim());
        this.buildBoard(boardDimension);

        /* read and load pieces info */
        HashMap<Integer, BasePiece> loadedPieces = new HashMap<>();
        int nrPieces = Integer.parseInt(reader.readLine().trim());
        for(int i = 0; i < nrPieces; i++){
            /* piece info */
            String pieceInfo = reader.readLine();
            String[] pieceData = pieceInfo.split(":");

            /* handle invalid data format exceptions */
            if(pieceData.length < 4){
                throw new InvalidGameInputException(i, InvalidGameInputException.getLessDataErrorDescription(
                        4, pieceData.length));
            }
            else if (pieceData.length > 4){
                throw new InvalidGameInputException(i, InvalidGameInputException.getMoreDataErrorDescription(
                        4, pieceData.length));
            }

            /* create piece and set piece on team */
            int id = Integer.parseInt(pieceData[0].trim());
            int typeId = Integer.parseInt(pieceData[1].trim());
            int teamId = Integer.parseInt(pieceData[2].trim());
            String nickname = pieceData[3].trim();

            Team team = teamId == Team.BLACK_TEAM_ID ? this.blackTeam : this.whiteTeam;
            BasePiece newPiece = BasePiece.create(id, typeId, nickname, team);
            team.addPiece(newPiece);
            loadedPieces.put(id, newPiece);
        }

        /* read and load board positions */
        final int BOARD_SIZE = this.board.size();
        for (int row = 0; row < BOARD_SIZE; row++) {
            /* board info */
            String boardRowInfo = reader.readLine();
            String[] boardRowData = boardRowInfo.split(":");

            /* handle invalid data format exceptions */
            if(boardRowData.length < BOARD_SIZE){
                throw new InvalidGameInputException(row, InvalidGameInputException.getLessDataErrorDescription(
                        BOARD_SIZE, boardRowData.length));
            }
            else if (boardRowData.length > BOARD_SIZE){
                throw new InvalidGameInputException(row, InvalidGameInputException.getMoreDataErrorDescription(
                        BOARD_SIZE, boardRowData.length));
            }

            for (int column = 0; column < boardRowData.length; column++){
                int boardSquarePieceId = Integer.parseInt(boardRowData[column].trim());
                if(boardSquarePieceId != 0){
                    Square boardSquare = this.board.get(row).get(column);

                    BasePiece piece = loadedPieces.get(boardSquarePieceId);
                    piece.revive();
                    piece.setSquare(boardSquare);
                    boardSquare.setPiece(piece);
                }
            }
        }
    }
    public int getBoardSize(){ return this.board.size(); }
    public boolean move(int x0, int y0, int x1, int y1){
        /* doesn't count as an invalid position,
           because there's no way from the UI to do so */
        if(!BaseMovement.isWithinBounds(board, x0, y0) || //
           !BaseMovement.isWithinBounds(board, x1, y1)) { //
            return false;
        }

        Team playingTeam =
                this.blackTeam.getIsPlaying() ?
                        this.blackTeam :
                        this.whiteTeam;

        // (invalid move): same position in both coordinates
        if(x0 == x1 && y0 == y1){
            playingTeam.incrementInvalidMove();
            this.backup.add(this.clone());
            return false;
        }

        /* validate/simulate piece move rules (WITHOUT MOVING)
           i)  validate piece move range and type of move
           ii) validate overlapping */
        Square currentSquare = this.board.get(y0).get(x0);
        BasePiece currentPiece = currentSquare.getPiece();

        // (invalid move): current piece isn't from this playing team
        if(currentPiece.getTeamId() != playingTeam.getId()){
            playingTeam.incrementInvalidMove();
            this.backup.add(this.clone());
            return false;
        }

        boolean validMoveRule = currentPiece.validMoveRules(board, x1, y1);
        // (invalid move): current piece rules not respected
        if(!validMoveRule){
            playingTeam.incrementInvalidMove();
            this.backup.add(this.clone());
            return false;
        }

        Square destinySquare = this.board.get(y1).get(x1);
        BasePiece destinyPiece = destinySquare.getPiece();
        if(destinyPiece != null){
            // (invalid move): trying to "hunt" destiny piece from the same team
            if(currentPiece.getTeamId() == destinyPiece.getTeamId()){
                playingTeam.incrementInvalidMove();
                this.backup.add(this.clone());
                return false;
            }

            // hunt and feed the statistics
            destinyPiece.defeatMe();
            destinyPiece.removeSquare();
            destinySquare.removePiece();
            playingTeam.incrementKillsAndScore(destinyPiece.getValue());
            this.movesWithoutDefeats = 0;
        } else { this.movesWithoutDefeats++; }

       /* update piece position
          i)  set current piece with destiny square
          ii) set destiny square with current piece*/
        currentSquare.removePiece();
        currentPiece.setSquare(destinySquare);
        destinySquare.setPiece(currentPiece);

        /* ensure both jokers clone next piece */
        JokerPiece whiteTeamJoker = whiteTeam.getJoker();
        JokerPiece blackTeamJoker = blackTeam.getJoker();
        if(whiteTeamJoker != null){
            whiteTeamJoker.cloneNext();
        }
        if(blackTeamJoker != null){
            blackTeamJoker.cloneNext();
        }

        /* ensure homer sleeping status */
        HomerSimpsonPiece whiteTeamHomerSimpson = whiteTeam.getHomer();
        HomerSimpsonPiece blackTeamHomerSimpson = blackTeam.getHomer();
        if(whiteTeamHomerSimpson != null){
            whiteTeamHomerSimpson.setSleeping(this.countValidRounds);
        }
        if(blackTeamHomerSimpson != null){
            blackTeamHomerSimpson.setSleeping(this.countValidRounds);
        }

        this.countValidRounds++;
        playingTeam.incrementValidMove();
        blackTeam.toggleIsPlaying();
        whiteTeam.toggleIsPlaying();

        this.backup.add(this.clone());
        return true;
    }
    public String[] getSquareInfo(int x, int y) {
        boolean coordinatesWithinBounds = BaseMovement.isWithinBounds(this.board, x, y);
        if(!coordinatesWithinBounds){
            return null;
        }

        Square square = board.get(y).get(x);

        BasePiece piece = square.getPiece();
        if (piece == null) {
            return new String[]{};
        }

        /* return the information about the piece */
        return new String[]{
                String.valueOf(piece.getId()),
                piece.getTypeName(),
                String.valueOf(piece.getTeamId()),
                piece.getNickname(),
                piece.getImage()
        };
    }
    public String[] getPieceInfo(int id){
        /* try fetch piece from id on both teams */
        BasePiece piece = this.blackTeam.getPieceById(id);
        if(piece == null){
            piece = this.whiteTeam.getPieceById(id);
        }

        if(piece == null) {
            return null;
        }

        Square square = piece.getSquare();

        return new String[]{
                piece.getId() + "",
                piece.getTypeName(),
                piece.getTeamId() + "",
                piece.getNickname(),
                piece.getIsDefeated() ? "capturado" : "em jogo",
                square != null ? square.getX() + "" : "",
                square != null ? square.getY() + "" : ""
        };
    }
    public String getPieceInfoAsString(int id) {
        /* try fetch piece from id on both teams */
        BasePiece piece = this.blackTeam.getPieceById(id);
        if(piece == null){
            piece = this.whiteTeam.getPieceById(id);
        }

        if(piece == null) {
            return "";
        }

        return piece.printInfo();
    }
    public int getCurrentTeamID() {
        return this.blackTeam.getIsPlaying() ? this.blackTeam.getId() : this.whiteTeam.getId();
    }
    public boolean gameOver() {
        if(this.tieFromFile || this.isTie()){
            return true;
        }

        return this.blackTeam.getIsDefeated() || this.whiteTeam.getIsDefeated();
    }
    public ArrayList<String> getGameResults(){
        String resultMessage = "";

        if(this.tieFromFile || this.isTie()){
            resultMessage = GameStaticData.RESULT_TIE_MESSAGE;
        } else {
            resultMessage = GameStaticData.RESULT_WIN_MESSAGE +
                    (this.blackTeam.getIsDefeated() ?
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
    public void saveGame(File file) throws IOException{
        //TODO: implement
    }
    public void undo(){
        if(this.backup.isEmpty()){
            return;
        }
        int lastBackupIndex = backup.size() - 1;
        GameManager lastBackupGameClone = backup.get(lastBackupIndex).clone();
        this.reInit(lastBackupGameClone);
        this.backup.remove(lastBackupIndex);
    }
    List<Comparable> getHints(int x, int y){
        //TODO: implement
        return null;
    }
    public Map<String, String> customizeBoard() {
        return new HashMap<>();
    }
}
