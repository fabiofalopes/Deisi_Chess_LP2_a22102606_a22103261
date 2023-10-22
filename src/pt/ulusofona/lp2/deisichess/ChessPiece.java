package pt.ulusofona.lp2.deisichess;

public class ChessPiece {
    int id;
    int team; // Black Pieces - team : 0 | White Pieces team : 1
    private String nickname;
    private String image;

    public ChessPiece(int id, int team , String nickname, String image) {
        this.id = id;
        this.team = team;
        this.nickname = nickname;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public int getTeam() {
        return team;
    }

    public String getNickname() {
        return nickname;
    }

    public String getImage() {
        return image;
    }
}
