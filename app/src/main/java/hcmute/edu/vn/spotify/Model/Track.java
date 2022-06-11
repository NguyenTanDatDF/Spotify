package hcmute.edu.vn.spotify.Model;

import com.google.firebase.database.Exclude;

public class Track {
    @Exclude
    private String key;

    private String albumId;
    private String artistId;
    private String image;
    private String name;
    private String source;
    private String tGenre;
    private int tListens;
    private String tLyrics;

    public Track(String albumId, String artistId, String image, String name, String source, String tGenre, int tListens, String tLyrics) {
        this.albumId = albumId;
        this.artistId = artistId;
        this.image = image;
        this.name = name;
        this.source = source;
        this.tGenre = tGenre;
        this.tListens = tListens;
        this.tLyrics = tLyrics;
    }

    public Track() {

    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getAlbumId() {
        return albumId;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

    public String getArtistId() {
        return artistId;
    }

    public void setArtistId(String artistId) {
        this.artistId = artistId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String gettGenre() {
        return tGenre;
    }

    public void settGenre(String tGenre) {
        this.tGenre = tGenre;
    }

    public int gettListens() {
        return tListens;
    }

    public void settListens(int tListens) {
        this.tListens = tListens;
    }

    public String gettLyrics() {
        return tLyrics;
    }

    public void settLyrics(String tLyrics) {
        this.tLyrics = tLyrics;
    }
}
