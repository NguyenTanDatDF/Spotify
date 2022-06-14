package hcmute.edu.vn.spotify.Database;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import hcmute.edu.vn.spotify.Model.Playlist;

public class DAOPlaylist {
    public DatabaseReference databaseReference;
    FirebaseDatabase db;
    public DAOPlaylist() {
        db = FirebaseDatabase.getInstance("https://admin-sportify-default-rtdb.firebaseio.com/");
        databaseReference = db.getReference("Playlist"); // return class name
    }
    public Task<Void> addNewPlaylist(Playlist playlist){
        if(playlist != null) {
            try {
                return databaseReference.child(playlist.getpName()).setValue(playlist);
            }
            catch (Exception e) {
                e.getMessage();
            }
        }
        return null;
    }
    public Task<Void> removePlaylist(String name){
        return databaseReference.child(name).removeValue();
    }
    public Query getByKey() { return databaseReference.orderByKey();}
}
