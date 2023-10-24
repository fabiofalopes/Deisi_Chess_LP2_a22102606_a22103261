package pt.ulusofona.lp2.deisichess;

public class Team {
    private int ID;
    private String name;
    private int invalidMoves;
    private int validMoves;
    private int countKills;

    public Team(int ID, String name){
        if(name == null || name.isEmpty()){
            throw new IllegalArgumentException("name is required to create a team");
        }

        this.ID = ID;
        this.name = name;
        this.invalidMoves = 0;
        this.validMoves = 0;
        this.countKills = 0;
    }

    // region Methods
    public void addInvalidMove(){
        this.invalidMoves += 1;
    }

    public void addValidMove(){
        this.validMoves += 1;
    }

    public void addKill(){
        this.countKills += 1;
    }

    public String[] getResult(){
        return new String[] {
            "Equipa das " + this.name,
            this.countKills + "",
            this.validMoves + "",
            this.invalidMoves + ""
        };
    }
    // endregion

    @Override
    public String toString() {
        return "Equipa das " + this.name;
    }
}
