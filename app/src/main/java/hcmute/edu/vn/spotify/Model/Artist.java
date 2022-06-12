package hcmute.edu.vn.spotify.Model;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class Artist implements Serializable {
    @Exclude
    public String key;

    public String imageArtist;
    public String nameArtist;
    private String idArtist;

    public Artist(String imageArtist, String nameArtist, String idArtist) {
        this.imageArtist = imageArtist;
        this.nameArtist = nameArtist;
        this.idArtist = idArtist;
    }
    public Artist() {

    }

    public String getImageArtist() {
        return imageArtist;
    }

    public void setImageArtist(String imageArtist) {
        this.imageArtist = imageArtist;
    }

    public String getNameArtist() {
        return nameArtist;
    }

    public void setNameArtist(String nameArtist) {
        this.nameArtist = nameArtist;
    }

    public String getIdArtist() {
        return idArtist;
    }

    public void setIdArtist(String idArtist) {
        this.idArtist = idArtist;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
