package hcmute.edu.vn.spotify.Model;


import com.google.firebase.database.Exclude;

public class MusicPlaylistTrack {
    //Create properties
    @Exclude
    String key;
    private String trackId;
    private String musicPlaylistId;
    private String tGenre;
    private String artistId;

    //Create empty constructor
    public MusicPlaylistTrack() {

    }

    //Create constructor
    public MusicPlaylistTrack(String trackId, String musicPlaylistId, String tGenre, String artistId) {
        this.trackId = trackId;
        this.musicPlaylistId = musicPlaylistId;
        this.tGenre = tGenre;
        this.artistId = artistId;
    }

    //Getter and setter
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTrackId() {
        return trackId;
    }

    public void setTrackId(String trackId) {
        this.trackId = trackId;
    }

    public String getMusicPlaylistId() {
        return musicPlaylistId;
    }

    public void setMusicPlaylistId(String musicPlaylistId) {
        this.musicPlaylistId = musicPlaylistId;
    }

    public String gettGenre() {
        return tGenre;
    }

    public void settGenre(String tGenre) {
        this.tGenre = tGenre;
    }

    public String getArtistId() {
        return artistId;
    }

    public void setArtistId(String artistId) {
        this.artistId = artistId;
    }

}
