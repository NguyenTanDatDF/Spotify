package hcmute.edu.vn.spotify.Model;

public class Topic {
    private int tColor;
    private String tName;
    private int tImage;

    public Topic(int tColor, String tName, int tImage) {
        this.tColor = tColor;
        this.tName = tName;
        this.tImage = tImage;
    }

    public int gettColor() {
        return tColor;
    }

    public void settColor(int tColor) {
        this.tColor = tColor;
    }

    public String gettName() {
        return tName;
    }

    public void settName(String tName) {
        this.tName = tName;
    }

    public int gettImage() {
        return tImage;
    }

    public void settImage(int tImage) {
        this.tImage = tImage;
    }
}
