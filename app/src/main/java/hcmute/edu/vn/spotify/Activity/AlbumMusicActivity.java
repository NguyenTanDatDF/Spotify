package hcmute.edu.vn.spotify.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import hcmute.edu.vn.spotify.Adapter.AlbumAdapter;
import hcmute.edu.vn.spotify.Adapter.TrackAdapter;
import hcmute.edu.vn.spotify.Database.DAOAlbum;
import hcmute.edu.vn.spotify.Database.DAOArtist;
import hcmute.edu.vn.spotify.Database.DAOPlaylist;
import hcmute.edu.vn.spotify.Database.DAOTrack;
import hcmute.edu.vn.spotify.Model.Album;
import hcmute.edu.vn.spotify.Model.Artist;
import hcmute.edu.vn.spotify.Model.Playlist;
import hcmute.edu.vn.spotify.Model.Track;
import hcmute.edu.vn.spotify.R;

public class AlbumMusicActivity extends AppCompatActivity {

    private RecyclerView rcvAlbum;
    private RecyclerView rcvTrack;
    private AlbumAdapter albumAdapter;
    private TrackAdapter trackAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_music);

        if(getIntent().getExtras() != null){
            Album album = (Album) getIntent().getExtras().get("object_album");

            //Set data for albums
            rcvAlbum = findViewById(R.id.activityAlbumMusic_listAlbumRv);
            albumAdapter = new AlbumAdapter(this);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
            rcvAlbum.setLayoutManager(linearLayoutManager);
            albumAdapter.setData(getListAlbum(album.getArtistId().trim(), album.getArtistName().trim()));
            rcvAlbum.setAdapter(albumAdapter);

            //Set data for track
            rcvTrack = findViewById(R.id.activityAlbumMusic_listMusicRv);
            trackAdapter = new TrackAdapter(this);
            LinearLayoutManager linearLayoutTrackManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
            rcvTrack.setLayoutManager(linearLayoutTrackManager);
            trackAdapter.setData(getListTrack(album.getAlbumId().trim()));
            rcvTrack.setAdapter(trackAdapter);
        }
    }
    private List<Album> getListAlbum(String artistId, String artistName)
    {
        List<Album> list = new ArrayList<>();
        DAOAlbum daoAlbum = new DAOAlbum();
        daoAlbum.getByKey().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data: snapshot.getChildren()){
                    Album album = data.getValue(Album.class);
                    if(album.getArtistId().trim().equals(artistId)){
                        album.setArtistName(artistName);
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
    private List<Track> getListTrack(String albumId)
    {
        List<Track> list = new ArrayList<>();

        DAOTrack daoTrack = new DAOTrack();
        daoTrack.getByKey().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data: snapshot.getChildren()){
                    Track track = data.getValue(Track.class);
                    if(track.getAlbumId().trim().equals(albumId)){
                        list.add(track);
                        String key = data.getKey();
                        track.setKey(key);
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

}