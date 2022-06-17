package hcmute.edu.vn.spotify.Database;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class DAOAlbum {
    //Call data reference and firebase database
    public DatabaseReference databaseReference;
    public FirebaseDatabase db;

    // Get all data from firebase
    public DAOAlbum() {
        db = FirebaseDatabase.getInstance("https://admin-sportify-default-rtdb.firebaseio.com/");
        databaseReference = db.getReference("Album"); // return class name
    }

    //Get key
    public Query getByKey() { return databaseReference.orderByKey();}
}
