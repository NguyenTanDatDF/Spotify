package hcmute.edu.vn.spotify.Activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import hcmute.edu.vn.spotify.Adapter.AlbumAdapter;
import hcmute.edu.vn.spotify.Adapter.TrackAdapter;
import hcmute.edu.vn.spotify.Database.DAOMusicPlaylistTrack;
import hcmute.edu.vn.spotify.Database.DAOTrack;
import hcmute.edu.vn.spotify.Model.Album;
import hcmute.edu.vn.spotify.Model.MusicPlaylist;
import hcmute.edu.vn.spotify.Model.MusicPlaylistTrack;
import hcmute.edu.vn.spotify.Model.MusicPlaylistTrackBuilder;
import hcmute.edu.vn.spotify.Model.PlaylistTrack;
import hcmute.edu.vn.spotify.Model.Track;
import hcmute.edu.vn.spotify.R;

public class MusicPlaylistTrackActivity extends AppCompatActivity {

    // declare the view
    com.google.android.material.appbar.CollapsingToolbarLayout musicPlaylistName;
    ImageView musicPlaylistImage;
    private RecyclerView rcvTrack;

    // declare the track adapter
    private TrackAdapter trackAdapter;

    // declare the DAO of track to get data from firebase
    DAOMusicPlaylistTrack daoMusicPlaylistTrack = new DAOMusicPlaylistTrack();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_playlist_track);

        //Get data from previous activity
        if(getIntent().getExtras() != null){
            MusicPlaylist musicPlaylist = (MusicPlaylist) getIntent().getExtras().get("object_music_playlist");
            updateTrack(musicPlaylist.getMusicPlaylistId().trim());
            setData(musicPlaylist.getMusicPlaylistId().trim());
            musicPlaylistName = (com.google.android.material.appbar.CollapsingToolbarLayout) findViewById(R.id.activityMusicPlaylistTrack_listName);
            musicPlaylistImage = (ImageView) findViewById(R.id.activityMusicPlaylistTrack_listImage);
            musicPlaylistName.setTitle(musicPlaylist.getlName().trim());
            Glide.with(this).load(musicPlaylist.getlImageUrl()).into(musicPlaylistImage);
        }
    }

    //Get list track from firebase
    private List<Track> getListTrack(String musicPlaylistId)
    {
        List<Track> list = new ArrayList<>();
        List<MusicPlaylistTrack> listMusicPlaylistTrack = getListMusicPlaylistTrack(musicPlaylistId);

        DAOTrack daoTrack = new DAOTrack();
        // this listener will listen from firebase continuously and get data
        daoTrack.getByKey().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data: snapshot.getChildren()){
                    Track track = data.getValue(Track.class);
                    for(MusicPlaylistTrack musicPlaylistTrack: listMusicPlaylistTrack){
                        if(     musicPlaylistTrack.getMusicPlaylistId().trim().equals(musicPlaylistId)
                                && track.getTrackId().trim().equals(musicPlaylistTrack.getTrackId().trim())
                                && track.gettGenre().trim().equals(musicPlaylistTrack.gettGenre().trim())){
                            list.add(track);
                            String key = data.getKey();
                            musicPlaylistTrack.setKey(key);
                        } else if (musicPlaylistTrack.getMusicPlaylistId().trim().equals(musicPlaylistId)
                                && track.getTrackId().trim().equals(musicPlaylistTrack.getTrackId().trim())
                                && track.gettListens() >= 10
                        ) {
                            list.add(track);
                            String key = data.getKey();
                            musicPlaylistTrack.setKey(key);
                        } else if (musicPlaylistTrack.getMusicPlaylistId().trim().equals(musicPlaylistId)
                                && track.getTrackId().trim().equals(musicPlaylistTrack.getTrackId().trim())
                                && track.getArtistId().trim().equals(musicPlaylistTrack.getArtistId().trim())
                        ) {
                            list.add(track);
                            String key = data.getKey();
                            musicPlaylistTrack.setKey(key);
                        }else if (musicPlaylistTrack.getMusicPlaylistId().trim().equals(musicPlaylistId)
                                && track.getTrackId().trim().equals(musicPlaylistTrack.getTrackId().trim())
                        ) {
                            list.add(track);
                            String key = data.getKey();
                            musicPlaylistTrack.setKey(key);
                        }
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
        // get list of music playlist
    private List<MusicPlaylistTrack> getListMusicPlaylistTrack(String musicPlaylistId){
        List<MusicPlaylistTrack> list = new ArrayList<>();
        // Declare dao of MusicPlaylistTrack to get data
        DAOMusicPlaylistTrack daoMusicPlaylistTrack = new DAOMusicPlaylistTrack();
        // this listener will listen from firebase continuously and get data
        daoMusicPlaylistTrack.getByKey().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data: snapshot.getChildren()){
                    MusicPlaylistTrack musicPlaylistTrack = data.getValue(MusicPlaylistTrack.class);
                    list.add(musicPlaylistTrack);
                    String key = data.getKey();
                    musicPlaylistTrack.setKey(key);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return list;
    }

    // update the track to music playlistTrack
    private void updateTrack(String musicPlaylistId) {
        List<MusicPlaylistTrack> list = getListMusicPlaylistTrack(musicPlaylistId);
        if(list.size() == 0){
            for(MusicPlaylistTrack musicPlaylistTrack: list){
                MusicPlaylistTrackBuilder musicPlaylistTrackBuilder = new MusicPlaylistTrackBuilder.Builder().musicPlaylistId(musicPlaylistId)
                                                                                                             .trackId(musicPlaylistTrack.getTrackId().trim())
                                                                                                             .artistId(musicPlaylistTrack.getArtistId().trim())
                                                                                                             .tGenre(musicPlaylistTrack.gettGenre().trim()).build();
                daoMusicPlaylistTrack.addNewMusicPlaylistTrack(musicPlaylistTrackBuilder);
            }
        }

//        PlaylistTrack newTrack = new PlaylistTrack(randomId() ,track.getTrackId().trim(), PlaylistMusicActivity.definedPlaylist.getPlaylistId().trim());
//        daoPlayListTrack.addNewPlaylistTrack(newTrack).addOnSuccessListener(suc -> {
//            Toast.makeText(pContext, "Add track to playlist successfully!", Toast.LENGTH_SHORT).show();
//        }).addOnFailureListener(err -> {
//            Toast.makeText(pContext, "Can't add this track!", Toast.LENGTH_SHORT).show();
//        });
    }

    //Set data of recyclerview
    public void setData(String musicPlaylistId){
        //Set data for track
        rcvTrack = findViewById(R.id.activityMusicPlaylistTrack_listMusic);
        trackAdapter = new TrackAdapter(this);
        LinearLayoutManager linearLayoutTrackManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rcvTrack.setLayoutManager(linearLayoutTrackManager);
        trackAdapter.setData(getListTrack(musicPlaylistId));
        rcvTrack.setAdapter(trackAdapter);
    }
}
