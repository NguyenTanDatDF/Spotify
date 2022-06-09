package hcmute.edu.vn.spotify.Model;

public class MusicPlaylist<L> {
    private String lImageUrl;
    private String lName;

    public MusicPlaylist(String lImageUrl, String lName) {
        this.lImageUrl = lImageUrl;
        this.lName = lName;
    }

    public String getlImageUrl() {
        return lImageUrl;
    }

    public void setlImageUrl(String lImageUrl) {
        this.lImageUrl = lImageUrl;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }
}
