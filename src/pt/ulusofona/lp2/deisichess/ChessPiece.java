package pt.ulusofona.lp2.deisichess;

public class ChessPiece {
    int id;
    int equipa; // Black Pieces - equipa : 0 | White Pieces equipa : 1
    private String alcunha; // Alcunha
    private String png;
    private boolean white = false;

    public ChessPiece(int id, int equipa , String alcunha, String png, boolean white) {
        this.id = id;
        this.equipa = equipa;
        this.alcunha = alcunha;
        this.png = png;
        this.white = white;
    }

    public int getId() {
        return id;
    }

    public int getEquipa() {
        return equipa;
    }

    public String getAlcunha() {
        return alcunha;
    }

    public String getPng() {
        return png;
    }

    public boolean getIsWhite()
    {
        return this.white;
    }

}
