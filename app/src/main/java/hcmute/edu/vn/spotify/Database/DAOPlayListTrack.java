package hcmute.edu.vn.spotify.Database;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import hcmute.edu.vn.spotify.Model.Playlist;
import hcmute.edu.vn.spotify.Model.PlaylistTrack;

public class DAOPlayListTrack {
    //Call data reference and firebase database
    public DatabaseReference databaseReference;
    FirebaseDatabase db;
    // Get all data from firebase
    public DAOPlayListTrack() {
        db = FirebaseDatabase.getInstance("https://admin-sportify-default-rtdb.firebaseio.com/");
        databaseReference = db.getReference("PlaylistTrack"); // return class name
    }
    //Add new music playlist track to database
    public Task<Void> addNewPlaylistTrack(PlaylistTrack playlistTrack){
        if(playlistTrack != null) {
            try {
                return databaseReference.child(playlistTrack.getKey()).setValue(playlistTrack);
            }
            catch (Exception e) {
                e.getMessage();
            }
        }
        return null;
    }
    //Remove playlist track
    public Task<Void> removePlaylistTrack(String key){
        return databaseReference.child(key).removeValue();
    }
    //Get key
    public Query getByKey() { return databaseReference.orderByKey();}
}
