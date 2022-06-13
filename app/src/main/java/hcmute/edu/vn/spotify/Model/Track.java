package hcmute.edu.vn.spotify.Model;

//import com.google.firebase.database.Exclude;
//
//import java.io.Serializable;
//
//public class Track implements Serializable {
//    @Exclude
//    private String key;
//
//    private String albumId;
//    private String artistId;
//    private String image;
//    private String name;
//    private String source;
//    private String tGenre;
//    private int tListens;
//    private String tLyrics;
//
//    public Track(String albumId, String artistId, String image, String name, String source, String tGenre, int tListens, String tLyrics) {
//        this.albumId = albumId;
//        this.artistId = artistId;
//        this.image = image;
//        this.name = name;
//        this.source = source;
//        this.tGenre = tGenre;
//        this.tListens = tListens;
//        this.tLyrics = tLyrics;
//
//    }
//
//    public Track() {
//
//    }
//
//    public String getKey() {
//        return key;
//    }
//
//    public void setKey(String key) {
//        this.key = key;
//    }
//
//    public String getAlbumId() {
//        return albumId;
//    }
//
//    public void setAlbumId(String albumId) {
//        this.albumId = albumId;
//    }
//
//    public String getArtistId() {
//        return artistId;
//    }
//
//    public void setArtistId(String artistId) {
//        this.artistId = artistId;
//    }
//
//    public String getImage() {
//        return image;
//    }
//
//    public void setImage(String image) {
//        this.image = image;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getSource() {
//        return source;
//    }
//
//    public void setSource(String source) {
//        this.source = source;
//    }
//
//    public String gettGenre() {
//        return tGenre;
//    }
//
//    public void settGenre(String tGenre) {
//        this.tGenre = tGenre;
//    }
//
//    public int gettListens() {
//        return tListens;
//    }
//
//    public void settListens(int tListens) {
//        this.tListens = tListens;
//    }
//
//    public String gettLyrics() {
//        return tLyrics;
//    }
//
//    public void settLyrics(String tLyrics) {
//        this.tLyrics = tLyrics;
//    }
//}
import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.PropertyName;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import hcmute.edu.vn.spotify.Interface.Prototyte;

@IgnoreExtraProperties
public class Track implements Serializable, Prototyte {

    @Exclude
    String key;
    private String name;
    private String source;
    private int tListens;
    private String tLyric;
    private String tGenre;
    private String image;
    private Date tReleaseDate;
    private Album tAlbum;
    private Artist tArtist;
    private String artistId;
    private String albumId;
    private String artistName;
    public Track() {
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("tName", name);
        result.put("source", source);
        result.put("tListens", tListens);
        result.put("tGenre", tGenre);
        result.put("image", image);
        result.put("tReleaseDate", tReleaseDate);
        result.put("tAlbum", tAlbum);
        result.put("tArtist", tArtist);
        result.put("artistId", artistId);
        result.put("albumId", albumId);

        return result;
    }

    public Track(String key, String name, String source, int tListens, String tLyric, String tGenre, String image, Date tReleaseDate, Album tAlbum, Artist tArtist, String artistId, String albumId, String artistName) {
        this.key = key;
        this.name = name;
        this.source = source;
        this.tListens = tListens;
        this.tLyric = tLyric;
        this.tGenre = tGenre;
        this.image = image;
        this.tReleaseDate = tReleaseDate;
        this.tAlbum = tAlbum;
        this.tArtist = tArtist;
        this.artistId = artistId;
        this.albumId = albumId;
        this.artistName = artistName;
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

    public int gettListens() {
        return tListens;
    }

    public void settListens(int tListens) {
        this.tListens = tListens;
    }

    public String gettLyric() {
        return tLyric;
    }

    public void settLyric(String tLyric) {
        this.tLyric = tLyric;
    }

    public String gettGenre() {
        return tGenre;
    }

    public void settGenre(String tGenre) {
        this.tGenre = tGenre;
    }

    public Date gettReleaseDate() {
        return tReleaseDate;
    }

    public void settReleaseDate(Date tReleaseDate) {
        this.tReleaseDate = tReleaseDate;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Album gettAlbum() {
        return tAlbum;
    }

    public void settAlbum(Album tAlbum) {
        this.tAlbum = tAlbum;
    }

    public Artist gettArtist() {
        return tArtist;
    }

    public void settArtist(Artist tArtist) {
        this.tArtist = tArtist;
    }

    public String getAlbumId() {
        return albumId;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
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

    @Override
    public String toString() {
        return "Track{" +
                "key='" + key + '\'' +
                ", name='" + name + '\'' +
                ", source='" + source + '\'' +
                ", tListens=" + tListens +
                ", tLyric='" + tLyric + '\'' +
                ", tGenre='" + tGenre + '\'' +
                ", image='" + image + '\'' +
                ", tReleaseDate=" + tReleaseDate +
                ", tAlbum=" + tAlbum +
                ", tArtist=" + tArtist +
                ", artistId='" + artistId + '\'' +
                ", albumId='" + albumId + '\'' +
                ", artistName='" + artistName + '\'' +
                '}';
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }
    @Override
    public Prototyte createClone() {
        return new Track(key,name ,source ,tListens ,tLyric ,tGenre ,image ,tReleaseDate ,tAlbum ,tArtist ,artistId ,albumId ,artistName);
    }
}