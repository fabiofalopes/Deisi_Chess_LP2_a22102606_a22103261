package pt.ulusofona.lp2.deisichess;

public class InvalidGameInputException extends Exception{
    private int lineWithError;
    private String problemDescription;

    public InvalidGameInputException(int lineWithError, String errorDescription){
        this.lineWithError = lineWithError;
        this.problemDescription = errorDescription;
    }

    public int getLineWithError(){
        return this.lineWithError;
    }

    public String getProblemDescription(){
        return this.problemDescription;
    }

    public static String getMoreDataErrorDescription(int expected, int obtained){
        return "DADOS A MAIS (Esperava: " + expected + " ; Obtive: " + obtained + ")";
    }

    public static String getLessDataErrorDescription(int expected, int obtained){
        return "DADOS A MENOS (Esperava: " + expected + " ; Obtive: " + obtained + ")";
    }
}
