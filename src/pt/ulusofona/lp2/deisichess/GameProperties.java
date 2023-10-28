package pt.ulusofona.lp2.deisichess;

public class GameProperties {
    public static int blackTeamID = 0;
    public static int whiteTeamID = 1;
    public static String blackTeamName = "Pretas";
    public static String whiteTeamName = "Brancas";
    public static String fileColumnSeparator = ":";
    public static int fileInfoNrColumns = 4;
    public static int tieMoveRule = 10;
    public static int minBoardDimension = 3;
    public static int minChessPieces = 2;
    public static String gameTitle = "JOGO DE CRAZY CHESS";
    public static String teamMessage = "Equipa das ";
    public static String winMessage = "VENCERAM AS ";
    public static String tieMessage = "EMPATE";
    public static String resultMessage = "Resultado: ";

    public static boolean readPieceValidation(int ID, int type, int teamID){
        return ID > 0 &&
               type == 0 && // temporary (just the king piece atm)
               (teamID == blackTeamID || teamID == whiteTeamID);
    }
}
