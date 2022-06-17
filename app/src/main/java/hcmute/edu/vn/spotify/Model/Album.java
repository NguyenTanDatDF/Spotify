package hcmute.edu.vn.spotify.Model;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class Album implements Serializable {
    //Create properties
    @Exclude
    public String key;

    private String name;
    private String resourceId;
    private String description;
    private String artistId;
    private String artistName;
    private String albumId;


    //Create empty Album constructor
    public Album() {

    }

    //Create album constructor
    public Album(String name, String resourceId, String description, String artistId, String artistName, String albumId) {
        this.name = name;
        this.resourceId = resourceId;
        this.description = description;
        this.artistId = artistId;
        this.artistName = artistName;
        this.albumId = albumId;
    }

    //Getter and setter
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getArtistId() {
        return artistId;
    }

    public void setArtistId(String artistId) {
        this.artistId = artistId;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getAlbumId() {
        return albumId;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }
}

