package hcmute.edu.vn.spotify.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import hcmute.edu.vn.spotify.R;

public class NewPlaylistActivity extends AppCompatActivity {

    EditText newPlaylist_et;
    TextView cancel_tv;
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
    }
}
