package pt.ulusofona.lp2.deisichess;

public class Team implements Cloneable{
    public static final int BLACK_TEAM_ID = 10;
    public static final String BLACK_TEAM_NAME = "Pretas";
    public static final int WHITE_TEAM_ID = 20;
    public static final String WHITE_TEAM_NAME = "Brancas";

    private int id;
    private String name;
    private int countValidMoves;
    private int countInvalidMoves;
    private int kills;
    private int score;

    Team(int id, String name){
        this.id = id;
        this.name = name;
        this.countValidMoves = 0;
        this.countInvalidMoves = 0;
        this.kills = 0;
        this.score = 0;
    }

    public void updateFromSavedFile(int countValidMoves, int countInvalidMoves, int kills, int score){
        this.countValidMoves = countValidMoves;
        this.countInvalidMoves = countInvalidMoves;
        this.kills = kills;
        this.score = score;
    }

    public int getId(){
        return this.id;
    }

    public int getCountValidMoves(){ return this.countValidMoves; }

    public int getCountInvalidMoves(){ return this.countInvalidMoves; }

    public int getKills(){return this.kills;}

    public int getScoreValue(){return this.score;}

    public String[] getScore(){
        return new String[] {
                "Equipa das " + this.name,
                this.kills + "",
                this.countValidMoves + "",
                this.countInvalidMoves + ""
        };
    }

    @Override
    public Team clone() {
        try {
            return (Team) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

    public void incrementValidMoves(){
        this.countValidMoves += 1;
    }

    public void incrementInvalidMoves(){
        this.countInvalidMoves += 1;
    }

    public void incrementKillsAndScore(int score){
        this.kills += 1;
        this.score += score;
    }
}
