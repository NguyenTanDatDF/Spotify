package hcmute.edu.vn.spotify.Activity;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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
import hcmute.edu.vn.spotify.Service.MyService;

public class ArtistMusicActivity extends AppCompatActivity {
    // The view which showing data as list
    private RecyclerView rcvAlbum;

    // The view which showing data as list
    private RecyclerView rcvTrack;

    // use to pass raw data into recyclerview of album
    private AlbumAdapter albumAdapter;

    // use to pass raw data into recyclerview of track
    private TrackAdapter trackAdapter;

    //Set name for artist
    com.google.android.material.appbar.CollapsingToolbarLayout artistName;

    //Set artist image for artist
    ImageView artistImage;
    // static name
    static String  Name;
    // Declare the view
    FloatingActionButton btn_playlist;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_music);

        // Check if data has been received from previous intent null
        if(getIntent().getExtras() != null){

            // Get object data
            Artist artist = (Artist) getIntent().getExtras().get("object_artist");

            //Set artist name and artist image
            artistName = (com.google.android.material.appbar.CollapsingToolbarLayout) findViewById(R.id.activityArtistMusic_artistName);
            artistImage = (ImageView) findViewById(R.id.activityArtistMusic_artistImage);

            artistName.setTitle(artist.getNameArtist().trim());
            Glide.with(this).load(artist.getImageArtist().trim()).into(artistImage);

            //Set data of recyclerview by artist id
            setData(artist.getIdArtist().trim());
            Name = artist.getIdArtist().trim();
        }
        playListTrack(MainActivity.playlist);
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
                list.clear();
                for(DataSnapshot data: snapshot.getChildren()){
                    Track track = data.getValue(Track.class);
                    if(track.getArtistId().trim().equals(artistId))
                    {
                        list.add(track);
                        String key = data.getKey();
                        track.setKey(key);
                    }
                }
                MainActivity.playlist= list;
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

    @Override
    protected void onResume() {
        super.onResume();
        getListTrack(Name);
    }
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


    public void PlayListMedia(List<Track> tracks)
    {

        int appNameStringRes = R.string.app_name;

        //MainActivity.pvMain.setPlayer(MainActivity.player);
        for(int i =0; i < tracks.size(); i++)
        {
            Uri uriOfContentUrl = Uri.parse(tracks.get(i).getSource());
            MediaItem Item = MediaItem.fromUri(uriOfContentUrl);
            // Add the media items to be played.
            MainActivity.player.addMediaItem(Item);
        }

        MainActivity.player.prepare();
        // Start the playback.
        MainActivity.player.play(); // start loading video and play it at the moment a chunk of it is available offline (start and play immediately)

        MainActivity.pvMain.setControllerShowTimeoutMs(0);

        MainActivity.pvMain.showController();
        MainActivity.pvMain.setControllerHideOnTouch(false);
    }
}
