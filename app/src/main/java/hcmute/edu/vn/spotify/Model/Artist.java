package hcmute.edu.vn.spotify.Model;

public class Artist {
    private int aId;
    private String aName;

    public Artist(int aId, String aName) {
        this.aId = aId;
        this.aName = aName;
    }

    public int getaId() {
        return aId;
    }

    public void setaId(int aId) {
        this.aId = aId;
    }

    public String getaName() {
        return aName;
    }

    public void setaName(String aName) {
        this.aName = aName;
    }
}
