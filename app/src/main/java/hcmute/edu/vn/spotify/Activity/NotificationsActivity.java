package hcmute.edu.vn.spotify.Activity;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import hcmute.edu.vn.spotify.Adapter.AlbumAdapter;
import hcmute.edu.vn.spotify.Adapter.TrackAdapter;
import hcmute.edu.vn.spotify.Database.DAOAlbum;
import hcmute.edu.vn.spotify.Database.DAOTrack;
import hcmute.edu.vn.spotify.Model.Album;
import hcmute.edu.vn.spotify.Model.Track;
import hcmute.edu.vn.spotify.R;

public class NotificationsActivity extends AppCompatActivity {
    // The view which showing data as list
    private RecyclerView rcvAlbum;
    // The view which showing data as list
    private RecyclerView rcvTrack;
    // use to pass raw data into recyclerview of album
    private AlbumAdapter albumAdapter;
    // use to pass raw data into recyclerview of track
    private TrackAdapter trackAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        //back to activity
        ImageView backIv = (ImageView) findViewById(R.id.activityNotif_backIv);
        backIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //Set Data for new album
        rcvAlbum = findViewById(R.id.activityNotification_albumsRv);
        albumAdapter = new AlbumAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        rcvAlbum.setLayoutManager(linearLayoutManager);
        albumAdapter.setData(getListAlbum());
        rcvAlbum.setAdapter(albumAdapter);

        //Set data for new track
        rcvTrack = findViewById(R.id.activityNotification_songsRv);
        trackAdapter = new TrackAdapter(this);
        LinearLayoutManager linearLayoutTrackManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rcvTrack.setLayoutManager(linearLayoutTrackManager);
        trackAdapter.setData(getListTrack());
        rcvTrack.setAdapter(trackAdapter);
    }
    //Get list track from firebase
    private List<Album> getListAlbum()
    {
        //Create a new list to pass data
        List<Album> list = new ArrayList<>();
        // Create DAO to get data
        DAOAlbum daoAlbum = new DAOAlbum();
        // this listener will listen from firebase continuously and get data
        daoAlbum.getByKey().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data: snapshot.getChildren()){
                    Album album = data.getValue(Album.class);
                    list.add(album);
                    String key = data.getKey();
                    album.setKey(key);
                }
                albumAdapter.setData(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return list;
    }
    //Get list track from firebase
    private List<Track> getListTrack()
    {
        //Create a new list to pass data
        List<Track> list = new ArrayList<>();
        // Create DAO to get data
        DAOTrack daoTrack = new DAOTrack();
        // this listener will listen from firebase continuously and get data
        daoTrack.getByKey().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data: snapshot.getChildren()){
                    Track track = data.getValue(Track.class);
                    list.add(track);
                    String key = data.getKey();
                    track.setKey(key);
                }
                trackAdapter.setData(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return list;
    }
}
