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
import hcmute.edu.vn.spotify.Model.User;
import hcmute.edu.vn.spotify.R;
import hcmute.edu.vn.spotify.Service.ThreadSafeLazyUserSingleton;

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
                User user = new User();
                ThreadSafeLazyUserSingleton singleton = ThreadSafeLazyUserSingleton.getInstance(user);
                user = singleton.user;

                Playlist playlist = new Playlist("https://img.freepik.com/free-vector/playlist-neon-sign-black-brick-wall_77399-755.jpg?w=2000", user.getUserId().toString(), newPlaylist_et.getText().toString(), user.getUsername().toString(), randomId());
                daoPlaylist.addNewPlaylist(playlist).addOnSuccessListener(suc -> {
                    Toast.makeText(NewPlaylistActivity.this, "Added new playlist successfully!", Toast.LENGTH_SHORT).show();
                    finish();
                }).addOnFailureListener(err -> {
                    Toast.makeText(NewPlaylistActivity.this, "Added new playlist failed!", Toast.LENGTH_SHORT).show();
                });
            }
        });
    }

    public String randomId() {
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(10);

        for (int i = 0; i < 15; i++) {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                    = (int) (AlphaNumericString.length()
                    * Math.random());

            // add Character one by one in end of sb
            sb.append(AlphaNumericString
                    .charAt(index));
        }
        return sb.toString();
    }
}
