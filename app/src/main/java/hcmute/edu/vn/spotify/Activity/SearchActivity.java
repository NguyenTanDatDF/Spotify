package hcmute.edu.vn.spotify.Activity;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import hcmute.edu.vn.spotify.Adapter.AlbumAdapter;
import hcmute.edu.vn.spotify.Adapter.ArtistAdapter;
import hcmute.edu.vn.spotify.Adapter.TrackAdapter;
import hcmute.edu.vn.spotify.Database.DAOAlbum;
import hcmute.edu.vn.spotify.Database.DAOArtist;
import hcmute.edu.vn.spotify.Database.DAOTrack;
import hcmute.edu.vn.spotify.Model.Album;
import hcmute.edu.vn.spotify.Model.Artist;
import hcmute.edu.vn.spotify.Model.Track;
import hcmute.edu.vn.spotify.R;

public class SearchActivity extends AppCompatActivity {

    private RecyclerView rcvAlbum;
    private RecyclerView rcvArtist;
    private ArtistAdapter artistAdapter;
    private TrackAdapter trackAdapter;
    SearchView searchView;
    ImageView backIv;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        //Set data
        setTrackData();
        setAlbumData();

        //search track
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) findViewById(R.id.searchView);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                trackAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                trackAdapter.getFilter().filter(newText);
                return false;
            }
        });


        backIv = findViewById(R.id.activitySearch_backIv);
        backIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }



    private List<Artist> getListArtist()
    {
        List<Artist> list = new ArrayList<>();
        DAOArtist daoArtist = new DAOArtist();
        daoArtist.getByKey().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data: snapshot.getChildren()){
                    Artist artist = data.getValue(Artist.class);
                    list.add(artist);
                    String key = data.getKey();
                    artist.setKey(key);
                }
                artistAdapter.setData(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return list;
    }
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
            //    data = list;

                trackAdapter.setData(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return list;
    }


    //set track data
    private void setTrackData(){
        //Set data for new track
        rcvArtist = findViewById(R.id.activitySearch_songsRv);
        trackAdapter = new TrackAdapter(this);
        LinearLayoutManager linearLayoutTrackManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rcvArtist.setLayoutManager(linearLayoutTrackManager);

//        List<Track> tracks = new ArrayList<>();
//        tracks.add(data.get(0));
//        tracks.add(data.get(1));
//        tracks.add(data.get(2));
//        tracks.add(data.get(3));
        trackAdapter.setData(getListTrack());
        rcvArtist.setAdapter(trackAdapter);
    }
    //set album data
    private void setAlbumData(){
        //Set Data for new album
        rcvArtist = findViewById(R.id.activitySearch_artistsRv);
        artistAdapter = new ArtistAdapter(this);
        GridLayoutManager gridArtistayoutManager = new GridLayoutManager(this,2, GridLayoutManager.VERTICAL, false);
        rcvArtist.setLayoutManager(gridArtistayoutManager);
        artistAdapter.setData(getListArtist());
        rcvArtist.setAdapter(artistAdapter);
    }


}
