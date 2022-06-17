package hcmute.edu.vn.spotify.Database;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import hcmute.edu.vn.spotify.Model.MusicPlaylistTrack;
import hcmute.edu.vn.spotify.Model.MusicPlaylistTrackBuilder;
import hcmute.edu.vn.spotify.Model.Playlist;

public class DAOMusicPlaylistTrack {
    public DatabaseReference databaseReference;
    public FirebaseDatabase db;
    public DAOMusicPlaylistTrack() {
        db = FirebaseDatabase.getInstance("https://admin-sportify-default-rtdb.firebaseio.com/");
        databaseReference = db.getReference("MusicPlaylistTrack"); // return class name
    }
    public Task<Void> addNewMusicPlaylistTrack(MusicPlaylistTrackBuilder musicPlaylistTrackBuilder){
        if(musicPlaylistTrackBuilder != null) {
            try {
                return databaseReference.child(musicPlaylistTrackBuilder.getMusicPlaylistId()).setValue(musicPlaylistTrackBuilder);
            }
            catch (Exception e) {
                e.getMessage();
            }
        }
        return null;
    }
    public Task<Void> removeMusicPlaylistTrack(String key){
        return databaseReference.child(key).removeValue();
    }
    public Query getByKey() { return databaseReference.orderByKey();}
}
