package hcmute.edu.vn.spotify.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import hcmute.edu.vn.spotify.Database.DAOPlaylist;
import hcmute.edu.vn.spotify.Model.Playlist;
import hcmute.edu.vn.spotify.R;

public class NewPlaylistActivity extends AppCompatActivity {

    //Variables
    EditText newPlaylist_et;
    TextView cancel_tv;
    TextView create_tv;
    DAOPlaylist daoPlaylist = new DAOPlaylist();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_playlist);

        //focus edittext
        newPlaylist_et = findViewById(R.id.activityAddPlaylist_playlistNameEt);
        newPlaylist_et.requestFocus();

        //cancel event
        cancel_tv = findViewById(R.id.activityAddPlaylist_cancelBtn);
        cancel_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //Create new playlist
        create_tv = findViewById(R.id.activityAddPlaylist_createBtn);
        create_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Playlist playlist = new Playlist("https://img.freepik.com/free-vector/playlist-neon-sign-black-brick-wall_77399-755.jpg?w=2000", 1, newPlaylist_et.getText().toString(), "Hoan");
                daoPlaylist.addNewPlaylist(playlist).addOnSuccessListener(suc -> {
                    Toast.makeText(NewPlaylistActivity.this, "Added new playlist successfully!", Toast.LENGTH_SHORT).show();
                    finish();
                }).addOnFailureListener(err -> {
                    Toast.makeText(NewPlaylistActivity.this, "Added new playlist failed!", Toast.LENGTH_SHORT).show();
                });
            }
        });
    }
}
