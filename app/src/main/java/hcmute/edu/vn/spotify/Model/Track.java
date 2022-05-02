package hcmute.edu.vn.spotify.Model;

public class Track {
    private String tName;
    private int tListens;
    private int tId;
    private String tLyric;
    private String tGerne;

    public Track(String tName, int tListens, int tId, String tLyric, String tGerne) {
        this.tName = tName;
        this.tListens = tListens;
        this.tId = tId;
        this.tLyric = tLyric;
        this.tGerne = tGerne;
    }

    public String gettName() {
        return tName;
    }

    public void settName(String tName) {
        this.tName = tName;
    }

    public int gettListens() {
        return tListens;
    }

    public void settListens(int tListens) {
        this.tListens = tListens;
    }

    public int gettId() {
        return tId;
    }

    public void settId(int tId) {
        this.tId = tId;
    }

    public String gettLyric() {
        return tLyric;
    }

    public void settLyric(String tLyric) {
        this.tLyric = tLyric;
    }

    public String gettGerne() {
        return tGerne;
    }

    public void settGerne(String tGerne) {
        this.tGerne = tGerne;
    }
}
