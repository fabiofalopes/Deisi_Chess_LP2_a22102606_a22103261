package pt.ulusofona.lp2.deisichess;

public class Square {
    int id;
    String tipo;
    int equipa;
    String alcunha;
    String png;

    public Square(int id, String tipo, int equipa, String alcunha, String png) {
        this.id = id;
        this.tipo = tipo;
        this.equipa = equipa;
        this.alcunha = alcunha;
        this.png = png;
    }

    public int getId() {
        return id;
    }

    public String getTipo() {
        return tipo;
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

    public void setId(int id) {
        this.id = id;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setEquipa(int equipa) {
        this.equipa = equipa;
    }

    public void setAlcunha(String alcunha) {
        this.alcunha = alcunha;
    }

    public void setPng(String png) {
        this.png = png;
    }
}
