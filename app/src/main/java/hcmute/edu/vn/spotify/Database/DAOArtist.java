package hcmute.edu.vn.spotify.Database;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class DAOArtist {
    public DatabaseReference databaseReference;
    FirebaseDatabase db;
    public DAOArtist() {
        db = FirebaseDatabase.getInstance("https://admin-sportify-default-rtdb.firebaseio.com/");
        databaseReference = db.getReference("artists"); // return class name
    }
    public Query getByKey() { return databaseReference.orderByKey();}
}
