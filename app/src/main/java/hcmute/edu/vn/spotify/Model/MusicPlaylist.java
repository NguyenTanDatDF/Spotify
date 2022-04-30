package hcmute.edu.vn.spotify.Model;

public class MusicPlaylist<L> {
    private int lId;
    private String lName;

    public MusicPlaylist(int lId, String lName) {
        this.lId = lId;
        this.lName = lName;
    }

    public int getlId() {
        return lId;
    }

    public void setlId(int lId) {
        this.lId = lId;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }
}
