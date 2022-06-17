package hcmute.edu.vn.spotify.Model;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class Topic implements Serializable {
    //Create properties
    @Exclude
    public String Key;

    private int tColor;
    public String name;
    public String url;

    //Create empty Topic constructor
    public Topic() {

    }
    //Create Topic constructor
    public Topic(String key, int tColor, String name, String url) {
        Key = key;
        this.tColor = tColor;
        this.name = name;
        this.url = url;
    }
    //Getter and setter
    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public int gettColor() {
        return tColor;
    }

    public void settColor(int tColor) {
        this.tColor = tColor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
