package hcmute.edu.vn.spotify.Activity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import hcmute.edu.vn.spotify.R;

import java.util.ArrayList;
import java.util.List;

import hcmute.edu.vn.spotify.Adapter.PlaylistAdapter;
import hcmute.edu.vn.spotify.Model.Playlist;

public class UserActivity extends AppCompatActivity {

    Button editUserBt;
    ImageView backIv;

    private RecyclerView rcvPlaylist;
    private PlaylistAdapter playlistAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        editUserBt = (Button) findViewById(R.id.activityUser_editUserBtn);
        editUserBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent editUser_actitivy = new Intent(UserActivity.this, EditUserActivity.class);
                startActivity(editUser_actitivy);
            }
        });

        backIv = (ImageView) findViewById(R.id.activityUser_backIv);
        backIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        rcvPlaylist = findViewById(R.id.activityUser_listPlaylistRv);
        playlistAdapter = new PlaylistAdapter(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rcvPlaylist.setLayoutManager(linearLayoutManager);

        playlistAdapter.setData(getListPlaylist());
        rcvPlaylist.setAdapter(playlistAdapter);
    }
    private List<Playlist> getListPlaylist() {
        List<Playlist> list = new ArrayList<>();

        list.add(new Playlist(R.drawable.playlist, "My playlist", "Hoan"));
        list.add(new Playlist(R.drawable.playlist, "My first play", "Tien"));
        list.add(new Playlist(R.drawable.playlist, "dancin", "Dat"));
        list.add(new Playlist(R.drawable.playlist, "EDM", "Luan"));
        return list;
    }
}
