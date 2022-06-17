package hcmute.edu.vn.spotify.Model;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class Playlist implements Serializable {
    //Create properties
    @Exclude
    public String Key;

    public String pUrl;
    public String uID;
    public String pName;
    private String uName;
    private String playlistId;

    //Create Playlist constructor
    public Playlist(String pUrl, String uID, String pName, String uName, String playlistId) {
        this.pUrl = pUrl;
        this.uID = uID;
        this.pName = pName;
        this.uName = uName;
        this.playlistId = playlistId;
    }

    //Create empty playlist construtor

    public Playlist() {

    }

    //Getter and setter

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public String getpUrl() {
        return pUrl;
    }

    public void setpUrl(String pUrl) {
        this.pUrl = pUrl;
    }

    public String getuID() {
        return uID;
    }

    public void setuID(String uID) {
        this.uID = uID;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(String playlistId) {
        this.playlistId = playlistId;
    }
}
