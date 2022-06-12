package hcmute.edu.vn.spotify.Database;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import hcmute.edu.vn.spotify.Model.Playlist;
import hcmute.edu.vn.spotify.Model.User;

public class DAOUser {
    public DatabaseReference databaseReference;
    public FirebaseDatabase db;
    public DAOUser() {
        db = FirebaseDatabase.getInstance("https://admin-sportify-default-rtdb.firebaseio.com/");
        databaseReference = db.getReference("User"); // return class name
    }
    public Task<Void> addNewUser(User user){
        if(user != null) {
            try {
                return databaseReference.child(user.getUsername()).setValue(user);
            }
            catch (Exception e) {
                e.getMessage();
            }
        }
        return null;
    }
    public Query getByKey() { return databaseReference.orderByKey();}
}
