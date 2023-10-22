package pt.ulusofona.lp2.deisichess;

import java.io.BufferedReader;
import java.io.File;
import java.util.ArrayList;

import java.io.FileReader;
import java.io.IOException;

import javax.swing.JPanel;

public class GameManager {

    private ArrayList<Square> board;
    private ArrayList<ChessPiece> pieces;
    private int currentTeamID;
    private boolean gameOver;
    JPanel authorsPanel;

    /*
        ------------
        MAIN METHODS
        ------------
    */
    public boolean loadGame(File file) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));

            // Check if the first line contains the board dimension (integer between 4 and 8)

            //int boardDimension = Integer.parseInt(reader.readLine());

            //read for dimension from fist line and ignore spaces
            int boardDimension = Integer.parseInt(reader.readLine().replaceAll("\\s+", ""));

            if (boardDimension < 4 || boardDimension > 8) {
                System.err.println("Invalid board dimension.");
                return false;
            }

            // Check if the second line contains the number of players
            int numberOfPieces = Integer.parseInt(reader.readLine());

            // Check if the following lines have the correct format for piece information (id:type:team:nickname)
            pieces = new ArrayList<ChessPiece>();

            for (int i = 0; i < numberOfPieces; i++) {
                String line = reader.readLine();
                String[] pieceData = line.split(":");
                if (pieceData.length != 4) {
                    System.err.println("Invalid piece information format: " + line);
                    return false;
                }
                int id = Integer.parseInt(pieceData[0]);
                int type = Integer.parseInt(pieceData[1]);
                int team = Integer.parseInt(pieceData[2]);
                String nickname = pieceData[3];
                ChessPiece piece = new ChessPiece(id, team, nickname, type);
                pieces.add(piece); // Add the piece to the list of pieces
            }

            // Check if the board representation matches the specified dimensions in the first line
            board = new ArrayList<Square>();

            for (int i = 0; i < boardDimension; i++) {
                String line = reader.readLine();
                String[] squareData = line.split(":");
                if (squareData.length != boardDimension) {
                    System.err.println("Invalid board representation format: " + line);
                    return false;
                }
                for (int j = 0; j < boardDimension; j++) {
                    int id = Integer.parseInt(squareData[j]);
                    //if even -> white  |  if odd -> black
                    boolean white = (id % 2 == 0);
                    Square square = new Square(id, i, j, white);
                    board.add(square); // Add the square to the list of squares
                }
            }

            reader.close();

            // Was able to load the game successfully
            return true;
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            return false;
        } catch (NumberFormatException e) {
            System.err.println("Invalid numeric format in the file.");
            return false;
        }
    }

    public int getBoardSize() {
        return board.size();
    }

    public boolean move(int x0, int y0, int x1, int y1) {
        //A interface gráfica carrega contem elementos:
        //  - De: (a,b)
        //  - Para: (c,d)
        //onde quando clicamos o botão **mover**, o programa chama a nossa classe
        // GameManager.move(a,b,c,d)

        return false;
    }

    public String[] getSquareInfo(int x, int y) {
        // id | tipo | equipa | alcunha | png  |
        return new String[]{"", ""};
    }

    public String[] getPieceInfo(int ID) {
        /*
            ID -> getPieceInfo() -> [id , tipo , equipa , alcunha , estado , x , y ]
            Estado é uma string que pode conter os valores:
                - em jogo
                - capturado
         */
        return new String[]{"", ""};
    }

    public String getPieceInfoAsString(int ID) {
        //Recebe ID da peça e retorna uma string com o seguinte formato:
        //id | tipo | equipa | alcunha @(x,y)
        //Exemplo: 1 | 0 | 0 | Chefe @(1,0)
        return "";
    }

    public int getCurrentTeamID() {
        return currentTeamID;
    }

    public boolean gameOver() {
        //Ten que analisar o tabuleiro e verificar se o jogo terminou
        return gameOver;
    }

    public JPanel getAuthorsPanel() {
        // Return a JPanel with information about the authors of the game.
        return authorsPanel;
    }

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

    /**
     * ----------------
     * ----------------
     * ----------------
     * ----------------
     * ----------------
     * AUXILIARY METHODS
     * ----------------
     * ----------------
     * ----------------
     * ----------------
     */

    public ArrayList<Square> getBoard() {
        return board;
    }

    public ArrayList<ChessPiece> getPieces() {
        return pieces;
    }

    // print the pieces
    public void printPieces() {
        for (ChessPiece piece : pieces) {
            System.out.println("Piece " + piece.getId() + " - Team: " + piece.getTeam() + " Type: " + piece.getType()  + " Nickname: " + piece.getNickname());
        }
    }

}
