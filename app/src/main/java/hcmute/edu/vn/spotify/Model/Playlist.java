package hcmute.edu.vn.spotify.Model;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class Playlist implements Serializable {
    @Exclude
    public String Key;

    public String pUrl;
    public int uID;
    public String pName;
    private String uName;

    public Playlist(String pUrl, int uID, String pName, String uName) {
        this.pUrl = pUrl;
        this.uID = uID;
        this.pName = pName;
        this.uName = uName;
    }
    public Playlist() {

    }

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

    public int getuID() {
        return uID;
    }

    public void setuID(int uID) {
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
}
