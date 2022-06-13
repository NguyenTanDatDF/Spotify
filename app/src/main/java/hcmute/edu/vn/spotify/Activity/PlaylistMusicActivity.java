package hcmute.edu.vn.spotify.Activity;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
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
import hcmute.edu.vn.spotify.Adapter.SuggestTrackAdapter;
import hcmute.edu.vn.spotify.Adapter.TrackAdapter;
import hcmute.edu.vn.spotify.Database.DAOPlayListTrack;
import hcmute.edu.vn.spotify.Database.DAOPlaylist;
import hcmute.edu.vn.spotify.Database.DAOTrack;
import hcmute.edu.vn.spotify.Model.Artist;
import hcmute.edu.vn.spotify.Model.Playlist;
import hcmute.edu.vn.spotify.Model.PlaylistTrack;
import hcmute.edu.vn.spotify.Model.Track;
import hcmute.edu.vn.spotify.Model.User;
import hcmute.edu.vn.spotify.R;

public class PlaylistMusicActivity extends AppCompatActivity {

    //Global variable
    public static Playlist definedPlaylist;
    //Variables
    private com.google.android.material.appbar.CollapsingToolbarLayout playlistName;
    private ImageView playlistImage;
    private RecyclerView rcvTrack;
    private TrackAdapter trackAdapter;
    private RecyclerView rcvSuggestTrack;
    private SearchView searchView;
    private Button deleteButton;
    private SuggestTrackAdapter suggestTrackAdapter;

    //DAO data
    DAOPlayListTrack daoPlayListTrack = new DAOPlayListTrack();
    DAOPlaylist daoPlaylist = new DAOPlaylist();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        // Create view
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist_music);

        //Get user information in order to get their playlist and their playlist's track
        if(getIntent().getExtras() != null) {
            Playlist playlist = (Playlist) getIntent().getExtras().get("object_playlist");

            //Get playlist for later use
            definedPlaylist = playlist;

            //Set data for heading
            playlistName = (com.google.android.material.appbar.CollapsingToolbarLayout) findViewById(R.id.activityPlaylistMusic_playlistName);
            playlistName.setTitle(playlist.getpName().trim());
            playlistImage = (ImageView) findViewById(R.id.activityPlaylistMusic_playlistImage);
            Glide.with(this).load("https://img.freepik.com/free-vector/playlist-neon-sign-black-brick-wall_77399-755.jpg?w=2000").into(playlistImage);


            //Set data for recycle view track
            setData(playlist.getuID().trim(), playlist.getPlaylistId().trim());
            setDataSuggest();

            //search track
            SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
            searchView = (SearchView) findViewById(R.id.searchView);
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    suggestTrackAdapter.getFilter().filter(query);
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    suggestTrackAdapter.getFilter().filter(newText);
                    return false;
                }
            });

            //Delete playlist
            deleteButton = (Button) findViewById(R.id.activityPlaylistMusic_deleteBt);
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    daoPlaylist.removePlaylist(playlist.getpName()).addOnSuccessListener(suc -> {
                        Toast.makeText(PlaylistMusicActivity.this, "Remove playlist successfully!", Toast.LENGTH_SHORT).show();
                        finish();
                    }).addOnFailureListener(err -> {
                        Toast.makeText(PlaylistMusicActivity.this, "Can't delete this playlist!", Toast.LENGTH_SHORT).show();
                    });
                }
            });


            //Refresh playlist track
            daoPlayListTrack.getByKey().addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    setData(playlist.getuID().trim(), playlist.getPlaylistId().trim());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    //Get all list track that belong to one user
    private List<Track> getUserListTrack(String userId, String playlistId)
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
                        if(userId.equals(SigninActivity.definedUser.getUserId().trim()) && playlistId.equals(playlistTrack1.getPlaylistId().trim()) && track.getTrackId().trim().equals(playlistTrack1.getTrackId().trim()))
                        {
                            list.add(track);
                            String key = data.getKey();
                            track.setKey(key);
                            break;
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

    //Get all playlist track
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

    //Get all track
    private List<Track> getListTrack()
    {
        List<Track> list = new ArrayList<>();

        DAOTrack daoTrack = new DAOTrack();
        daoTrack.getByKey().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data: snapshot.getChildren()){
                    Track track = data.getValue(Track.class);
                    list.add(track);
                    String key = data.getKey();
                    track.setKey(key);
                }
                suggestTrackAdapter.setData(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return list;
    }

    //SetData for activity
    public void setData(String userId, String playlistId){
        //Set data for track
        rcvTrack = findViewById(R.id.activityArtistMusic_listMusicRv);
        trackAdapter = new TrackAdapter(this);
        LinearLayoutManager linearLayoutTrackManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rcvTrack.setLayoutManager(linearLayoutTrackManager);
        trackAdapter.setData(getUserListTrack(userId, playlistId));
        rcvTrack.setAdapter(trackAdapter);

    }

    //SetData for suggest track
    public void setDataSuggest(){

        //Set data for track suggest
        rcvSuggestTrack = findViewById(R.id.activityArtistMusic_listMusicSuggestRv);
        suggestTrackAdapter = new SuggestTrackAdapter(this);
        LinearLayoutManager linearLayoutTrackManager1 = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rcvSuggestTrack.setLayoutManager(linearLayoutTrackManager1);
        suggestTrackAdapter.setData(getListTrack());
        rcvSuggestTrack.setAdapter(suggestTrackAdapter);
    }
}
