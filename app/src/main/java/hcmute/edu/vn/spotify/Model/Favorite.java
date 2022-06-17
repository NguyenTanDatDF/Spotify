package hcmute.edu.vn.spotify.Model;

import hcmute.edu.vn.spotify.Interface.Prototyte;

public class Favorite implements Prototyte {
    //Create properties
    String uid;
    String tid;

    //Create Favorite constructor
    public Favorite(String uid, String tid) {
        this.uid = uid;
        this.tid = tid;
    }

    //Create empty constructor
    public Favorite() {
    }

    //Getter and setter
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public Prototyte createClone() {
        return new Favorite(uid,tid);
    }
}
