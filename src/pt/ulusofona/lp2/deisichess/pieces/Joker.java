package pt.ulusofona.lp2.deisichess.pieces;

import pt.ulusofona.lp2.deisichess.Board;
import pt.ulusofona.lp2.deisichess.ChessPiece;
import pt.ulusofona.lp2.deisichess.GameStaticData;

public class Joker extends ChessPiece {
    private int cloneID;
    private ChessPiece clone;

    public Joker(int teamID){ super(teamID); }

    public Joker(String nickame, int teamID) {
        super(nickame, teamID);
        this.id = GameStaticData.JOKER_PIECE_ID;
        this.typeName = GameStaticData.JOKER_NAME;
        this.value = GameStaticData.JOKER_PIECE_VALUE;
        this.cloneID = GameStaticData.QUEEN_PIECE_ID;
        this.clone = new Queen(this.teamID);
    }

    public void cloneNext(){
        this.cloneID++;

        switch (cloneID) {
            case GameStaticData.MAGIC_LITTLE_HORSE_PIECE_ID -> this.clone = new MagicLittleHorse(this.teamID);
            case GameStaticData.VILLAGE_PRIEST_PIECE_ID -> this.clone = new VillagePriest(this.teamID);
            case GameStaticData.HORIZONTAL_TOWER_PIECE_ID -> this.clone = new HorizontalTower(this.teamID);
            case GameStaticData.VERTICAL_TOWER_PIECE_ID -> this.clone = new VerticalTower(this.teamID);
            case GameStaticData.HOMER_SIMPSON_PIECE_ID -> this.clone = new HomerSimpson(this.teamID);
            default -> {
                this.cloneID = GameStaticData.QUEEN_PIECE_ID;
                this.clone = new Queen(this.teamID);
            }
        }
    }

    @Override
    protected boolean isJoker(){ return true; }
    @Override
    public boolean tryMove(Board board, int x, int y) {
        return this.clone.tryMove(board, x, y);
    }
}
