package hcmute.edu.vn.spotify.Activity;

import android.os.Bundle;

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

    private RecyclerView rcvPlaylist;
    private PlaylistAdapter playlistAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        rcvPlaylist = findViewById(R.id.activityUser_listPlaylistRv);
        playlistAdapter = new PlaylistAdapter(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rcvPlaylist.setLayoutManager(linearLayoutManager);

        playlistAdapter.setData(getListUser());
        rcvPlaylist.setAdapter(playlistAdapter);
    }
    private List<Playlist> getListUser() {
        List<Playlist> list = new ArrayList<>();

        list.add(new Playlist(R.drawable.playlist, "My playlist", "Hoan"));
        list.add(new Playlist(R.drawable.playlist, "My first play", "Tien"));
        list.add(new Playlist(R.drawable.playlist, "dancin", "Dat"));
        list.add(new Playlist(R.drawable.playlist, "EDM", "Luan"));


        return list;
    }
}
