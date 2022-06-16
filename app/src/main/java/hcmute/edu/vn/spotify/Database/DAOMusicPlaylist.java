package hcmute.edu.vn.spotify.Database;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class DAOMusicPlaylist {
    public DatabaseReference databaseReference;
    public FirebaseDatabase db;
    public DAOMusicPlaylist() {
        db = FirebaseDatabase.getInstance("https://admin-sportify-default-rtdb.firebaseio.com/");
        databaseReference = db.getReference("MusicPlaylist"); // return class name
    }
    public Query getByKey() { return databaseReference.orderByKey();}
}
