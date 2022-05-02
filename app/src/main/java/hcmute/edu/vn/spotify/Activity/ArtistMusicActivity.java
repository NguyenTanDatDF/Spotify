package hcmute.edu.vn.spotify.Activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import hcmute.edu.vn.spotify.Adapter.AlbumAdapter;
import hcmute.edu.vn.spotify.Adapter.PlaylistAdapter;
import hcmute.edu.vn.spotify.Adapter.TrackAdapter;
import hcmute.edu.vn.spotify.Model.Album;
import hcmute.edu.vn.spotify.Model.Track;
import hcmute.edu.vn.spotify.R;

public class ArtistMusicActivity extends AppCompatActivity {

    private RecyclerView rcvAlbum;
    private RecyclerView rcvTrack;
    private AlbumAdapter albumAdapter;
    private TrackAdapter trackAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_music);

        //Set data for albums
        rcvAlbum = findViewById(R.id.activityArtistMusic_albumRv);
        albumAdapter = new AlbumAdapter(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        rcvAlbum.setLayoutManager(linearLayoutManager);

        albumAdapter.setData(getListAlbum());
        rcvAlbum.setAdapter(albumAdapter);

        //Set data for track
        rcvTrack = findViewById(R.id.activityArtistMusic_listMusicRv);
        trackAdapter = new TrackAdapter(this);

        LinearLayoutManager linearLayoutTrackManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rcvTrack.setLayoutManager(linearLayoutTrackManager);

        trackAdapter.setData(getListTrack());
        rcvTrack.setAdapter(trackAdapter);
    }
    private List<Album> getListAlbum()
    {
        List<Album> list = new ArrayList<>();
        list.add(new Album("Chill" ,R.drawable.album1, "MCK, K-ICM, LowG"));
        list.add(new Album("Remix Tiktok" ,R.drawable.album2,"Nguyen Tan Dat, Cukak"));
        list.add(new Album("Bolero" ,R.drawable.album3,"Tran Dang Khoa"));
        list.add(new Album("Nonstop", R.drawable.album4, "Nguyen Le Minh Nhut"));
        list.add(new Album( "Piano",R.drawable.album5,"Ho Dang Tien" ));
        list.add(new Album("Guitar", R.drawable.album6, "Nguyen Thien Hoan"));

        return list;
    }
    private List<Track> getListTrack()
    {
        List<Track> list = new ArrayList<>();
        list.add(new Track("Không con đâu" ,17092001, R.drawable.album, "",  ""));
        list.add(new Track("Không con nha" ,17092001, R.drawable.album1, "",  ""));
        list.add(new Track("Không con haha" ,17092001, R.drawable.album2, "",  ""));
        list.add(new Track("Không con sad" ,17092001, R.drawable.album3, "",  ""));
        list.add(new Track("Không con vjp" ,17092001, R.drawable.album4, "",  ""));

        return list;
    }
}
