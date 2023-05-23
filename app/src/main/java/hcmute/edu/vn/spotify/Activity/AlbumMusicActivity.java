package hcmute.edu.vn.spotify.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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
import hcmute.edu.vn.spotify.Service.MyService;

public class AlbumMusicActivity extends AppCompatActivity {
    // The view which showing data as list
    private RecyclerView rcvAlbum;

    // The view which showing data as list
    private RecyclerView rcvTrack;

    // use to pass raw data into recyclerview of album
    private AlbumAdapter albumAdapter;

    // use to pass raw data into recyclerview of track
    private TrackAdapter trackAdapter;

    //Set name for activity by album name
    com.google.android.material.appbar.CollapsingToolbarLayout albumName;

    //Set image for album
    ImageView albumImage;

    // The view which showing FloatingActionButton button in activity
    FloatingActionButton btn_playlist;
    static String AlbumName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_music);

        // Mapping the view
        btn_playlist = findViewById(R.id.btn_playlist);

        // Check if data has been received from previous intent null
        if(getIntent().getExtras() != null){

            // Get object data
            Album album = (Album) getIntent().getExtras().get("object_album");

            setAlbumData(album);
            setTrackData(album);
            //Set artist name and artist image
            albumName = (com.google.android.material.appbar.CollapsingToolbarLayout) findViewById(R.id.activityAlbumMusic_albumName);
            albumImage = (ImageView) findViewById(R.id.activityAlbumMusic_albumImage);

            // set title of album
            albumName.setTitle(album.getName().trim());
            // set Image view with url by library
            Glide.with(this).load(album.getResourceId().trim()).into(albumImage);

            // function which use to play a list of track if clicking the floating action button
            playListTrack(MainActivity.playlist);
        }
    }

    // use to get list of album
    private List<Album> getListAlbum(String artistId, String artistName)
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
    private List<Track> getListTrack(String type)
    {
        //Create a new list to pass data
        List<Track>  list = new ArrayList<>();
        // Create DAO to get data
        DAOTrack daoTrack = new DAOTrack();
        // this listener will listen from firebase continuously and get data
        daoTrack.getByKey().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot data: snapshot.getChildren()){
                    Track track = data.getValue(Track.class);
                    String key = data.getKey();

                    track.setKey(key);
                    try{
                        if(track.gettAlbum() != null){
                            if(track.gettAlbum().getAlbumId().equals(type)){
                                list.add(track);
                            }
                        }
                        else
                        if(track.gettAlbum() == null){
                            if(track.gettAlbum().getAlbumId().equals(type)){
                                list.add(track);
                            }
                        }
                    }
                    catch (Exception e){
                    }
                }
                // Update the global current playlist
                MainActivity.playlist= list;
                // Change recycle view
                trackAdapter.notifyDataSetChanged();
                trackAdapter.setData(list);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        return list;
    }





    // function which use to play a list of track if clicking the floating action button
    public void playListTrack(List<Track> trackList)
    {
        btn_playlist = findViewById(R.id.btn_playlist);
        btn_playlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //PlayMedia(trackList.get(0).getSource());
                MainActivity.player.stop(true);
                PlayListMedia(MainActivity.playlist);
                MainActivity.track = MainActivity.playlist.get(0);
                MainActivity.name_track.setText( MainActivity.track .getName());
                MainActivity.nameArtist_track.setText( MainActivity.track .getName());
                MainActivity.img_track.setImageBitmap(MyService.getBitmapFromURL(MainActivity.track .getImage()));
                MainActivity.currentIndex = 0;
                MainActivity.typePlaying = "list";
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        // whenever go to other activity and back to this, the  MainActivity.playlist must be update in this function
        getListTrack(AlbumName);
    }
    // Play a list of current track
    public void PlayListMedia(List<Track> tracks)
    {
        // Get app name
        int appNameStringRes = R.string.app_name;

        //MainActivity.pvMain.setPlayer(MainActivity.player);
        // add all track in playlist to music player
        for(int i =0; i < tracks.size(); i++)
        {
            Uri uriOfContentUrl = Uri.parse(tracks.get(i).getSource());
            MediaItem Item = MediaItem.fromUri(uriOfContentUrl);
            // Add the media items to be played.
            MainActivity.player.addMediaItem(Item);
        }

        // loading data for playing
        MainActivity.player.prepare();

        // Start the playback.
        MainActivity.player.play();

        //Always display the controller of player
        MainActivity.pvMain.setControllerShowTimeoutMs(0);
        // Show controller
        MainActivity.pvMain.showController();
        // Disable hiding view when touching
        MainActivity.pvMain.setControllerHideOnTouch(false);
    }

    //Set data for album
    public void setAlbumData(Album album)
    {
        //Set data for albums
        rcvAlbum = findViewById(R.id.activityAlbumMusic_listAlbumRv);
        albumAdapter = new AlbumAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        rcvAlbum.setLayoutManager(linearLayoutManager);
        albumAdapter.setData(getListAlbum(album.getArtistId().trim(), album.getArtistName().trim()));
        rcvAlbum.setAdapter(albumAdapter);
    }

    public void setTrackData(Album album){
        //Set data for track
        rcvTrack = findViewById(R.id.activityAlbumMusic_listMusicRv);
        trackAdapter = new TrackAdapter(this);
        LinearLayoutManager linearLayoutTrackManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rcvTrack.setLayoutManager(linearLayoutTrackManager);
        trackAdapter.setData(getListTrack(album.getAlbumId().trim()));
        AlbumName = album.getAlbumId().trim();
        rcvTrack.setAdapter(trackAdapter);
    }
}
