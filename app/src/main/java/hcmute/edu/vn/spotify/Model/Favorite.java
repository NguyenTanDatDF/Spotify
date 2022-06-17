package hcmute.edu.vn.spotify.Model;

import hcmute.edu.vn.spotify.Interface.Prototyte;

public class Favorite implements Prototyte {
    String uid;
    String tid;

    public Favorite(String uid, String tid) {
        this.uid = uid;
        this.tid = tid;
    }

    public Favorite() {
    }

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
