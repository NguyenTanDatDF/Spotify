package hcmute.edu.vn.spotify.Model;

public class Playlist {
    private int pId;
    private String pName;
    private String pCreator;

    public Playlist(int pId, String pName, String pCreator) {
        this.pId = pId;
        this.pName = pName;
        this.pCreator = pCreator;
    }

    public int getpId() {
        return pId;
    }

    public void setpId(int pId) {
        this.pId = pId;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getpCreator() {
        return pCreator;
    }

    public void setpCreator(String pCreator) {
        this.pCreator = pCreator;
    }

}
