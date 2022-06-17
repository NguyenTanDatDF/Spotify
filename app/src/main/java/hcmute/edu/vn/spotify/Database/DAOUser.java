package hcmute.edu.vn.spotify.Database;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import hcmute.edu.vn.spotify.Model.Playlist;
import hcmute.edu.vn.spotify.Model.User;

public class DAOUser {
    //Call data reference and firebase database
    public DatabaseReference databaseReference;
    public FirebaseDatabase db;
    // Get all data from firebase
    public DAOUser() {
        db = FirebaseDatabase.getInstance("https://admin-sportify-default-rtdb.firebaseio.com/");
        databaseReference = db.getReference("User"); // return class name
    }
    //Add new user
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
    //Update user information
    public Task<Void> updateUser(User user){

        return databaseReference.child(user.getKey()).updateChildren(user.toMap());
    }
    //Get key
    public Query getByKey() { return databaseReference.orderByKey();}
}
