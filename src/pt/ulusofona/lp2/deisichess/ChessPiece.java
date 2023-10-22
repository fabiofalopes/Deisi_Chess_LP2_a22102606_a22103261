package pt.ulusofona.lp2.deisichess;

public class ChessPiece {
    int id;
    int team; // Black Pieces - team : 0 | White Pieces team : 1
    int type; // King : 0
    private String nickname;
    private String image;

    public ChessPiece(int id, int team , String nickname, int type) {
        this.id = id;
        this.team = team;
        this.nickname = nickname;
        this.type = type;
        this.image = null;
    }

    public ChessPiece(int id, int team , String nickname, int type, String image) {
        this.id = id;
        this.team = team;
        this.nickname = nickname;
        this.type = type;
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

    public int getType() {
        return type;
    }

    public String getImage() {
        return image;
    }

    // toString() method
    @Override
    public String toString() {
        return "ChessPiece{" +
                "id=" + id +
                ", team=" + team +
                ", type=" + type +
                ", nickname='" + nickname + '\'' +
                ", image='" + image + '\'' +
                '}';
    }

}
