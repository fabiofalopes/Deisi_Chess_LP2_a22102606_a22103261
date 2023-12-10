package refactor;

public class InvalidGameInputException extends Exception{
    private int lineWithError;
    private String problemDescription;

    public InvalidGameInputException(int lineWithError, String errorDescription){
        super();
        this.lineWithError = lineWithError;
        this.problemDescription = "Ocorreu um erro a ler o ficheiro, na linha " +
                                   this.lineWithError + " " +
                                   "com o seguinte problema: " +
                                   errorDescription;
    }

    public static String getMoreDataErrorDescription(int expected, int obtained){
        return "DADOS A MAIS (Esperava: " + expected + "; Obtive: " + obtained + ")";
    }
    public static String getLessDataErrorDescription(int expected, int obtained){
        return "DADOS A MENOS (Esperava: " + expected + "; Obtive: " + obtained + ")";
    }
    public int getLineWithError(){
        return this.lineWithError;
    }
    public String getProblemDescription(){
        return this.problemDescription;
    }
}
