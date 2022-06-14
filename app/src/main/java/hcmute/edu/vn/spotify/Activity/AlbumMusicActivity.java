package hcmute.edu.vn.spotify.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

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

    private RecyclerView rcvAlbum;
    private RecyclerView rcvTrack;
    private AlbumAdapter albumAdapter;
    private TrackAdapter trackAdapter;
    FloatingActionButton btn_playlist;
    static String AlbumName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_music);
        btn_playlist = findViewById(R.id.btn_playlist);
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
            AlbumName = album.getAlbumId().trim();
            rcvTrack.setAdapter(trackAdapter);
            playListTrack(MainActivity.playlist);
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
    private List<Track> getListTrack(String type)
    {
        List<Track>  list = new ArrayList<>();
        DAOTrack daoTrack = new DAOTrack();
        daoTrack.getByKey().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
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
                MainActivity.playlist= list;
                trackAdapter.notifyDataSetChanged();
                trackAdapter.setData(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return list;
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
    @Override
    protected void onResume() {
        super.onResume();
        getListTrack(AlbumName);
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
