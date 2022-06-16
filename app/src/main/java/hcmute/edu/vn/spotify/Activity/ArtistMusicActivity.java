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
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import hcmute.edu.vn.spotify.Adapter.AlbumAdapter;
import hcmute.edu.vn.spotify.Adapter.PlaylistAdapter;
import hcmute.edu.vn.spotify.Adapter.TrackAdapter;
import hcmute.edu.vn.spotify.Database.DAOAlbum;
import hcmute.edu.vn.spotify.Database.DAOTrack;
import hcmute.edu.vn.spotify.Model.Album;
import hcmute.edu.vn.spotify.Model.Artist;
import hcmute.edu.vn.spotify.Model.Track;
import hcmute.edu.vn.spotify.R;

public class ArtistMusicActivity extends AppCompatActivity {
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
        setContentView(R.layout.activity_artist_music);

        // Check if data has been received from previous intent null
        if(getIntent().getExtras() != null){

            // Get object data
            Artist artist = (Artist) getIntent().getExtras().get("object_artist");

            //Set data of recyclerview by artist id
            setData(artist.getIdArtist().trim());
        }

    }

    // use to get list of album
    private List<Album> getListAlbum(String artistId)
    {
        // Create new list
        List<Album> list = new ArrayList<>();
        // Create new daoAlbum to get data
        DAOAlbum daoAlbum = new DAOAlbum();
        // this listener will listen from firebase continuously and get data
        daoAlbum.getByKey().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data: snapshot.getChildren()){
                    Album album = data.getValue(Album.class);
                    if(album.getArtistId().trim().equals(artistId)){
                        list.add(album);
                        String key = data.getKey();
                        album.setKey(key);
                    }
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
    private List<Track> getListTrack(String artistId)
    {
        List<Track> list = new ArrayList<>();

        DAOTrack daoTrack = new DAOTrack();
        // this listener will listen from firebase continuously and get data
        daoTrack.getByKey().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data: snapshot.getChildren()){
                    Track track = data.getValue(Track.class);
                    if(track.getArtistId().trim().equals(artistId))
                    {
                        list.add(track);
                        String key = data.getKey();
                        track.setKey(key);
                    }
                }
                // Change recycle view
                trackAdapter.setData(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return list;
    }
    //Set data of recyclerview
    public void setData(String artistId){
        //Set data for albums
        rcvAlbum = findViewById(R.id.activityArtistMusic_listAlbumRv);
        albumAdapter = new AlbumAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        rcvAlbum.setLayoutManager(linearLayoutManager);
        albumAdapter.setData(getListAlbum(artistId));
        rcvAlbum.setAdapter(albumAdapter);

        //Set data for track
        rcvTrack = findViewById(R.id.activityArtistMusic_listMusicRv);
        trackAdapter = new TrackAdapter(this);
        LinearLayoutManager linearLayoutTrackManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rcvTrack.setLayoutManager(linearLayoutTrackManager);
        trackAdapter.setData(getListTrack(artistId));
        rcvTrack.setAdapter(trackAdapter);
    }
}
