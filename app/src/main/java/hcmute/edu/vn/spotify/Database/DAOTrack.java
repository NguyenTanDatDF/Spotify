package hcmute.edu.vn.spotify.Database;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class DAOTrack {
    //Call data reference and firebase database
    public DatabaseReference databaseReference;
    FirebaseDatabase db;
    // Get all data from firebase
    public DAOTrack() {
        db = FirebaseDatabase.getInstance("https://admin-sportify-default-rtdb.firebaseio.com/");
        databaseReference = db.getReference("Track"); // return class name
    }
    //Get key
    public Query getByKey() { return databaseReference.orderByKey();}
}
