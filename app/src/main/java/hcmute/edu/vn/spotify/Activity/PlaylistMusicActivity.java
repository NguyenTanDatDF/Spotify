package hcmute.edu.vn.spotify.Activity;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import hcmute.edu.vn.spotify.Adapter.AlbumAdapter;
import hcmute.edu.vn.spotify.Adapter.TrackAdapter;
import hcmute.edu.vn.spotify.Database.DAOPlayListTrack;
import hcmute.edu.vn.spotify.Database.DAOTrack;
import hcmute.edu.vn.spotify.Model.Artist;
import hcmute.edu.vn.spotify.Model.Playlist;
import hcmute.edu.vn.spotify.Model.PlaylistTrack;
import hcmute.edu.vn.spotify.Model.Track;
import hcmute.edu.vn.spotify.R;

public class PlaylistMusicActivity extends AppCompatActivity {
    private RecyclerView rcvTrack;
    private TrackAdapter trackAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist_music);
        if(getIntent().getExtras() != null){
            Playlist playlist = (Playlist) getIntent().getExtras().get("object_playlist");
            setData(playlist.getuID().trim(), playlist.getPlaylistId().trim());
        }
    }
    private List<Track> getListTrack(String userId, String playlistId)
    {
        List<Track> list = new ArrayList<>();
        List<PlaylistTrack> playlistTrack = getPlaylistTrack();
        DAOTrack daoTrack = new DAOTrack();
        daoTrack.getByKey().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data: snapshot.getChildren()){
                    Track track = data.getValue(Track.class);
                    for(PlaylistTrack playlistTrack1: playlistTrack){
                        if(userId.equals(SigninActivity.definedUser.getUserId().trim()) && playlistId.equals(playlistTrack1.getPlaylistId().trim()) && track.getTrackId().trim().equals(playlistTrack1.getTrackId().replaceAll("-", "").trim()))
                        {
                            list.add(track);
                            String key = data.getKey();
                            track.setKey(key);
                        }
                    }
                }
                trackAdapter.setData(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return list;
    }
    private List<PlaylistTrack> getPlaylistTrack()
    {
        List<PlaylistTrack> list = new ArrayList<>();
        DAOPlayListTrack daoPlayListTrack = new DAOPlayListTrack();
        daoPlayListTrack.getByKey().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data: snapshot.getChildren()){
                    PlaylistTrack playlistTrack = data.getValue(PlaylistTrack.class);
                    list.add(playlistTrack);
                    String key = data.getKey();
                    playlistTrack.setKey(key);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return list;
    }
    public void setData(String userId, String playlistId){
        //Set data for track
        rcvTrack = findViewById(R.id.activityArtistMusic_listMusicRv);
        trackAdapter = new TrackAdapter(this);
        LinearLayoutManager linearLayoutTrackManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rcvTrack.setLayoutManager(linearLayoutTrackManager);
        trackAdapter.setData(getListTrack(userId, playlistId));
        rcvTrack.setAdapter(trackAdapter);
    }
}
