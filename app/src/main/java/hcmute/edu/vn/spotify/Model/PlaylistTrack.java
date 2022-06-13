package hcmute.edu.vn.spotify.Model;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class PlaylistTrack implements Serializable {
    @Exclude
    private String key;
    private String trackId;
    private String playlistId;

    public PlaylistTrack(String trackId, String playlistId) {
        this.trackId = trackId;
        this.playlistId = playlistId;
    }

    public PlaylistTrack() {

    }

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

    public String getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(String playlistId) {
        this.playlistId = playlistId;
    }
}
