package hcmute.edu.vn.spotify.Model;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class MusicPlaylist implements Serializable {
    //Create properties
    @Exclude
    String key;
    private String lImageUrl;
    private String lName;
    private String musicPlaylistId;

    //Create empty constructor
    public MusicPlaylist() {

    }

    //Create music playlist constructor
    public MusicPlaylist(String lImageUrl, String lName, String musicPlaylistId) {
        this.lImageUrl = lImageUrl;
        this.lName = lName;
        this.musicPlaylistId = musicPlaylistId;
    }

    //Getter and setter
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
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

    public String getMusicPlaylistId() {
        return musicPlaylistId;
    }

    public void setMusicPlaylistId(String musicPlaylistId) {
        this.musicPlaylistId = musicPlaylistId;
    }
}
